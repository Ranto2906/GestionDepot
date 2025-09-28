package com.example.depotejb.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "Flux")
public class Flux {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdFlux")
    private int idFlux;

    @Column(name = "Sens", nullable = false)
    private String sens;

    @Column(name = "Montant", nullable = false)
    private BigDecimal montant;

    @Column(name = "CreatedAt", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "IdCompte", nullable = false)
    private int idCompte;

    @ManyToOne
    @JoinColumn(name = "IdTransaction", nullable = false)
    private Transaction transaction;

    // Constructeurs
    public Flux() {}

    public Flux(String sens, BigDecimal montant, OffsetDateTime createdAt, int idCompte, Transaction transaction) {
        this.sens = sens;
        this.montant = montant;
        this.createdAt = createdAt;
        this.idCompte = idCompte;
        this.transaction = transaction;
    }

    // Getters et Setters
    public int getIdFlux() {
        return idFlux;
    }

    public void setIdFlux(int idFlux) {
        this.idFlux = idFlux;
    }

    public String getSens() {
        return sens;
    }

    public void setSens(String sens) {
        this.sens = sens;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}