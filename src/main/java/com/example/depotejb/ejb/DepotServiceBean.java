package com.example.depotejb.ejb;

import com.example.depotejb.entity.Flux;
import com.example.depotejb.entity.Transaction;
import com.example.depotejb.entity.TypeTransaction;
import com.example.depotejb.ejb.dao.FluxDao;
import com.example.depotejb.ejb.dao.TransactionDao;
import com.example.depotejb.ejb.dao.TypeTransactionDao;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import javax.sql.DataSource;

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
    private DataSource ds;

    private TransactionDao transactionDao;
    private FluxDao fluxDao;
    private TypeTransactionDao typeTransactionDao;

    @PostConstruct
    public void init() {
        try (Connection conn = ds.getConnection()) {
            System.out.println("Connexion réussie à PostgresDS");
            transactionDao = new TransactionDao(em);
            fluxDao = new FluxDao(em);
            typeTransactionDao = new TypeTransactionDao(em);
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

        TypeTransaction typeTransaction = typeTransactionDao.findByCode("DEPOT");
        if (typeTransaction == null) {
            typeTransaction = new TypeTransaction("DEPOT", "Dépôt en compte");
            typeTransactionDao.save(typeTransaction);
        }

        Transaction transaction = new Transaction();
        transaction.setMontant(montant);
        transaction.setReference(reference);
        transaction.setDateTransaction(OffsetDateTime.now(ZoneId.of("Africa/Nairobi")));
        transaction.setIdTypeStatus(1); // À rendre dynamique si possible
        transaction.setTypeTransaction(typeTransaction);
        transactionDao.save(transaction);

        Flux flux = new Flux();
        flux.setSens("crédit");
        flux.setMontant(montant);
        flux.setCreatedAt(OffsetDateTime.now(ZoneId.of("Africa/Nairobi")));
        flux.setIdCompte(idCompte);
        flux.setTransaction(transaction);
        fluxDao.save(flux);
    }
}