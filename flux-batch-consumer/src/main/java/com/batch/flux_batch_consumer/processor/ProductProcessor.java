package com.batch.flux_batch_consumer.processor;

import com.batch.flux_batch_consumer.model.Product;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ProductProcessor implements ItemProcessor<Product, Product> {

        @Override
        public Product process(Product product) throws Exception {
            double cost = product.getProdCost();
            product.setProdDiscount(cost * 0.12);
            product.setProdGst(cost * 0.18);
            return product;
        }
}
