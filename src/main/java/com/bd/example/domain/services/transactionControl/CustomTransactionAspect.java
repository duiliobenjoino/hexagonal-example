package com.bd.example.domain.services.transactionControl;

import com.bd.example.domain.ports.TransactionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class CustomTransactionAspect {
    private final TransactionManager transactionManager;

    public CustomTransactionAspect(final TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Around("@annotation(customTransactional)")
    public Object around(
            final ProceedingJoinPoint joinPoint, final CustomTransactional customTransactional) throws Throwable {
        transactionManager.begin();
        try {
            final var result = joinPoint.proceed();
            transactionManager.commit();
            return result;
        } catch (final Throwable throwable) {
            transactionManager.rollback();
            throw throwable;
        }
    }
}
