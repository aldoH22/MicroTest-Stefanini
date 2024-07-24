package com.microserviceTest.service;

import java.util.List;

import com.microserviceTest.dto.UserDTO;

public interface IUserService {

	public String createUser (UserDTO userDTO);
	
	public List<UserDTO> findAllUsers ();
	
	public UserDTO findUserById (Long id);
	
	public String updateUser (UserDTO userDTO, Long id);
	
	public String updateEmail(UserDTO userDTO, Long id);
	
	public String deleteUser(Long id);
	
}
