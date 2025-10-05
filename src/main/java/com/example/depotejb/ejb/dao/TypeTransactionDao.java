package com.example.depotejb.ejb.dao;

import com.example.depotejb.entity.TypeTransaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class TypeTransactionDao {
    private final EntityManager em;

    public TypeTransactionDao(EntityManager em) {
        this.em = em;
    }

    public TypeTransaction findByCode(String code) {
        TypedQuery<TypeTransaction> query = em.createQuery(
                "SELECT t FROM TypeTransaction t WHERE t.code = :code", TypeTransaction.class);
        query.setParameter("code", code);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public TypeTransaction save(TypeTransaction typeTransaction) {
        if (typeTransaction.getIdTypeTransaction() == 0) { // Utilisation de getIdTypeTransaction() au lieu de getId()
            em.persist(typeTransaction);
        } else {
            return em.merge(typeTransaction);
        }
        return typeTransaction;
    }
}