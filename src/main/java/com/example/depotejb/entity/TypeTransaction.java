package com.example.depotejb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"TypeTransactions\"")
public class TypeTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"IdTypeTransaction\"")
    private int idTypeTransaction;

    @Column(name = "\"Code\"", nullable = false)
    private String code;

    @Column(name = "\"Description\"", nullable = false)
    private String description;

    // Constructeurs
    public TypeTransaction() {}

    public TypeTransaction(String code, String description) {
        this.code = code;
        this.description = description;
    }

    // Getters et Setters
    public int getIdTypeTransaction() {
        return idTypeTransaction;
    }

    public void setIdTypeTransaction(int idTypeTransaction) {
        this.idTypeTransaction = idTypeTransaction;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}