package com.jewellery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewellery.model.Categories;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Integer>{

}
