package com.toshiro.dreamshops.Data;

import com.toshiro.dreamshops.Model.User;
import com.toshiro.dreamshops.Model.Role;
import com.toshiro.dreamshops.Repository.RoleRepo;
import com.toshiro.dreamshops.Repository.UserRepo;
import com.toshiro.dreamshops.Service.Role.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private RoleService roleService;





    @Override
    public void onApplicationEvent(ApplicationReadyEvent event){
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultUserIfNotExists();
        //createDefaultRole(defaultRoles);
        createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists(){
       Role userRole = roleService.getRoleById(1L);

        for(int i  = 1; i<5; i++){
            String defaultEmail = "user"+i+"@email.com";
            if (userRepo.existsByEmail(defaultEmail)){

                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepo.save(user);
            System.out.print("Default vet user" + i+"created successfully");
        }
    }

    private void createDefaultAdminIfNotExists(){
        Role adminRole = roleService.getRoleById(2L);
        for(int i  = 1; i<2; i++){
            String defaultEmail = "admin"+i+"@email.com";
            if (userRepo.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepo.save(user);
            System.out.print("Default Admin user" + i+"created successfully");
        }
    }


    private void createDefaultRole(Set<String> roles) {
        roles.stream()

                .map(Role:: new).forEach(roleRepo::save);
    }
}
