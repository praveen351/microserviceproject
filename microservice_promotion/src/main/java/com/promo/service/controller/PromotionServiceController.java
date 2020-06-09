package com.promo.service.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.promo.service.common.AppUtils;
import com.promo.service.exception.ResourceNotFoundException;
import com.promo.service.model.PromotionObj;
import com.promo.service.service.PromoService;

@RestController
public class PromotionServiceController {

	@Autowired
	PromoService mPromotionService;

	@PostMapping("/addPromotion")
	public ResponseEntity<String> addPromotion(@RequestBody PromotionObj promotionObj) {
		String strReponse = mPromotionService.addPromotion(promotionObj);
		if (strReponse.equalsIgnoreCase(AppUtils.ID_SAME) || strReponse.equals(AppUtils.RECORD_INSERT_FAIL)) {
			return new ResponseEntity<String>(AppUtils.PROMOTION_ALREADY_PRESENT, HttpStatus.NOT_FOUND);
		} else if (strReponse.equals(AppUtils.RECORD_INSERT_FAIL)) {
			return new ResponseEntity<String>(AppUtils.RECORD_INSERT_FAIL, HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity<>(strReponse, HttpStatus.CREATED);
	}

	@GetMapping("/getPromoInfo/{productId}")
	public PromotionObj getPromotionInfo(@PathVariable("productId") long productId) throws ResourceNotFoundException {
		Optional<PromotionObj> promObj = mPromotionService.getPromotionInfo(productId);

		if (!promObj.isPresent())
			throw new ResourceNotFoundException(AppUtils.EXCEPTION_NOT_EXIST);

		return promObj.get();
	}

	@PutMapping("/updatePromoInfo")
	public ResponseEntity<String> UpdatePromotionInfo(@RequestBody PromotionObj productObj)
			throws ResourceNotFoundException {
		String strResponse = mPromotionService.UpdatePromotionInfo(productObj);
		if (AppUtils.PRODUCT_ID_NOT_MATCH.equals(strResponse)) {
			throw new ResourceNotFoundException(AppUtils.PRODUCT_ID_NOT_MATCH);
		}
		return new ResponseEntity<String>(strResponse, HttpStatus.OK);
	}

}
