package com.microserviceTest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.microserviceTest.DataProvider;
import com.microserviceTest.dto.UserDTO;
import com.microserviceTest.repository.IUserDao;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@Mock
	private IUserDao userDao;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Test
	public void testFindAll() {
		
		//When
		when(userDao.findAll()).thenReturn(DataProvider.userListMock());
		List<UserDTO> result = userService.findAllUsers();
		
		//Then
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("Tania", result.get(1).getName());
		assertEquals("Lopez", result.get(1).getLastName());
		assertEquals("tania@mail.com", result.get(1).getEmail());
		assertEquals("12345", result.get(1).getPassword());
		
	}
}
