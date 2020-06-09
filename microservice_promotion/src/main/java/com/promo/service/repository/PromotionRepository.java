package com.promo.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.promo.service.model.PromotionObj;

@Repository
public interface PromotionRepository extends CrudRepository<PromotionObj, Long> {

}
