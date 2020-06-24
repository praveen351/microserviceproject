package com.price.service.service.serviceimpl;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.price.service.dao.PriceDao;
import com.price.service.exception.NoProductAvailable;
import com.price.service.exception.NullValueException;
import com.price.service.exception.ProductDoesNotExist;
import com.price.service.model.Price;
import com.price.service.service.PriceService;

@Service
public class PriceServiceImpl implements PriceService {

	@Autowired
	PriceDao priceDao;
	
	@Override
	public List<Price> getAllPrice() throws ServiceException {
		
		List<Price> products = priceDao.findAllPrice();
		
		try {
			
			if(products.isEmpty())
			{
				throw new NoProductAvailable("No Product Available");
			}
			else
				return products;
			
		}
		catch(NoProductAvailable e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public String addPrice(Price price) throws ServiceException {
		try {
			if(price == null )
				throw new NullValueException("Values not entered");
			else {
				priceDao.saveAndFlushBook(price);
			}
			
		}
		catch(NullValueException e) {
			throw new ServiceException(e.getMessage());
		}
		return "Product has been added successfully!";
	}


	@Override
	public Price getPrice(int productID) throws ServiceException {
		try {
			if (!priceDao.existsByProductId(productID)) {
				
				throw new ProductDoesNotExist("ProductDoesNotExist");
			}

		} catch (ProductDoesNotExist e) {
			throw new ServiceException(e.getMessage());
		}
		Price price = priceDao.findByProductId(productID);
		return price;
		
	}


	@Override
	public String updateProductPrice(Price price) throws ServiceException {
		try {
			if (!priceDao.existsByProductId(price.getProductId())) {
				
				throw new ProductDoesNotExist("ProductDoesNotExist");
			}

		} catch (ProductDoesNotExist e) {
			throw new ServiceException(e.getMessage());
		}
		
		priceDao.saveAndFlushBook(price);
		return "Product Price has been updated successfully!";
	}


	@Override
	public String deleteProductPriceById(int productID) throws ServiceException {
		try {
			if (!priceDao.existsByProductId(productID)) {
				
				throw new ProductDoesNotExist("ProductDoesNotExist");
			}

		} catch (ProductDoesNotExist e) {
			throw new ServiceException(e.getMessage());
		}		
		priceDao.deleteProductPriceByID(productID);
		
		if (!priceDao.existsByProductId(productID)) {
			return "Product Price has been deleted successfully!";
		} else {
			return "Invalid";
		}
	}

	@Override
	public String deleteAllProductPrice() {
		// TODO Auto-generated method stub
		
		priceDao.deleteAllProductPrice();
		
		if(priceDao.findAllPrice().isEmpty())
		{
			return "All Product Price has been deleted successfully!";
		}
		else
			return "Invalid";
	}




}
