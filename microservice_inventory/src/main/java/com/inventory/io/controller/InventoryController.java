package com.inventory.io.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.io.exception.ResourceNotFoundException;
import com.inventory.io.model.Inventory;
import com.inventory.io.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
	
	@Autowired
	private InventoryService inventoryService;

	@RequestMapping(value="/add", method = RequestMethod.POST)
	public ResponseEntity<String> addInv(@RequestBody Inventory inventory) {
		return new ResponseEntity<>(inventoryService.addInv(inventory), HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/update/{productId}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateInv(@RequestBody Inventory inventory, @PathVariable String productId) {
		return new ResponseEntity<>(inventoryService.updateInv(productId,inventory), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/find/{productId}", method = RequestMethod.GET)
	public Optional<Inventory> findInv(@PathVariable String productId) throws ResourceNotFoundException{
		return inventoryService.findById(productId);
	}
	
	@RequestMapping(value="/view", method = RequestMethod.GET)
	public ResponseEntity<List<Inventory>> viewInv() {
		List<Inventory> l = inventoryService.viewInv();
		
		if(l.isEmpty()) {
			return new ResponseEntity<List<Inventory>>(l, HttpStatus.NO_CONTENT);
		}
		else
		{
			return new ResponseEntity<List<Inventory>>(l, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/delete/{productId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteInv(@PathVariable String productId) throws ResourceNotFoundException{
		return new ResponseEntity<>(inventoryService.delete(productId), HttpStatus.OK);
		
	}
	
}

