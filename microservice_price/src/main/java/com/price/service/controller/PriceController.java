package com.price.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.price.service.exception.ServiceException;
import com.price.service.exception.exceptionhandler.ResponseMsg;
import com.price.service.model.Price;
import com.price.service.service.PriceService;

@RestController
@RequestMapping("price/")
public class PriceController {

	@Autowired
	PriceService priceService;

	/**
	 * @return List of ProductPrice
	 * @throws ServiceException
	 * 
	 *                          To get all product price list
	 */

	@RequestMapping(value = "/getTrial", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> getDemoContent() {
		return new ResponseEntity<String>("Praveen Kumar Samal", HttpStatus.OK);
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Price>> getAllPrice() throws ServiceException {
		List<Price> price = priceService.getAllPrice();

		return new ResponseEntity<List<Price>>(price, HttpStatus.OK);
	}

	/**
	 * @return
	 * @throws ServiceException
	 * 
	 *                          To add a product - price
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseMsg> addPrice(@RequestBody Price price) throws ServiceException {
		ResponseMsg responseMsg = new ResponseMsg();

		String status = priceService.addPrice(price);
		responseMsg.setMessage(status);
		responseMsg.setStatus("Success");
		return new ResponseEntity<ResponseMsg>(responseMsg, HttpStatus.OK);
	}

	/**
	 * @return Product - Price
	 * @throws ServiceException
	 * 
	 *                          To get a price of a product
	 */
	@RequestMapping(value = "/get/{productID}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Price> getPrice(@PathVariable int productID) throws ServiceException {
		Price priceinfo = priceService.getPrice(productID);
		return new ResponseEntity<Price>(priceinfo, HttpStatus.OK);
	}

	/**
	 * @return String
	 * @throws ServiceException
	 * 
	 *                          To update a product price
	 */
	@RequestMapping(value = "/update/{productID}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseMsg> updateProductPrice(@RequestBody Price price) throws ServiceException {
		ResponseMsg responseMsg = new ResponseMsg();

		String status = priceService.updateProductPrice(price);
		responseMsg.setMessage(status);
		responseMsg.setStatus("Success");
		return new ResponseEntity<ResponseMsg>(responseMsg, HttpStatus.OK);

	}

	/**
	 * @return String
	 * @throws ServiceException
	 * 
	 *                          To delete product price
	 */
	@RequestMapping(value = "/delete/{productID}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<ResponseMsg> deleteProductPriceById(@PathVariable int productID) throws ServiceException {
		String status = priceService.deleteProductPriceById(productID);

		ResponseMsg responseMsg = new ResponseMsg();
		responseMsg.setMessage(status);
		responseMsg.setStatus("Success");
		return new ResponseEntity<ResponseMsg>(responseMsg, HttpStatus.OK);
	}

	/**
	 * @return String
	 * @throws ServiceException
	 * 
	 *                          To delete all product price List
	 */
	@RequestMapping(value = "/delete/all", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<ResponseMsg> deleteAllProductPrice() throws ServiceException {
		String status = priceService.deleteAllProductPrice();
		ResponseMsg responseMsg = new ResponseMsg();
		responseMsg.setMessage(status);
		responseMsg.setStatus("Success");
		return new ResponseEntity<ResponseMsg>(responseMsg, HttpStatus.OK);
	}
}
