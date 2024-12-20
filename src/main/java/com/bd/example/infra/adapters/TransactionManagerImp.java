package com.bd.example.infra.adapters;

import com.bd.example.domain.ports.CustomTransactionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Slf4j
@RequiredArgsConstructor
public class TransactionManagerImp implements CustomTransactionManager {

    private final PlatformTransactionManager transactionManager;
    private TransactionStatus transactionStatus;

    @Override
    public void begin() {
        final var def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        def.setName("Custom Transaction");
        log.info("Beginning transaction with isolation level READ_COMMITTED");
        this.transactionStatus = transactionManager.getTransaction(def);
    }

    @Override
    public void commit() {
        transactionManager.commit(this.transactionStatus);
        log.info("Commit Custom Transaction");
    }

    @Override
    public void rollback() {
        transactionManager.rollback(this.transactionStatus);
        log.info("Rollback Custom Transaction");
    }
}
