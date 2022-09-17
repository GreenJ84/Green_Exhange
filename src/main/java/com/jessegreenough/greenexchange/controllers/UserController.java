package com.jessegreenough.greenexchange.controllers;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jessegreenough.greenexchange.models.LoginUser;
import com.jessegreenough.greenexchange.models.User;
import com.jessegreenough.greenexchange.services.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/register")
	public String register(
				@ModelAttribute("newUser") User user,
				BindingResult results,
				HttpSession session){
		if (session.getAttribute("userId") != null) {
			return "redirect:/dashboard";
		}
		return "registration.jsp";
	}
	
	@PostMapping("/createUser")
	public String newUser(
				@Valid @ModelAttribute("newUser") User user,
				BindingResult results,
				Model model,
				HttpSession session) {
		
		session.removeAttribute("errors");
		
		if ( !user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("newUser", user);
			results.rejectValue("confirmPassword", "NoMatches", "The Confirm Password must match Password!");
			return "registration.jsp";
		}
		
		if (userService.getUserByEmail(user.getEmail()) != null) {
			session.setAttribute("errors", "User with this email already registered. Please login.");
			model.addAttribute("newUser", user);
			return "redirect:/login";
		} 
		
		else {

			if( results.hasErrors() ) {
				model.addAttribute("newUser", user);
				return "registration.jsp";
			}
		
			userService.save(user);
			
			session.setAttribute("userId", user.getId());
			session.setAttribute("userFirstName", user.getFirstName());
			session.setAttribute("userLastName", user.getLastName());
			return "redirect:/dashboard";
		}
	}
	
	@RequestMapping("/login")
	public String login(
				@ModelAttribute("loginEvent") LoginUser login,
				BindingResult results,
				HttpSession session) {
		if (session.getAttribute("userId") != null) {	
			return "redirect:/dashboard";
		}
		return "login.jsp";
	}
	
	@PostMapping("/login/check")
	public String checkLogin(
				@Valid @ModelAttribute("loginEvent") LoginUser login,
				BindingResult results,
				Model model,
				HttpSession session) {
		
		session.removeAttribute("errors");
		
		if (userService.getUserByEmail(login.getEmail()) == null) {
			
			session.setAttribute("errors", "Email does not exist in system. Please Register!");
			model.addAttribute("loginEvent", login);
			return "redirect:/register";
		} 
		
		else {
			
			User userCheck = userService.getUserByEmail(login.getEmail());
			
			if (!BCrypt.checkpw(login.getPassword(), userCheck.getPassword())) {
				
				results.rejectValue("password", "NotMatches", "Invalid Password!");
				model.addAttribute("loginEvent", login);
				return "login.jsp";
			}
			
			else {
				
				session.setAttribute("userId", userCheck.getId());
				session.setAttribute("userName", userCheck.getFirstName());
				session.setAttribute("userLastName", userCheck.getLastName());
				return "redirect:/dashboard";
			}
		}
	}
	
	@PostMapping("/deposit")
	public String deposit(
				@RequestParam(value="deposit") Integer deposit,
				HttpSession session) {
		User thisUser = userService.getUserInfo((Long) session.getAttribute("userId"));
		
		Integer account = thisUser.getAccountUSD();
		account += deposit;
		thisUser.setAccountUSD(account);

		Integer available = thisUser.getAvailableAccountUSD();
		available += deposit;
		thisUser.setAvailableAccountUSD(available);
		
		userService.updateUser(thisUser);
		
		return "redirect:/dashboard";
	}
	
	@PostMapping("/withdraw")
	public String withdraw(
				@RequestParam(value="withdraw") Integer withdraw,
				HttpSession session,
				RedirectAttributes flash) {
		
		User thisUser = userService.getUserInfo((Long) session.getAttribute("userId"));
		Integer account = thisUser.getAccountUSD();
		Integer available = thisUser.getAvailableAccountUSD();
		
		if (available-5 > withdraw) {
		account -= withdraw;
		available -= withdraw;
		thisUser.setAccountUSD(account);
		thisUser.setAvailableAccountUSD(available);
		userService.save(thisUser);
		} else {
			session.setAttribute("overdraw", "You do not have the funds to withdraw!");
			return "redirect:/dashboard";
		}
		
		thisUser.setAccountUSD(account);
		thisUser.setAvailableAccountUSD(available);
		
		userService.updateUser(thisUser);
		session.removeAttribute("overdraw");
		return "redirect:/dashboard";
	}
	
	@GetMapping("/clear")
	public String logOut(
				@ModelAttribute("loginEvent") LoginUser login,
				BindingResult results,
				HttpSession session) {
		
		session.invalidate();
		return "redirect:/login";
	}
	
	
	
	
}
