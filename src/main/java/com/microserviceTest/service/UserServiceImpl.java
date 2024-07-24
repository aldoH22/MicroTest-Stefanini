package com.microserviceTest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microserviceTest.dto.UserDTO;
import com.microserviceTest.entity.UserEntity;
import com.microserviceTest.exception.UserAlreadyExistsException;
import com.microserviceTest.exception.UserNotFoundException;
import com.microserviceTest.repository.IUserDao;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDAO;

	@Transactional
	@Override
	public String createUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		if(userDAO.existsByEmail(userDTO.getEmail())) {
			throw new UserAlreadyExistsException("El email '" + userDTO.getEmail() + "' ya ha sido registrado");
		}
		UserEntity userEntity = convertToEntity(userDTO);
		userDAO.save(userEntity);
		return "Usuario creado correctamente";
	}

	@Transactional(readOnly = true)
	@Override
	public List<UserDTO> findAllUsers() {
		// TODO Auto-generated method stub
		return userDAO.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public UserDTO findUserById(Long id) {
		// TODO Auto-generated method stub
		return userDAO.findById(id).map(this::convertToDTO).orElseThrow(() ->
				new UserNotFoundException("Usuario con ID: " + id + " no encontrado"));
	}

	@Transactional
	@Override
	public String updateUser(UserDTO userDTO, Long id) {
		// TODO Auto-generated method stub

        UserEntity currentUserEntity = userDAO.findById(id).orElseThrow(() -> 
        		new UserNotFoundException("Usuario con ID: " + id + " no encontrado"));

        if (!currentUserEntity.getEmail().equals(userDTO.getEmail()) && userDAO.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("El email '" + userDTO.getEmail() + "' ya ha sido registrado");
        }
        
        currentUserEntity.setName(userDTO.getName());
        currentUserEntity.setLastName(userDTO.getLastName());
        currentUserEntity.setEmail(userDTO.getEmail());
        currentUserEntity.setPassword(userDTO.getPassword());

        userDAO.save(currentUserEntity);
        return "Usuario actualizado correctamente";
		
	}

	@Transactional
	@Override
	public String updateEmail(UserDTO userDTO, Long id) {
		// TODO Auto-generated method stub

        UserEntity currentUserEntity = userDAO.findById(id).orElseThrow(() -> 
        		new UserNotFoundException("Usuario con ID: " + id + " no encontrado"));

		if(userDAO.existsByEmail(userDTO.getEmail())) {
			throw new UserAlreadyExistsException("El email '" + userDTO.getEmail() + "' ya ha sido registrado");
		}
		
        currentUserEntity.setEmail(userDTO.getEmail());
        userDAO.save(currentUserEntity);
        return "Email actualizado correctamente";
		
	}

	@Transactional
	@Override
	public String deleteUser(Long id) {
		// TODO Auto-generated method stub
		
        UserEntity currentUserEntity = userDAO.findById(id).orElseThrow(() -> 
        		new UserNotFoundException("Usuario con ID: " + id + " no encontrado"));

        userDAO.delete(currentUserEntity);
        return "Usuario con ID " + id + " a sido eliminado";
		
	}

	private UserDTO convertToDTO(UserEntity userEntity) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(userEntity, UserDTO.class);
	}

	private UserEntity convertToEntity(UserDTO userDTO) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(userDTO, UserEntity.class);
	}

}
