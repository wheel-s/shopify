package com.toshiro.dreamshops.Controller;

import com.toshiro.dreamshops.Model.User;
import com.toshiro.dreamshops.Request.CreateUserRequest;
import com.toshiro.dreamshops.Request.UserUpdateRequest;
import com.toshiro.dreamshops.Response.ApiResponse;
import com.toshiro.dreamshops.Service.User.UserService;
import com.toshiro.dreamshops.dto.UserDto;
import com.toshiro.dreamshops.exceptions.AlreadyExistsException;
import com.toshiro.dreamshops.exceptions.ResourcNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("${api.prefix}/user")
public class UsersController {
    @Autowired
   private UserService service;

    @GetMapping ("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        try {
            User user = service.getUserById(userId);
            UserDto userDto = service.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success", userDto));
        } catch (ResourcNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PostMapping ("/AddUser")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request){
        try{
            User user =  service.createUser(request);
            UserDto userDto = service.convertUserToDto(user);
            return  ResponseEntity.ok(new ApiResponse("Create user success!", userDto));
        }
        catch(AlreadyExistsException e){
           return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }

    }
    @PutMapping ("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser( @RequestBody UserUpdateRequest request, @PathVariable Long userId){

        try{
           User user = service.updateUser(request, userId);
            UserDto userDto = service.convertUserToDto(user);
            return  ResponseEntity.ok(new ApiResponse("Create user success!", userDto));
        }
        catch(ResourcNotFoundException e){
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }


    }


    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse>  deleteUser(@PathVariable Long userId) {

        try {
            service.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete user success", null));
        } catch (ResourcNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }


}
