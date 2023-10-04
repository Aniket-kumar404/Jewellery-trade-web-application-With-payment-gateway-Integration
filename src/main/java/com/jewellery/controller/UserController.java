package com.jewellery.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.jewellery.cart.ViewCart;
import com.jewellery.model.Role;
import com.jewellery.model.User;
import com.jewellery.repository.CategoryRepository;
import com.jewellery.repository.ProductRepository;
import com.jewellery.repository.RoleRepository;
import com.jewellery.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserRepository userrepo;
	@Autowired
	RoleRepository rolerepo;

	@Autowired
	CategoryRepository categoryrepo;

	@Autowired
	ProductRepository productrepo;

	@GetMapping("/")
	public String home(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("cartCount",ViewCart.cart.size());
		model.addAttribute("categories", categoryrepo.findAll());
		model.addAttribute("products", productrepo.findAll());

		return "user/Homepage";
	}


	
	
	@PostMapping("/saveuser")
	public String saveUser(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
		System.out.println(user);
		String password = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		List<Role> roles = new ArrayList<>();
		roles.add(rolerepo.findById(2).get());
		user.setRoles(roles);
		userrepo.save(user);
//		request.login(user.getEmail(), password);
		return "redirect:/";
	}
//
	@PostMapping("/user/login")
	public String loginPage(HttpServletRequest request) throws ServletException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		System.out.println(email+" "+password);
		request.login(email, password);
		return "redirect:/";
	}

	@GetMapping("/user/profile")
	public String profile(HttpServletRequest request,Model model) {
		try {
		String email = request.getRemoteUser();
		User user= userrepo.findUserByEmail(email).get();
		model.addAttribute("user",user);
		return "user/viewprofile";
		
		}
		catch(Exception e) {
			return "redirect:/";
		}
	}
	
	@GetMapping("/user/profile/edit")
	public String profileEdit(HttpServletRequest request,Model model) {
		try {
		String email = request.getRemoteUser();
		User user= userrepo.findUserByEmail(email).get();
		model.addAttribute("user",user);
		return "user/editprofile";
		
		}
		catch(Exception e) {
			return "redirect:/";
		}
	}
	@PostMapping("/user/profile/update")
	public String updateProfile(@ModelAttribute("user") User user,HttpServletRequest request) {
			String email = request.getRemoteUser();
			User user2= userrepo.findUserByEmail(email).get();
			
			user2.setFirstName(user.getFirstName());
			user2.setLastName(user.getLastName());
			user2.setEmail(user.getEmail());
			user2.setPassword(user.getPassword());
			userrepo.save(user2);
			return "redirect:/";
		
	}
// @GetMapping("/user/logout")
// 	public String logout(HttpServletRequest request) throws ServletException {
// 		request.logout();
// 		ViewCart.cart.clear();
		
// 		return "redirect:/";
// 	}

	@GetMapping("/user/logout")
	public String logout(HttpServletRequest request) throws ServletException {
		request.logout();
		ViewCart.cart.clear();
		
		return "redirect:/";
	}
}
