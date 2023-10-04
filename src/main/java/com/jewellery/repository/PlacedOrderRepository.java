package com.jewellery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewellery.model.PlacedOrder;

@Repository
public interface PlacedOrderRepository extends JpaRepository<PlacedOrder, Integer>{

}
