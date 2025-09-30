package com.example.depotejb.ejb;

import com.example.depotejb.entity.Flux;
import com.example.depotejb.entity.Transaction;
import com.example.depotejb.entity.TypeTransaction;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Stateless
public class DepotServiceBean implements DepotService {
    @PersistenceContext(unitName = "DepotsPU")
    private EntityManager em;

    @Override
    public void effectuerDepot(int idCompte, BigDecimal montant, String reference) {
        // Validation
        if (montant == null || montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }

        // Obtenir le type de transaction "Dépôt"
        TypeTransaction typeTransaction = em.createQuery(
                        "SELECT t FROM TypeTransaction t WHERE t.code = :code", TypeTransaction.class)
                .setParameter("code", "DEPOT")
                .getSingleResult();

        // Créer une transaction
        Transaction transaction = new Transaction();
        transaction.setMontant(montant);
        transaction.setReference(reference);
        transaction.setDateTransaction(OffsetDateTime.now(ZoneId.of("Africa/Nairobi"))); // 10:44 AM EAT
        transaction.setIdTypeStatus(1); // Supposons 1 = "ACTIF"
        transaction.setTypeTransaction(typeTransaction);
        em.persist(transaction);

        // Créer un flux
        Flux flux = new Flux();
        flux.setSens("crédit");
        flux.setMontant(montant);
        flux.setCreatedAt(OffsetDateTime.now(ZoneId.of("Africa/Nairobi"))); // 10:44 AM EAT
        flux.setIdCompte(idCompte);
        flux.setTransaction(transaction);
        em.persist(flux);

        // Appeler le Web Service C# pour mettre à jour le solde dans ComptesDB
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost("http://localhost:5082/api/comptes/" + idCompte + "/depot");
            post.setHeader("Content-Type", "application/json");
            post.setEntity(new StringEntity(montant.toString()));
            CloseableHttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Échec de l'appel au Web Service C#: " + response.getStatusLine());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'appel au Web Service C#", e);
        }
    }
}