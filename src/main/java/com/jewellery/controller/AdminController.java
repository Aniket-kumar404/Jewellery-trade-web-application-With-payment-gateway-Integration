package com.jewellery.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jewellery.dto.ProductDto;
import com.jewellery.model.Categories;
import com.jewellery.model.Products;
import com.jewellery.model.Role;
import com.jewellery.model.User;
import com.jewellery.repository.CategoryRepository;
import com.jewellery.repository.PlacedOrderRepository;
import com.jewellery.repository.ProductRepository;
import com.jewellery.repository.RoleRepository;
import com.jewellery.repository.UserRepository;
@Controller
public class AdminController {
	
	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
	
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

	@Autowired
	PlacedOrderRepository orderrepo;
	

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/register")
	public String registerPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "adminRegister";
	}

	@PostMapping("/register")
	public String saveUser(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
		System.out.println(user);
		String password = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		List<Role> roles = new ArrayList<>();
		roles.add(rolerepo.findById(1).get());
		user.setRoles(roles);
		userrepo.save(user);
		request.login(user.getEmail(), password);
		return "redirect:/login/";
	}

////	Admin Dashboard  and their Authroization

	@GetMapping("/admin")
	public String homePage() {
		return "AdminDashboard";
	}
	
	
	@GetMapping("/admin/profile")
	public String profile() {
		return "profile";
	}
	

	
	

	@GetMapping("/admin/categories")
	public String categoies(Model model) {

		List<Categories> list = categoryrepo.findAll();
		model.addAttribute("categories", list);

		return "category-detail";
	}

	@GetMapping("/admin/categories/add")
	public String addCategoies(Model model) {
		Categories category = new Categories();
		model.addAttribute("category", category);
		return "category-add";
	}

	@PostMapping("/admin/categories/add")
	public String saveCategories(@ModelAttribute("category") Categories category) {
		System.out.println(category.getName());
		categoryrepo.save(category);
		return "redirect:/admin";
	}

	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategories(@PathVariable int id) {

		Categories categories = categoryrepo.findById(id).get();
		categoryrepo.delete(categories);
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/update/{id}")
	public String updateCategories(@PathVariable int id, Model model) {

		Categories categories = categoryrepo.findById(id).get();
		model.addAttribute("category", categories);
		return "category-add";
	}

	@GetMapping("/admin/products")
	public String products(Model model) {
		model.addAttribute("products",productrepo.findAll());
		return "product-detail";
	}
	
	@GetMapping("/admin/products/add")
	public String addProducts(Model model) {
		ProductDto product = new ProductDto();
		model.addAttribute("productDTO", product);
		model.addAttribute("categories", categoryrepo.findAll());
		return "product-add";
	}

	@PostMapping("/admin/products/add")
	public String saveProducts(@ModelAttribute("productDTO") ProductDto product,
				@RequestParam("productImage") MultipartFile file  , @RequestParam("imgName")  String name) throws IOException {
		
		Products pro = new Products();
		pro.setId(product.getId());
		pro.setName(product.getName());
		pro.setCategory(categoryrepo.findById(product.getCategory_id()).get());
		pro.setDescription(product.getDescription());
		pro.setPrice(product.getPrice());
		pro.setWeight(product.getWeight());
		
		String imgUUID;
		
		if(!file.isEmpty()) {
			imgUUID = file.getOriginalFilename();
			Path fileName= Paths.get(uploadDir,imgUUID);
			Files.write(fileName, file.getBytes());
			
		}else {
			imgUUID =name;
		}
		
		
		pro.setImageName(imgUUID);
		productrepo.save(pro);
		
		

		return "redirect:/admin/products";
	}

	@GetMapping("/admin/product/delete/{id}")
	public String deleteProdct(@PathVariable int id) {

		Products product = productrepo.findById(id).get();
		productrepo.delete(product);
		return "redirect:/admin/products";
	}
//
	@GetMapping("/admin/product/update/{id}")
	public String updateProducts(@PathVariable int id, Model model) {

		Products pro = productrepo.findById(id).get();
		ProductDto product = new ProductDto();
		product.setId(pro.getId());
		product.setCategory_id(pro.getCategory().getId());
		product.setDescription(pro.getDescription());
		product.setName(pro.getName());
		product.setPrice(pro.getPrice());
		product.setWeight(pro.getWeight());
		product.setImageName(pro.getImageName());	
		model.addAttribute("productDTO", product);
		model.addAttribute("categories", categoryrepo.findAll());
		return "product-add";
	}
	
	
	
	@GetMapping("/admin/orders")
	public String orders(Model model) {
		model.addAttribute("orders",orderrepo.findAll());
		return "placedorders";
	}
	
	
	
	@GetMapping("/access-denied")
	public String accessDenied() {
		return "access-denied";
	}

	
}
