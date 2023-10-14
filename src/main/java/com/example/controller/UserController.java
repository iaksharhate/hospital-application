package com.example.controller;

import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.model.MasterResponse;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private IUserService userService;

    @PostMapping("/signup")
    public ResponseEntity<MasterResponse> userSignup(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<MasterResponse> userLogin (@RequestBody LoginDto loginDto){
        return new ResponseEntity<>(userService.userLogin(loginDto), HttpStatus.OK);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<MasterResponse> getUserDetails(@PathVariable(value = "id") int id ){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }
}
