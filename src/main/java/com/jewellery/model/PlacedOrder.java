package com.jewellery.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class PlacedOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private double price;
	private double weight;
	private String description;
	private String imageName;
	private String ordered_date;
	private String order_status;
	private String expected_date;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderdetail_id", referencedColumnName = "orderdetail_id")
	private OrderDetails Orders;

}
