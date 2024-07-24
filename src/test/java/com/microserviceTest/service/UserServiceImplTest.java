package com.microserviceTest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.microserviceTest.DataProvider;
import com.microserviceTest.dto.UserDTO;
import com.microserviceTest.entity.UserEntity;
import com.microserviceTest.exception.UserAlreadyExistsException;
import com.microserviceTest.exception.UserNotFoundException;
import com.microserviceTest.repository.IUserDao;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@Mock
	private IUserDao userDao;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	public void testFindAll() {
		// When
		when(userDao.findAll()).thenReturn(DataProvider.userListMock());
		List<UserDTO> result = userService.findAllUsers();

		// Then
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("Tania", result.get(1).getName());
		assertEquals("Lopez", result.get(1).getLastName());
		assertEquals("tania@mail.com", result.get(1).getEmail());
		assertEquals("12345", result.get(1).getPassword());
	}

	@SuppressWarnings("unused")
	@Test
	public void testCreateUser() {
		// Given
		UserDTO userDTO = DataProvider.newUserDTOMock();
		UserEntity userEntity = this.convertToEntity(userDTO);
		// When
		this.userService.createUser(userDTO);

		// Then
		ArgumentCaptor<UserEntity> userArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
		verify(this.userDao).save(userArgumentCaptor.capture());
		assertEquals(7L, userArgumentCaptor.getValue().getId());
		assertEquals("Jesus", userArgumentCaptor.getValue().getName());
	}

	@Test
	public void testCreaterUserWhenEmailAlreadyExists() {
		// Given
		UserDTO userDTO = DataProvider.newUserDTOMock();
		when(userDao.existsByEmail(userDTO.getEmail())).thenReturn(true);

		// When & Then
		assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userDTO));
	}

	@Test
	public void testFindUserById() {
		// Given
		UserDTO userDTO = DataProvider.userDTOMock();
		Long id = userDTO.getId();
		UserEntity userEntity = this.convertToEntity(userDTO);

		// When
		when(this.userDao.findById(id)).thenReturn(Optional.of(userEntity));
		UserDTO result = this.userService.findUserById(id);

		// Then
		assertNotNull(result);
		assertEquals("Fernando", result.getName());
		assertEquals("Ortega", result.getLastName());
		assertEquals("fer@mail.com", result.getEmail());
		assertEquals("11111", result.getPassword());
		verify(this.userDao).findById(anyLong());
	}

	@Test
	public void testFindUserByIdWhenUserNotFound() {
		// Given
		Long id = 3L;
		when(this.userDao.findById(id)).thenReturn(Optional.empty());

		// When & Then
		assertThrows(UserNotFoundException.class, () -> userService.findUserById(id));

	}

	@Test
	public void testUpdateUser() {
		// Given
		UserDTO updatedUserDTO = DataProvider.newUserDTOMock();
		UserEntity existingUserEntity = DataProvider.userEntityMock();
		Long id = updatedUserDTO.getId();

		when(userDao.findById(id)).thenReturn(Optional.of(existingUserEntity));
		when(userDao.existsByEmail(updatedUserDTO.getEmail())).thenReturn(false);

		// When
		String result = userService.updateUser(updatedUserDTO, id);

		// Then
		assertEquals("Usuario actualizado correctamente", result);
		assertEquals("Jesus", existingUserEntity.getName());
		assertEquals("Figo", existingUserEntity.getLastName());
		assertEquals("jesus@mail.com", existingUserEntity.getEmail());
		assertEquals("password", existingUserEntity.getPassword());
		verify(userDao).save(existingUserEntity);

	}
	
	@Test
	public void testUpdateUserWhenEmailIsTheSame() {
	    // Given
	    UserDTO updatedUserDTO = DataProvider.newUserDTOMock();
	    UserEntity existingUserEntity = DataProvider.userEntityMockAux();
	    Long id = updatedUserDTO.getId();

	    when(userDao.findById(id)).thenReturn(Optional.of(existingUserEntity));

	    // When
	    String result = userService.updateUser(updatedUserDTO, id);

	    // Then
	    assertEquals("Usuario actualizado correctamente", result);
	    assertEquals("Jesus", existingUserEntity.getName());
	    assertEquals("Figo", existingUserEntity.getLastName());
	    assertEquals("jesus@mail.com", existingUserEntity.getEmail());
	    assertEquals("password", existingUserEntity.getPassword());
	    verify(userDao).save(existingUserEntity);
	}


	@Test
	public void testUpdateUserWhenUserNotFound() {
		// Given
		UserDTO updatedUserDTO = DataProvider.newUserDTOMock();
		Long id = updatedUserDTO.getId();
		
		when(userDao.findById(id)).thenReturn(Optional.empty());

		// When & Then
		assertThrows(UserNotFoundException.class, () -> userService.updateUser(updatedUserDTO, id));
	}

	@Test
	public void testUpdateUserWhenEmailAlreadyExists() {
		// Given
		UserDTO updatedUserDTO = DataProvider.newUserDTOMock();
		UserEntity existingUserEntity = DataProvider.userEntityMock();
		Long id = updatedUserDTO.getId();
		
		when(userDao.findById(id)).thenReturn(Optional.of(existingUserEntity));
		when(userDao.existsByEmail(updatedUserDTO.getEmail())).thenReturn(true);

		// When & Then
		assertThrows(UserAlreadyExistsException.class, () -> userService.updateUser(updatedUserDTO, id));
	}

	@Test
	public void testUpdateEmail() {

		// Given
		UserDTO updatedUserDTO = DataProvider.newUserDTOMock();
		UserEntity existingUserEntity = DataProvider.userEntityMock();
		Long id = updatedUserDTO.getId();

		when(userDao.findById(id)).thenReturn(Optional.of(existingUserEntity));
		when(userDao.existsByEmail(updatedUserDTO.getEmail())).thenReturn(false);

		// When
		String result = userService.updateEmail(updatedUserDTO, id);

		// Then
		assertEquals("Email actualizado correctamente", result);
		assertEquals("jesus@mail.com", existingUserEntity.getEmail());
		verify(userDao).save(existingUserEntity);
	}

	@Test
	public void testUpdateEmailWhenUserNotFound() {
		// Given
		UserDTO updatedUserDTO = DataProvider.newUserDTOMock();
		Long id = updatedUserDTO.getId();
		when(userDao.findById(id)).thenReturn(Optional.empty());

		// When & Then
		assertThrows(UserNotFoundException.class, () -> userService.updateEmail(updatedUserDTO, id));
	}

	@Test
	public void testUpdateEmailWhenEmailAlreadyExists() {
		// Given
		UserDTO updatedUserDTO = DataProvider.newUserDTOMock();
		UserEntity existingUserEntity = DataProvider.userEntityMock();
		Long id = updatedUserDTO.getId();
		when(userDao.findById(id)).thenReturn(Optional.of(existingUserEntity));
		when(userDao.existsByEmail(updatedUserDTO.getEmail())).thenReturn(true);

		// When & Then
		assertThrows(UserAlreadyExistsException.class,() -> userService.updateEmail(updatedUserDTO, id));
	}

	@Test
	public void testDeleteUser() {
		// Given
		UserEntity existingUserEntity = DataProvider.userEntityMock();
		Long id = existingUserEntity.getId();

		when(userDao.findById(id)).thenReturn(Optional.of(existingUserEntity));

		// When
		String result = userService.deleteUser(id);

		// Then
		assertEquals("Usuario con ID " + id + " a sido eliminado", result);
		verify(userDao).delete(existingUserEntity); 
	}
	
	@Test
	public void testDeleteUserWhenUserNotFound() {
	    // Given
	    Long id = 1L;

	    when(userDao.findById(id)).thenReturn(Optional.empty()); 

	    // When & Then
		assertThrows(UserNotFoundException.class, () -> userService.deleteUser(id));
	}

	private UserEntity convertToEntity(UserDTO userDTO) {
		return new UserEntity(userDTO.getId(), userDTO.getName(), userDTO.getLastName(), userDTO.getEmail(),
				userDTO.getPassword());
	}

}
