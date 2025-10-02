// src/main/java/com/example/depotejb/ejb/DepotServiceRemote.java
package com.example.depotejb.ejb;

import jakarta.ejb.Remote;

import java.math.BigDecimal;

@Remote
public interface DepotServiceRemote {
    void effectuerDepot(int idCompte, BigDecimal montant, String reference);
}