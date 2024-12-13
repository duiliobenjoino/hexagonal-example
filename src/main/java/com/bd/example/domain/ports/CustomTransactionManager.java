package com.bd.example.domain.ports;

import java.util.ConcurrentModificationException;

public interface CustomTransactionManager {
    void begin();

    void commit() throws ConcurrentModificationException;

    void rollback() throws Exception;
}
