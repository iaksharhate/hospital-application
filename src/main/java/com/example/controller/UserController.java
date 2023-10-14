package com.example.controller;

import com.example.dto.LoginDto;
import com.example.dto.Response;
import com.example.dto.UserDto;
import com.example.model.User;
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
    public ResponseEntity<Response> userSignp(@RequestBody UserDto userDto){

        User user = userService.createUser(userDto);
        Response response = new Response("User created successfully!", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> userLogin (@RequestBody LoginDto loginDto){
        Response response = new Response("Logging request processed", userService.userLogin(loginDto));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<Response> getUserDetails(@PathVariable(value = "id") int id ){
        User user = userService.getUserById(id);
        Response response = new Response("Get call success for :"+ id, user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
