package com.jessegreenough.greenexchange.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginUser {
	
	@NotNull
    @Email(message="Please enter a valid email!")
    private String email;
    
    @NotNull
    @Size(min=8, max=144, message="Password must be between 8 and 144 characters")
    private String password;
    
    public LoginUser() {}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
