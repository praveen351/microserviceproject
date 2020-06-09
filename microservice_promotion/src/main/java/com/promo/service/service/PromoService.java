package com.promo.service.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promo.service.common.AppUtils;
import com.promo.service.model.PromotionObj;
import com.promo.service.repository.PromotionRepository;

@Service
public class PromoService {
	@Autowired
	PromotionRepository mPromotionRepository;
	private static AtomicLong atomicLongPromoNo = new AtomicLong(AppUtils.PROMO_NUMER_SERIES);

	/**
	 * To create a record for Promotion
	 * 
	 * @param promotionObj - productObj
	 * @return String - message
	 */
	public String addPromotion(PromotionObj promotionObj) {
		PromotionObj createdObj = null;

		if (promotionObj != null) {
			long previousProductId = 0;
			Optional<PromotionObj> tmpObj = getPromotionInfo(promotionObj.getProductId());
			if (tmpObj.isPresent()) {
				previousProductId = tmpObj.get().getProductId();
			}
			if (promotionObj.getProductId() == previousProductId) {
				return AppUtils.ID_SAME;
			} else {
				long promoId = generatePromoId();
				promotionObj.setPromoId(promoId);
				promotionObj.setCreated("" + LocalDateTime.now());
				promotionObj.setUpdated("" + LocalDateTime.now());
				createdObj = mPromotionRepository.save(promotionObj);
			}

		}
		if (createdObj != null) {
			return AppUtils.PROMOTION_INSERT_SUCCESS + createdObj.getPromoId();
		} else {
			return AppUtils.RECORD_INSERT_FAIL;
		}
	}

	/**
	 * To find promotion by Product Id
	 * 
	 * @param ProductId - ProductId
	 * @return productObj
	 */
	public Optional<PromotionObj> getPromotionInfo(long productId) {
		return mPromotionRepository.findById(productId);
	}

	/**
	 * To update the Promotion Info
	 * 
	 * @param PromoObj - PromoObj
	 */

	public String UpdatePromotionInfo(PromotionObj promoObj) {
		String strResponseMessage;
		if (promoObj != null && existsById(promoObj.getProductId())) {
			PromotionObj tmpObj = getPromotionInfo(promoObj.getProductId()).get();
			promoObj.setPromoId(tmpObj.getPromoId());
			promoObj.setCreated(tmpObj.getCreated());
			promoObj.setUpdated("" + LocalDateTime.now());
			if (mPromotionRepository.save(promoObj) != null) {
				strResponseMessage = AppUtils.PROMOTION_RECORD_UPDATE_SUCCESS;
			} else {
				strResponseMessage = AppUtils.PROMOTION_RECORD_UPDATE_FAIL;
			}
		} else {
			strResponseMessage = AppUtils.PRODUCT_ID_NOT_MATCH;
		}
		return strResponseMessage;
	}

	/**
	 * To check weather Promotion info exist or not
	 * 
	 * @param ProductId - ProductId
	 * @return boolean - true/false
	 */
	public boolean existsById(long productId) {
		return mPromotionRepository.existsById(productId);
	}

	/**
	 * Method to generate Promotion ID
	 * 
	 * @return - Promotion ID
	 */
	private long generatePromoId() {
		return atomicLongPromoNo.getAndIncrement();
	}

}
