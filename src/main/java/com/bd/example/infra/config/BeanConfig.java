package com.bd.example.infra.config;

import com.bd.example.domain.ports.repositories.ProductRepository;
import com.bd.example.domain.services.CreateProductService;
import com.bd.example.domain.services.FindProductService;
import com.bd.example.domain.services.RemoveProductService;
import com.bd.example.domain.services.UpdateProductService;
import com.bd.example.infra.adapters.repositories.ProductRepositoryImp;
import com.bd.example.infra.adapters.repositories.ProductRepositorySpring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    CreateProductService injectCreateProductService(ProductRepository repository) {
        return new CreateProductService(repository);
    }
    @Bean
    UpdateProductService injectUpdateProductService(ProductRepositorySpring repositorySpring) {
        return new UpdateProductService(new ProductRepositoryImp(repositorySpring));
    }
    @Bean
    RemoveProductService injectRemoveProductService(ProductRepositorySpring repositorySpring) {
        return new RemoveProductService(new ProductRepositoryImp(repositorySpring));
    }
    @Bean
    FindProductService injectFindProductService(ProductRepositorySpring repositorySpring) {
        return new FindProductService(new ProductRepositoryImp(repositorySpring));
    }

}
