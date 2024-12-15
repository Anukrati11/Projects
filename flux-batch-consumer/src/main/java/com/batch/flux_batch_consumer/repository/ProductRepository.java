package com.batch.flux_batch_consumer.repository;

import com.batch.flux_batch_consumer.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
