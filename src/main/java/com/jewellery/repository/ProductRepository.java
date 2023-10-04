package com.jewellery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewellery.model.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer>{
		List<Products> findAllByCategory_id(int id);
}
