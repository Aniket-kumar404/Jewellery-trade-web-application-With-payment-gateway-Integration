package com.jewellery.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jewellery.model.CustomUserDetails;
import com.jewellery.model.User;
import com.jewellery.repository.UserRepository;
@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	UserRepository userrepo;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user=userrepo.findUserByEmail(email);
		user.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
		return user.map(CustomUserDetails :: new).get();
	}

}
