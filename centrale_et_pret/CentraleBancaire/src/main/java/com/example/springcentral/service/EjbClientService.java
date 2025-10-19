package com.example.springcentral.service;

import com.example.depotejb.ejb.DepotServiceRemote;
import com.example.pretsejb.ejb.PretService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.util.Properties;

@Service
public class EjbClientService {

    private DepotServiceRemote depotService;
    private PretService pretService;

    @PostConstruct
    public void init() {
        try {
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            props.put(Context.PROVIDER_URL, "http-remoting://localhost:8083");
            props.put(Context.SECURITY_PRINCIPAL, "admin");
            props.put(Context.SECURITY_CREDENTIALS, "admin1r");
            props.put("jboss.naming.client.ejb.context", true);

            Context context = new InitialContext(props);

            // Utilisez les noms JNDI complets
            depotService = (DepotServiceRemote) context.lookup(
                "ejb:/GestionDepot-1.0-SNAPSHOT/DepotServiceBean!com.example.depotejb.ejb.DepotServiceRemote"
            );

            pretService = (PretService) context.lookup(
                "ejb:/GestionPrets-1.0-SNAPSHOT/PretServiceBean!com.example.pretsejb.ejb.PretService"
            );

            System.out.println("✅ Connexion réussie aux EJB sur WildFly !");

        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("⚠️ Échec de la connexion aux EJB : " + e.getMessage());
        }
    }

    public void effectuerDepot(int idCompte, BigDecimal montant, String reference) {
        if (depotService == null) {
            throw new IllegalStateException("Service EJB Depot non disponible. Vérifiez que WildFly est démarré et que GestionDepot-1.0-SNAPSHOT.jar est déployé.");
        }
        depotService.effectuerDepot(idCompte, montant, reference);
    }

    public void accorderPret(int idCompte, BigDecimal montant, Integer duree, int idTypePret, int idTypeStatus) {
        if (pretService == null) {
            throw new IllegalStateException("Service EJB Pret non disponible. Vérifiez que WildFly est démarré et que GestionPrets-1.0-SNAPSHOT.jar est déployé.");
        }
        pretService.accorderPret(idCompte, montant, duree, idTypePret, idTypeStatus);
    }
}