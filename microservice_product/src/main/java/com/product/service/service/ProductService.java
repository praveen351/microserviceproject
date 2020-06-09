package com.product.service.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.service.common.AppUtils;
import com.product.service.model.ProductObj;
import com.product.service.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository mProductRepository;
	private static AtomicLong atomicLongProductId = new AtomicLong(AppUtils.PRODUCT_ID_NUMER_SERIES);

	/**
	 * To create a record for Product
	 * 
	 * @param productObj - productObj
	 * @return String - message
	 */
	public String addProduct(ProductObj productObj) {
		ProductObj createdObj = null;
		if (productObj != null) {
			long productId = generateProductId();
			productObj.setProductId(productId);
			productObj.setCreated("" + LocalDateTime.now());
			productObj.setUpdated("" + LocalDateTime.now());
			createdObj = mProductRepository.save(productObj);
		}
		if (createdObj != null) {
			return AppUtils.RECORD_INSERT_SUCCESS + createdObj.getProductId();
		} else {
			return AppUtils.RECORD_INSERT_FAIL;
		}
	}

	/**
	 * Method to generate Product ID
	 * 
	 * @return - Product ID
	 */
	private long generateProductId() {
		return atomicLongProductId.getAndIncrement();
	}

	/**
	 * To find Product by Product Id
	 * 
	 * @param ProductId - ProductId
	 * @return productObj
	 */
	public Optional<ProductObj> findById(long productId) {
		return mProductRepository.findById(productId);
	}

	/**
	 * To check weather Product info exist or not
	 * 
	 * @param ProductId - ProductId
	 * @return boolean - true/false
	 */
	public boolean existsById(long productId) {
		return mProductRepository.existsById(productId);
	}

	/**
	 * To get all the Product Records
	 * 
	 * @return List of Product
	 */
	public ArrayList<ProductObj> findAll() {
		ArrayList<ProductObj> productList = new ArrayList<>();
		mProductRepository.findAll().forEach(custObj -> productList.add(custObj));
		return productList;
	}

	/**
	 * To delete the Product info
	 * 
	 * @param ProductId - ProductId
	 */
	public void deleteById(long productId) {
		mProductRepository.deleteById(productId);
	}

	/**
	 * To delete all the record
	 */
	public void deleteAllRecord() {
		mProductRepository.deleteAll();

	}

	/**
	 * To update the Product Info
	 * 
	 * @param productObj - productObj
	 */

	public String UpdateProductInfo(ProductObj productObj) {
		String strResponseMessage;
		if (productObj != null && existsById(productObj.getProductId())) {
			ProductObj tmpObj = findById(productObj.getProductId()).get();
			productObj.setProductId(tmpObj.getProductId());
			productObj.setCreated(tmpObj.getCreated());
			productObj.setUpdated("" + LocalDateTime.now());
			if (mProductRepository.save(productObj) != null) {
				strResponseMessage = AppUtils.RECORD_UPDATE_SUCCESS;
			} else {
				strResponseMessage = AppUtils.RECORD_UPDATE_FAIL;
			}
		} else {
			strResponseMessage = AppUtils.PRODUCT_ID_NOT_MATCH;
		}
		return strResponseMessage;
	}

	public long getProductPrice(long productId) {
		Optional<ProductObj> obj = mProductRepository.findById(productId);
		if (!obj.isPresent()) {
			return 0;
		}
		return (long) obj.get().getPrice();
	}

	public long getProductquantity(long productId) {
		Optional<ProductObj> obj = mProductRepository.findById(productId);
		if (!obj.isPresent()) {
			return 0;
		}
		return (long) obj.get().getQty();
	}

}
