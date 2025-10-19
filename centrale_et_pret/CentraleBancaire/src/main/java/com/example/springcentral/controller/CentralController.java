package com.example.springcentral.controller;

import com.example.springcentral.service.CSharpWebServiceClient; // Correction du package
import com.example.springcentral.service.EjbClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class CentralController {

    private final EjbClientService ejbClientService;
    private final CSharpWebServiceClient cSharpClient;

    @Autowired
    public CentralController(EjbClientService ejbClientService, CSharpWebServiceClient cSharpClient) {
        this.ejbClientService = ejbClientService;
        this.cSharpClient = cSharpClient;
    }

    @PostMapping("/depot")
    public String effectuerDepot(@RequestParam int idCompte, @RequestParam BigDecimal montant, @RequestParam String reference) {
        ejbClientService.effectuerDepot(idCompte, montant, reference);
        return "Dépôt effectué avec succès";
    }

    @PostMapping("/pret")
    public String accorderPret(@RequestParam int idCompte, @RequestParam BigDecimal montant, @RequestParam Integer duree,
                               @RequestParam int idTypePret, @RequestParam int idTypeStatus) {
        ejbClientService.accorderPret(idCompte, montant, duree, idTypePret, idTypeStatus);
        return "Prêt accordé avec succès";
    }

    @PostMapping("/remboursement")
    public String effectuerRemboursement(@RequestParam int idEcheancePret, @RequestParam BigDecimal montant) {
        // Méthode exposée dans PretServiceRemote directement si besoin, sinon ajoute un wrapper dans EjbClientService
        return "Remboursement effectué avec succès";
    }

    @PostMapping("/csharp-service")
    public String callCSharpService(@RequestBody CSharpRequest request) {
        return cSharpClient.callCSharpService(request.getEndpoint(), request.getPayload());
    }
}

class CSharpRequest {
    private String endpoint;
    private Object payload;

    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
    public Object getPayload() { return payload; }
    public void setPayload(Object payload) { this.payload = payload; }
}