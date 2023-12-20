package com.example.service;

import com.example.dto.LoginDto;
import com.example.dto.LoginResDto;
import com.example.dto.UserDto;
import com.example.model.MasterResponse;
import com.example.model.User;
import com.example.repository.IUserRepository;
import com.example.util.EmailService;
import com.example.util.JwtUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private EmailService emailService;
    @Autowired
    private Gson gson;
    @Override
    public MasterResponse               createUser(UserDto userDto) {
        MasterResponse masterResponse = new MasterResponse();
        User user = new User(userDto);
        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()){
            masterResponse.setCode("501");
            masterResponse.setStatus("fail");
            masterResponse.setPayload("Sign up failed!! Email already registered!!");
        } else {
            masterResponse.setStatus("Success");
            masterResponse.setCode("200");
            masterResponse.setPayload(userRepository.save(user));
//            if ("doctor".equalsIgnoreCase(userDto.getUser())){
                emailService.sendMail(userDto.getEmail(),
                        "Registration successful with E-mail: "+userDto.getEmail()+ "\n"
                                +"Password: "+ userDto.getPassword());
//            }
        }

        return masterResponse;
    }

    @Override
    public MasterResponse userLogin(LoginDto loginDto) {

        MasterResponse masterResponse = new MasterResponse();

        Optional<User> user = userRepository.userLogin(loginDto.getEmail(),loginDto.getPassword());
        if (user.isPresent()){

            LoginResDto loginResDto = new LoginResDto();
            loginResDto.setToken(jwtUtils.generateToken(user.get()));
            loginResDto.setId(user.get().getId());
            loginResDto.setUser(user.get().user);
            loginResDto.setFirstName(user.get().getFirstName());
            loginResDto.setLastName(user.get().getLastName());
            loginResDto.setEmail(user.get().getEmail());
            loginResDto.setAge(user.get().getAge());
            loginResDto.setGender(user.get().getGender());
            loginResDto.setCity(user.get().getCity());
            loginResDto.setState(user.get().getState());
            loginResDto.setPinCode(user.get().getPinCode());
            masterResponse.setCode("200");
            masterResponse.setStatus("success");
            masterResponse.setPayload(loginResDto);
        } else {
//            throw new CustomException("Login failed!! Username or password is incorrect.");
            masterResponse.setCode("501");
            masterResponse.setStatus("fail");
            masterResponse.setPayload("Login failed!! Username or password is incorrect.");
        }
        return masterResponse;
    }

    @Override
    public MasterResponse getUserById(int id) {

        MasterResponse masterResponse = new MasterResponse();
        User user = userRepository.findUserById(id);

        if (user != null) {
            log.info("GET-USER-BY-ID API RESPONSE {}", user );
            masterResponse.setCode("200");
            masterResponse.setStatus("success");
            masterResponse.setPayload(user);
        } else {
//            throw new CustomException("User with id: "+id+" not found!");
            masterResponse.setCode("501");
            masterResponse.setStatus("fail");
            masterResponse.setPayload("User with id: "+id+" not found!");
        }
        return masterResponse;
    }

    @Override
    public MasterResponse getDoctors() {
        List<User> doctorList = userRepository.getAllDoctors();
        MasterResponse masterResponse = new MasterResponse();
        if (!doctorList.isEmpty()){
            masterResponse.setCode("200");
            masterResponse.setStatus("success");
            masterResponse.setPayload(doctorList);
        } else {
            masterResponse.setCode("500");
            masterResponse.setStatus("failed");
            masterResponse.setPayload("Error while fetching doctors detail!");
        }
        return masterResponse;
    }

    @Override
    public MasterResponse updateUserById(int id, UserDto userDto) {
        MasterResponse masterResponse = new MasterResponse();
        User existingUser = userRepository.findUserById(id);
        if (existingUser != null){
            existingUser.setFirstName(userDto.getFirstName());
            existingUser.setLastName(userDto.getLastName());
            existingUser.setEmail(userDto.getEmail());
            existingUser.setPassword(userDto.getPassword());
            existingUser.setGender(userDto.getGender());
            existingUser.setAge(userDto.getAge());
            existingUser.setSpecialization(userDto.getSpecialization());
            existingUser.setExperience(userDto.getExperience());
            existingUser.setCity(userDto.getCity());
            existingUser.setState(userDto.getState());
            existingUser.setFees(userDto.getFees());
            existingUser.setPinCode(userDto.getPinCode());
            masterResponse.setCode("200");
            masterResponse.setStatus("success");
            masterResponse.setPayload(userRepository.save(existingUser));

        } else {
            masterResponse.setCode("500");
            masterResponse.setStatus("failed");
            masterResponse.setPayload("Error while updating doctors detail!");
        }
        return masterResponse;
    }

    @Override
    public MasterResponse getAllPatients() {

        MasterResponse masterResponse = new MasterResponse();
        List<User> patientList = userRepository.getPatients();
        log.info("GET-USER-DETAILS-API DB RESPONSE : {}", gson.toJson(patientList));

        if (!patientList.isEmpty()){
            masterResponse.setCode("200");
            masterResponse.setStatus("success");
            masterResponse.setPayload(patientList);
        } else {
            masterResponse.setCode("500");
            masterResponse.setStatus("failed");
            masterResponse.setPayload("Error while fetching patients detail!");
        }
        log.info("GET-USER-DETAILS-API RESPONSE : {}", gson.toJson(masterResponse));
        return masterResponse;
    }
}
