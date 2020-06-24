package com.inventory.io.repository;

import org.springframework.data.repository.CrudRepository;

import com.inventory.io.model.Inventory;

public interface InventoryRepository extends CrudRepository<Inventory, String> {

}
