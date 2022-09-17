package com.jessegreenough.greenexchange.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min=4, max=40, message="First Name must be between 3 and 40 characters")
	private String firstName;
	
	@NotNull
	@Size(min=4, max=40, message="Last Name must be between 3 and 40 characters")
	private String lastName;
	
	@NotNull
	@Email(message="Email must be between 10 and 120 characters long")
	private String email;
	
	@NotNull
	@Size(min=8, max=144, message="Password must be between 8 and 144 characters!")
	private String password;
	
	@Transient
	@Size(min=8, max=144, message="Confirm Password must be between 8 and 144 characters!")
	private String confirmPassword;
	
	private Integer accountUSD = 0;
	
	private Integer availableAccountUSD = 0;
	
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<Crypto> wallet;
	
	@Column(updatable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;
	
	
	public User() {}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Integer getAccountUSD() {
		return accountUSD;
	}


	public void setAccountUSD(Integer accountUSD) {
		this.accountUSD = accountUSD;
	}

	
	public Integer getAvailableAccountUSD() {
		return availableAccountUSD;
	}


	public void setAvailableAccountUSD(Integer availableAccountUSD) {
		this.availableAccountUSD = availableAccountUSD;
	}
	
	


	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}


	public List<Crypto> getWallet() {
		return wallet;
	}


	public void setWallet(List<Crypto> wallet) {
		this.wallet = wallet;
	}


	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
}
	