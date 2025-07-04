package com.toshiro.dreamshops.Service.User;


import com.toshiro.dreamshops.Model.User;
import com.toshiro.dreamshops.Repository.UserRepo;
import com.toshiro.dreamshops.Request.CreateUserRequest;
import com.toshiro.dreamshops.Request.UserUpdateRequest;
import com.toshiro.dreamshops.dto.UserDto;
import com.toshiro.dreamshops.exceptions.AlreadyExistsException;
import com.toshiro.dreamshops.exceptions.ResourcNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements  iUserService {
 @Autowired
 private  UserRepo userRepo;

 @Autowired
 private ModelMapper modelmapper;

 @Autowired
 private PasswordEncoder passwordEncoder;

 @Override
 public User getUserById(Long userId) {
  return userRepo.findById(userId).orElseThrow(()->new ResourcNotFoundException("User not found"));
 }

 @Override
 public User createUser(CreateUserRequest request) {
  return Optional.of(request)
          .filter((user->!userRepo.existsByEmail(request.getEmail())))
          .map(req ->{
           User user  = new User();
           user.setFirstName(request.getFirstName());
           user.setLastName(request.getLastName());
           user.setEmail(request.getEmail());
           user.setPassword(passwordEncoder.encode(request.getPassword()));
           return  userRepo.save(user);
          }).orElseThrow(()->new AlreadyExistsException( "Oops" + request.getEmail() + "user not found"));

 }

 @Override
 public User updateUser(UserUpdateRequest request, Long userId) {
  return userRepo.findById(userId).map(existingUser ->{
   existingUser.setFirstName(request.getFirstName());
   existingUser.setLastName(request.getLastName());
   return  userRepo.save(existingUser);
  }).orElseThrow(()-> new ResourcNotFoundException("User not found"));
 }

 @Override
 public void deleteUser(Long userId) {
  userRepo.findById(userId).ifPresentOrElse(userRepo::delete, ()->{
   throw  new ResourcNotFoundException("user not found");
  });

 }
 @Override
 public UserDto convertUserToDto(User user){
     return modelmapper.map(user, UserDto.class);
 }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepo.findByEmail(email);
    }

}
