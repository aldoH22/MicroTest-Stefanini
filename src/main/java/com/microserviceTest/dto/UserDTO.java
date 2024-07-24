package com.microserviceTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

	private Long id;
	private String name;
	private String lastName;
	private String email;
	private String password;
}
