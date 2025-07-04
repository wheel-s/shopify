package com.toshiro.dreamshops.Controller;

import com.toshiro.dreamshops.Request.LoginRequest;
import com.toshiro.dreamshops.Response.ApiResponse;
import com.toshiro.dreamshops.Response.JwtResponse;
import com.toshiro.dreamshops.Security.Jwt.JwtUtils;
import com.toshiro.dreamshops.Security.User.ShopUserDetails;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.PasswordAuthentication;

@RestController

@NoArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class AuthController {

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
     private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid  @RequestBody LoginRequest request){
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateTokenForUser(authentication);
            ShopUserDetails userDetails = (ShopUserDetails)  authentication.getPrincipal();
            JwtResponse jwtResponse= new JwtResponse(userDetails.getId(), jwt);
            return ResponseEntity.ok(new ApiResponse("Login successful", jwtResponse));
        } catch (AuthenticationException e) {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse( e.getMessage(), null));
        }


    }

}
