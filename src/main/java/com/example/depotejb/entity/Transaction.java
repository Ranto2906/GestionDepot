package com.example.depotejb.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "\"Transactions\"")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"IdTransaction\"")
    private int idTransaction;

    @Column(name = "\"Montant\"", nullable = false)
    private BigDecimal montant;

    @Column(name = "\"Reference\"", nullable = false)
    private String reference;

    @Column(name = "\"DateTransaction\"", nullable = false)
    private OffsetDateTime dateTransaction;

    @Column(name = "\"UpdatedAt\"")
    private OffsetDateTime updatedAt;

    @Column(name = "\"IdTypeStatus\"", nullable = false)
    private int idTypeStatus;

    @ManyToOne
    @JoinColumn(name = "\"IdTypeTransaction\"", nullable = false)
    private TypeTransaction typeTransaction;

    // Constructeurs
    public Transaction() {}

    public Transaction(BigDecimal montant, String reference, OffsetDateTime dateTransaction, int idTypeStatus, TypeTransaction typeTransaction) {
        this.montant = montant;
        this.reference = reference;
        this.dateTransaction = dateTransaction;
        this.idTypeStatus = idTypeStatus;
        this.typeTransaction = typeTransaction;
    }

    // Getters et Setters
    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public OffsetDateTime getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(OffsetDateTime dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getIdTypeStatus() {
        return idTypeStatus;
    }

    public void setIdTypeStatus(int idTypeStatus) {
        this.idTypeStatus = idTypeStatus;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }
}