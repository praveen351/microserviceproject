package com.price.service.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.price.service.model.Price;

public interface PriceService {

	List<Price> getAllPrice() throws ServiceException;

	String addPrice(Price price) throws ServiceException;

	Price getPrice(int productID) throws ServiceException;

	String updateProductPrice(Price price) throws ServiceException;

	String deleteProductPriceById(int productID) throws ServiceException;

	String deleteAllProductPrice();

}
