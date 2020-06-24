package com.product.service.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.product.service.common.AppUtils;
import com.product.service.exception.ResourceNotFoundException;
import com.product.service.model.ProductObj;
import com.product.service.service.ProductService;

@RestController
public class ProductServiceController {

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	@Autowired
	ProductService mProductService;

	@Autowired
	EurekaClient eurekaClient;

	@GetMapping("/callprice")
	public String getData(@RequestParam("authKey") String authKey) {
		RestTemplate restTemplate = restTemplateBuilder.build();

		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("micro_apigateway", false);
		String baseUrl = instanceInfo.getHomePageUrl();

		System.out.println(baseUrl);

		String result = restTemplate.getForObject(baseUrl.concat("msprice/price/getTrial?authKey=" + authKey),
				String.class);

		return result;
	}

	@PostMapping("/addProduct")
	public ResponseEntity<String> addProduct(@RequestBody ProductObj productObj) {
		return new ResponseEntity<>(mProductService.addProduct(productObj), HttpStatus.CREATED);
	}

	@GetMapping("/getProductById/{productId}")
	public ProductObj findById(@PathVariable("productId") long productId) throws ResourceNotFoundException {
		Optional<ProductObj> productObj = mProductService.findById(productId);

		if (!productObj.isPresent())
			throw new ResourceNotFoundException(AppUtils.EXCEPTION_NOT_EXIST);

		return productObj.get();
	}

	@GetMapping("/getAllProducts")
	public ArrayList<ProductObj> findAll() throws ResourceNotFoundException {
		ArrayList<ProductObj> productList = mProductService.findAll();
		if (productList == null || productList.size() < 1) {
			throw new ResourceNotFoundException(AppUtils.EXCEPTION_RECORD_NOT_FOUND);
		}
		return productList;
	}

	@DeleteMapping("/deleteProductById/{productId}")
	public void deleteById(@PathVariable("productId") long productId) {
		mProductService.deleteById(productId);
	}

	@DeleteMapping("/deleteAllProducts")
	public void deleteAllRecord() {
		mProductService.deleteAllRecord();
	}

	@PutMapping("/updateProductInfo")
	public ResponseEntity<String> UpdateProductInfo(@RequestBody ProductObj productObj)
			throws ResourceNotFoundException {
		String strResponse = mProductService.UpdateProductInfo(productObj);
		if (AppUtils.PRODUCT_ID_NOT_MATCH.equals(strResponse)) {
			throw new ResourceNotFoundException(AppUtils.PRODUCT_ID_NOT_MATCH);
		}
		return new ResponseEntity<String>(strResponse, HttpStatus.OK);
	}

	@GetMapping("/getProductPrice/{productId}")
	public long getProductPrice(@PathVariable("productId") long productId) throws ResourceNotFoundException {
		long price = mProductService.getProductPrice(productId);

		if (price == 0)
			throw new ResourceNotFoundException(AppUtils.EXCEPTION_NOT_EXIST);

		return price;
	}

	@GetMapping("/checkInventory/{productId}")
	public long checkInventory(@PathVariable("productId") long productId) throws ResourceNotFoundException {
		long quantity = mProductService.getProductquantity(productId);
		if (quantity == 0)
			throw new ResourceNotFoundException(AppUtils.EXCEPTION_NOT_EXIST);

		return quantity;
	}
}
