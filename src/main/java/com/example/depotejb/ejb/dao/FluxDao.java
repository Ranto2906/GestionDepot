package com.example.depotejb.ejb.dao;

import com.example.depotejb.entity.Flux;
import jakarta.persistence.EntityManager;

public class FluxDao {
    private final EntityManager em;

    public FluxDao(EntityManager em) {
        this.em = em;
    }

    public Flux save(Flux flux) {
        if (flux.getIdFlux() == 0) { // Utilisation de getIdFlux() au lieu de getId()
            em.persist(flux);
        } else {
            return em.merge(flux);
        }
        return flux;
    }

    public Flux findById(int id) {
        return em.find(Flux.class, id);
    }
}