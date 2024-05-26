package com.jewellery.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jewellery.cart.ViewCart;
import com.jewellery.model.OrderDetails;
import com.jewellery.model.PlacedOrder;
import com.jewellery.model.Products;
import com.jewellery.model.User;
import com.jewellery.repository.OrderRepository;
import com.jewellery.repository.PlacedOrderRepository;
import com.jewellery.repository.ProductRepository;
import com.jewellery.repository.RoleRepository;
import com.jewellery.repository.UserRepository;
//import com.jewellery.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
// @RequestMapping("/cart")
public class CartController {

	@Autowired
	UserRepository userrepo;
	@Autowired
	RoleRepository rolerepo;
	@Autowired
	ProductRepository productrepo;
	@Autowired
	OrderRepository orderrepo;
//	@Autowired
//	PaypalService service;
	@Autowired 
	PlacedOrderRepository confirmRepo;

	public static final String SUCCESS_URL = "cart/success";
	public static final String CANCEL_URL = "cart/cancel";


	@GetMapping("/viewproduct/{id}")
	public String viewProduct(@PathVariable int id, Model model) {
		// model.addAttribute("categories", categoryrepo.findAll());
		//

		model.addAttribute("product", productrepo.findById(id).get());

		return "user/viewProduct";
	}

	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable int id) {
		ViewCart.cart.add(productrepo.findById(id).get());
		String str ="redirect:/viewproduct/"+String.valueOf(id);
		return str;
	}
	@GetMapping("/removeItem/{id}")
	public String removeCart(Model model, @PathVariable int id) {
		ViewCart.cart.remove(productrepo.findById(id).get());
		if(ViewCart.cart.isEmpty()){
			return "redirect:/cart";
		}
		model.addAttribute("cartCount", ViewCart.cart.size());
		model.addAttribute("total", ViewCart.cart.stream().mapToDouble(Products::getPrice).sum());
		model.addAttribute("cart", ViewCart.cart);
		return "redirect:/cart";
	}

	@GetMapping("/cart")
	public String viewCart(Model model) {
		model.addAttribute("cartCount", ViewCart.cart.size());
		model.addAttribute("total", ViewCart.cart.stream().mapToDouble(Products::getPrice).sum());
		model.addAttribute("cart", ViewCart.cart);

		return "user/shoppingcart";
	}
//
//	@GetMapping("/BuyNow/{id}")
//	public String buyNowCart(@PathVariable int id,HttpServletRequest request,Model model) {
//		Products pro =productrepo.findById(id).get();
//
//		String email = request.getRemoteUser();
//		// email
//		User user= userrepo.findUserByEmail(email).get();
//		try {
//			Payment payment = service.createPayment(ViewCart.cart.stream().mapToDouble(pro.getPrice(), "USD","paypal",
//					"SALE", "Order Paying", "http://localhost:8080/" + CANCEL_URL,
//					"http://localhost:8008/" + SUCCESS_URL));
//			for(Links link:payment.getLinks()) {
//				if(link.getRel().equals("approval_url")) {		
//					int user_id = user.getId();
//					OrderDetails order = new OrderDetails();
//					model.addAttribute("orders", order);
//					order.setUser_id(user_id);
//					order.setAmount(pro.getPrice());
//					orderrepo.save(order);	
//						PlacedOrder confirmOrder = new PlacedOrder();
//						confirmOrder.setOrders(order);
//						confirmOrder.setDescription(pro.getDescription());
//						confirmOrder.setImageName(pro.getImageName());
//						confirmOrder.setName(pro.getName());
//						confirmOrder.setPrice(pro.getPrice());
//						confirmOrder.setWeight(pro.getWeight());
//						confirmRepo.save(confirmOrder);
//					return "redirect:"+link.getHref();
//				}
//			}
//			
//		} catch (PayPalRESTException e) {
//		
//			e.printStackTrace();
//		}
//		return "redirect:/";	
//	}

	

	@GetMapping("/cart/checkout")
	public String viewCheckOut(Model model) {

		OrderDetails orders = new OrderDetails();

		model.addAttribute("total", ViewCart.cart.stream().mapToDouble(Products::getPrice).sum());
		model.addAttribute("orders", orders);
		return "user/checkout";
	}

//	@PostMapping("/cart/buynow")
//	public String payNow(@ModelAttribute("orders") OrderDetails order,Model model,HttpServletRequest request) {
//		String email = request.getRemoteUser();
//		// email
//		User user= userrepo.findUserByEmail(email).get();
//		try {
//			Payment payment = service.createPayment(ViewCart.cart.stream().mapToDouble(Products::getPrice).sum(), "USD","paypal",
//					"SALE", "Order Paying", "http://localhost:8080/" + CANCEL_URL,
//					"http://localhost:8008/" + SUCCESS_URL);
//			for(Links link:payment.getLinks()) {
//				if(link.getRel().equals("approval_url")) {
//
//					
//				
//					int id = user.getId();
//					model.addAttribute("orders", order);
//					order.setUser_id(id);
//					order.setAmount( ViewCart.cart.stream().mapToDouble(Products::getPrice).sum());
//								orderrepo.save(order);
//									
//					for(Products p: ViewCart.cart) {
//						PlacedOrder confirmOrder = new PlacedOrder();
//						confirmOrder.setOrders(order);
//						confirmOrder.setDescription(p.getDescription());
//						confirmOrder.setImageName(p.getImageName());
//						confirmOrder.setName(p.getName());
//						confirmOrder.setPrice(p.getPrice());
//						confirmOrder.setWeight(p.getWeight());
//						confirmOrder.setOrder_status("Order Placed");
//						confirmOrder.setOrdered_date(String.valueOf(System.currentTimeMillis()));
//						confirmRepo.save(confirmOrder);
//					}
//					
//					ViewCart.cart.clear();
//		
//					
//					return "redirect:"+link.getHref();
//				}
//			}
//			
//		} catch (PayPalRESTException e) {
//		
//			e.printStackTrace();
//		}
//		return "redirect:/";
//	}
//		
//		

	
	

 @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "redirect:/";
    }
// @GetMapping(value = SUCCESS_URL)
// public String successPay() {
//     return "redirect:/";
// }
//@GetMapping("/success")
//public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
//    try {
//        Payment payment = service.executePayment(paymentId, payerId);
//        System.out.println(payment.toJSON());
//        if (payment.getState().equals("approved")) {
//            Payer payer =payment.getPayer(); 
//          PayerInfo info = payer.getPayerInfo();
//          String Addresss = info.getShippingAddress().toString();
//          System.out.println(Addresss);
//            return "success";
//        }
//    } catch (PayPalRESTException e) {
//     System.out.println(e.getMessage());
//    }
//    return "redirect:/";
//}
//
//	
//	
	
	
	
	@PostMapping("/cart/orderplaced")
	public String orderPlaced(@ModelAttribute("orders") OrderDetails orders) {

		// orderrepo.save(order);
		System.out.println(orders);
		return "user/orderPlaced";
	}
}
