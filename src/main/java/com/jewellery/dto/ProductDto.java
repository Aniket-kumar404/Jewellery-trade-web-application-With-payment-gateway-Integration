package com.jewellery.dto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.jewellery.model.Categories;
import com.jewellery.model.Products;

import lombok.Data;


@Data
public class ProductDto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	

	private int category_id;
	
	private double price;
	private double weight;
	private String description;
	private String imageName;
}
