package com.toshiro.dreamshops.Service.Role;


import com.toshiro.dreamshops.Model.Role;
import com.toshiro.dreamshops.Repository.RoleRepo;
import com.toshiro.dreamshops.exceptions.ResourcNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements iRoleService{

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public Role getRoleById(Long id) {
        return roleRepo.findById(id).orElseThrow(()->new ResourcNotFoundException("Role not found")) ;
    }

    @Override
    public Role addRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepo.findByName(name);
    }

    @Override
    public Role updateRole(Role role, Long id) {
        return Optional.ofNullable(getRoleById(id)).map(oldRole ->{
            oldRole.setName(role.getName());
            return roleRepo.save(oldRole);
        }).orElseThrow(()->new ResourcNotFoundException("user not found"));

    }

    @Override
    public void deleteRoleById(Long id) {
        roleRepo.findById(id).ifPresentOrElse(roleRepo::delete,
                ()->{throw new ResourcNotFoundException("Role not found!");} );
    }
}
