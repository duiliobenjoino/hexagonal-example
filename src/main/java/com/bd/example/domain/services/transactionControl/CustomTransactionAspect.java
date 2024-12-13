package com.bd.example.domain.services.transactionControl;

import com.bd.example.domain.ports.CustomTransactionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class CustomTransactionAspect {
    private final CustomTransactionManager customTransactionManager;

    public CustomTransactionAspect(final CustomTransactionManager customTransactionManager) {
        this.customTransactionManager = customTransactionManager;
    }

    @Around("@annotation(customTransactional)")
    public Object around(
            final ProceedingJoinPoint joinPoint, final CustomTransactional customTransactional) throws Throwable {
        customTransactionManager.begin();
        try {
            final var result = joinPoint.proceed();
            customTransactionManager.commit();
            return result;
        } catch (final Throwable throwable) {
            customTransactionManager.rollback();
            throw throwable;
        }
    }
}
