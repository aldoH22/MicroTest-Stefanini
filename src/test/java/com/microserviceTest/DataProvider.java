package com.microserviceTest;

import java.util.List;

import com.microserviceTest.dto.UserDTO;
import com.microserviceTest.entity.UserEntity;

public class DataProvider {

	public static List<UserEntity> userListMock() {
		return List.of(new UserEntity(1L, "Aldo", "Hernandez", "aldo@mail.com", "contraseña"),
				new UserEntity(2L, "Tania", "Lopez", "tania@mail.com", "12345"),
				new UserEntity(3L, "Fernando", "Ortega", "fer@mail.com", "11111"),
				new UserEntity(4L, "Julio", "Sanchez", "julio@mail.com", "98765"),
				new UserEntity(5L, "Alexis", "Carreon", "alex@mail.com", "alex22"),
				new UserEntity(6L, "Gabriela", "Huerta", "gabi@mail.com", "gabi1234"));
	}

	public static UserDTO userDTOMock() {

		return new UserDTO(3L, "Fernando", "Ortega", "fer@mail.com", "11111");
	}

	public static UserDTO newUserDTOMock() {

		return new UserDTO(7L, "Jesus", "Figo", "jesus@mail.com", "password");
	}

	public static UserEntity userEntityMock() {

		return new UserEntity(7L, "Cristian", "Grijalva", "cris@mail.com", "2222222");
	}
	
	public static UserEntity userEntityMockAux() {

		return new UserEntity(7L, "Jesus Cristian", "Gonzalez", "jesus@mail.com", "3456");
	}

}
