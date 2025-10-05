package com.example.depotejb.ejb.dao;

import com.example.depotejb.entity.Transaction;
import jakarta.persistence.EntityManager;

public class TransactionDao {
    private final EntityManager em;

    public TransactionDao(EntityManager em) {
        this.em = em;
    }

    public Transaction save(Transaction transaction) {
        if (transaction.getIdTransaction() == 0) { // Vérifie si l'ID est 0 (non persisté)
            em.persist(transaction);
        } else {
            return em.merge(transaction);
        }
        return transaction;
    }

    public Transaction findById(int id) {
        return em.find(Transaction.class, id);
    }
}