package com.example.depotejb.ejb;

import jakarta.ejb.Remote;
import java.math.BigDecimal;

@Remote
public interface DepotService {
    void effectuerDepot(int idCompte, BigDecimal montant, String reference);
}