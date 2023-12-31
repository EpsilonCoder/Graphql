package org.sid.inventoryservice.repository;

import org.sid.inventoryservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

    @Query(value = "SELECT * FROM product WHERE category = 1", nativeQuery = true)
    List<Product> findByCategory(String category);

}
