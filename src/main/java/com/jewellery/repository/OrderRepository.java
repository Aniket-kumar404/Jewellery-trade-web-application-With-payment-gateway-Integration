package com.jewellery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jewellery.model.OrderDetails;

public interface OrderRepository extends JpaRepository<OrderDetails, Integer>{

}
