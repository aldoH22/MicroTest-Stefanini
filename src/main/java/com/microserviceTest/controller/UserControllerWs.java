package com.microserviceTest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microserviceTest.dto.UserDTO;
import com.microserviceTest.service.UserServiceImpl;

@RestController
@RequestMapping("/UserControllerWs")
@CrossOrigin
public class UserControllerWs {

	@Autowired
	private UserServiceImpl userService;

	// http://localhost:9000/UserControllerWs/createUser
	@PostMapping("/createUser")
	public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {

		return new ResponseEntity<>(this.userService.createUser(userDTO), HttpStatus.CREATED);
	}

	// http://localhost:9000/UserControllerWs/findAllUsers
	@GetMapping("/findAllUsers")
	public ResponseEntity<List<UserDTO>> findAllUsers() {

		return new ResponseEntity<>(this.userService.findAllUsers(), HttpStatus.OK);
	}

	// http://localhost:9000/UserControllerWs/findUserById/{id}
	@GetMapping("/findUserById/{id}")
	public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) {

		return new ResponseEntity<>(this.userService.findUserById(id), HttpStatus.OK);
	}

	// http://localhost:9000/UserControllerWs/updateUser/{id}
	@PutMapping("/updateUser/{id}")
	public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {

		return new ResponseEntity<>(this.userService.updateUser(userDTO, id), HttpStatus.OK);
	}

	// http://localhost:9000/UserControllerWs/updateEmail/{id}
	@PatchMapping("/updateEmail/{id}")
	public ResponseEntity<String> updateEmail(@RequestBody UserDTO userDTO, @PathVariable Long id) {

		return new ResponseEntity<>(this.userService.updateEmail(userDTO, id), HttpStatus.OK);
	}
	
	// http://localhost:9000/UserControllerWs//deleteUser/{id}
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		
		return new ResponseEntity<>(this.userService.deleteUser(id), HttpStatus.OK);
	}
}
