package com.ufpi.multimidiaawsbackend.Controllers;

import com.ufpi.multimidiaawsbackend.DTO.LoginDTO;
import com.ufpi.multimidiaawsbackend.DTO.UserDTO;
import com.ufpi.multimidiaawsbackend.DTO.UserNameLoginDTO;
import com.ufpi.multimidiaawsbackend.Models.User;
import com.ufpi.multimidiaawsbackend.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService service) {
        this.userService = service;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        User user = this.userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO user){
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, userDTO);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long id) {
        this.userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<User> Login(@RequestBody LoginDTO loginDTO){
        User newUser = userService.Login(loginDTO);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PostMapping("/login/name")
    public ResponseEntity<User> Login(@RequestBody UserNameLoginDTO loginDTO){
        User newUser = userService.Login(loginDTO);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    //TODO: Esqueceu a senha
}