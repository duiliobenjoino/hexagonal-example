package com.bd.example.infra.config;

import com.bd.example.domain.services.CreateProductService;
import com.bd.example.domain.services.FindProductService;
import com.bd.example.domain.services.RecordInventoryMovement;
import com.bd.example.domain.services.RemoveProductService;
import com.bd.example.domain.services.UpdateProductService;
import com.bd.example.domain.services.transactionControl.CustomTransactionAspect;
import com.bd.example.infra.adapters.TransactionManagerImp;
import com.bd.example.infra.adapters.repositories.InventoryHistoryRepositoryImp;
import com.bd.example.infra.adapters.repositories.InventoryHistoryRepositorySpring;
import com.bd.example.infra.adapters.repositories.ProductRepositoryImp;
import com.bd.example.infra.adapters.repositories.ProductRepositorySpring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement
@Configuration
public class BeanConfig {

    @Bean
    CreateProductService injectCreateProductService(final ProductRepositorySpring repositorySpring) {
        return new CreateProductService(new ProductRepositoryImp(repositorySpring));
    }

    @Bean
    UpdateProductService injectUpdateProductService(final ProductRepositorySpring repositorySpring) {
        return new UpdateProductService(new ProductRepositoryImp(repositorySpring));
    }

    @Bean
    RemoveProductService injectRemoveProductService(final ProductRepositorySpring repositorySpring) {
        return new RemoveProductService(new ProductRepositoryImp(repositorySpring));
    }

    @Bean
    FindProductService injectFindProductService(final ProductRepositorySpring repositorySpring) {
        return new FindProductService(new ProductRepositoryImp(repositorySpring));
    }

    @Bean
    RecordInventoryMovement injectRecordInventoryMovement(
            final ProductRepositorySpring productRepositorySpring,
            final InventoryHistoryRepositorySpring inventoryHistoryRepositorySpring) {

        return new RecordInventoryMovement(
                new InventoryHistoryRepositoryImp(inventoryHistoryRepositorySpring),
                new ProductRepositoryImp(productRepositorySpring)
        );
    }

    @Bean
    CustomTransactionAspect injectCustomTransactionAspect(final PlatformTransactionManager transactionManager) {
        return new CustomTransactionAspect(new TransactionManagerImp(transactionManager));
    }

    @Bean
    public PlatformTransactionManager transactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
