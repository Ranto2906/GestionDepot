// src/main/java/com/example/depotejb/ejb/DepotServiceBean.java
package com.example.depotejb.ejb;

import com.example.depotejb.entity.Flux;
import com.example.depotejb.entity.Transaction;
import com.example.depotejb.entity.TypeTransaction;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
//import javax.sql.DataSource;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Stateless
public class DepotServiceBean implements DepotServiceRemote {
    @PersistenceContext(unitName = "PostgresDS")
    private EntityManager em;

    @Resource(lookup = "java:jboss/datasources/PostgresDS")
    private javax.sql.DataSource ds;

    @PostConstruct
    public void init() {
        try (Connection conn = ds.getConnection()) {
            System.out.println("Connexion réussie à PostgresDS");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à PostgresDS: " + e.getMessage());
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void effectuerDepot(int idCompte, BigDecimal montant, String reference) {
        if (montant == null || montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }

        TypeTransaction typeTransaction = em.createQuery(
                        "SELECT t FROM TypeTransaction t WHERE t.code = :code", TypeTransaction.class)
                .setParameter("code", "DEPOT")
                .getSingleResult();
        if (typeTransaction == null) {
            typeTransaction = new TypeTransaction("DEPOT", "Dépôt en compte");
            em.persist(typeTransaction);
        }

        Transaction transaction = new Transaction();
        transaction.setMontant(montant);
        transaction.setReference(reference);
        transaction.setDateTransaction(OffsetDateTime.now(ZoneId.of("Africa/Nairobi")));
        transaction.setIdTypeStatus(1);
        transaction.setTypeTransaction(typeTransaction);
        em.persist(transaction);

        Flux flux = new Flux();
        flux.setSens("crédit");
        flux.setMontant(montant);
        flux.setCreatedAt(OffsetDateTime.now(ZoneId.of("Africa/Nairobi")));
        flux.setIdCompte(idCompte);
        flux.setTransaction(transaction);
        em.persist(flux);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost("http://localhost:5082/api/comptes/" + idCompte + "/depot");
            post.setHeader("Content-Type", "application/json");
            String jsonPayload = "{\"montant\": " + montant.toString() + "}";
            post.setEntity(new StringEntity(jsonPayload));
            try (CloseableHttpResponse response = client.execute(post)) {
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Échec de l'appel au Web Service C#: " + response.getStatusLine());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'appel au Web Service C#", e);
        }
    }
}