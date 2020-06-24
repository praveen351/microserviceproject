package com.price.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.price.service.model.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {

}
