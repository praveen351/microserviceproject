package com.product.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.product.service.model.ProductObj;

@Repository
public interface ProductRepository extends CrudRepository<ProductObj, Long> {

	/*
	 * @Modifying
	 * 
	 * @Query(value = "SELECT amount FROM tblProduct  WHERE ProductId = :productId")
	 * public double getCurrentBalance(@Param("productId")long productId);
	 * 
	 */

}
