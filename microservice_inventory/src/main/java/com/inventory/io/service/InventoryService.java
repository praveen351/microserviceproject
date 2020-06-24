package com.inventory.io.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.io.exception.ResourceNotFoundException;
import com.inventory.io.model.Inventory;
import com.inventory.io.repository.InventoryRepository;

@Service
public class InventoryService {
	
	@Autowired
	private InventoryRepository inventoryRepository;

	public String addInv(Inventory inventory) {
		inventoryRepository.save(inventory);
		return "Record Added";
		
	}

	public String updateInv(String productId, Inventory inventory) {
		if (inventory.getProductId().equals(productId)) {
			inventoryRepository.save(inventory);
			return "Record Updated";
		}
		else {
			return "Record Not Found";
		}
		
		
	}

	public Optional<Inventory> findById(String productId) throws ResourceNotFoundException {
		if (inventoryRepository.existsById(productId)) {
			return inventoryRepository.findById(productId);
		}
		else {
		   throw new ResourceNotFoundException("NOT_FOUND");
		}
		
		
	}

	public String delete(String productId) throws ResourceNotFoundException{
		if (inventoryRepository.existsById(productId)) {
			inventoryRepository.deleteById(productId) ;
			return "Deleted";
		}
		else {
		   throw new ResourceNotFoundException("NOT_FOUND");
		}
		
	}

	public List<Inventory> viewInv() {
		List<Inventory> list = new ArrayList<>();
		inventoryRepository.findAll().forEach(x -> list.add(x));
		return list;
	}
	

}
