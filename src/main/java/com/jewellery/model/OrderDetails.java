package com.jewellery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class OrderDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "orderdetail_id")
	private int id;

	// @OneToMany
	private int user_id;

	private String fullname;
	private String email;
	private String phonenumber;
	private String address;
	private String city;
	private String state;
	private Long pincode;
	private double amount;


}
