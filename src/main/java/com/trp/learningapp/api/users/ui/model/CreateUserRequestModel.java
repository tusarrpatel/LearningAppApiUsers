package com.trp.learningapp.api.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {

	@NotNull(message = "Firstname cannot be null")
	@Size(min = 2, message = "fistname need to be more than 2 letters")
	private String firstName;
	@NotNull(message = "Lastname cannot be null")
	@Size(min = 2, message = "Lastname should be more than 2 letters")
	private String lastName;
	@NotNull(message = "Password cannot be null")
	@Size(min = 8, max = 16, message = "password length should be more than 8 and less tha 16")
	private String password;
	@NotNull(message = "Email cannot be null")
	@Email(message = "please verify the mail format")
	private String email;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
