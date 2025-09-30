package com.example.depotejb.rest;

import com.example.depotejb.ejb.DepotService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;

@Path("/depot")
public class DepotResource {
    @EJB
    private DepotService depotService;

    @POST
    @Path("/{idCompte}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response effectuerDepot(@PathParam("idCompte") int idCompte, BigDecimal montant) {
        depotService.effectuerDepot(idCompte, montant, "DEPOT_" + System.currentTimeMillis());
        return Response.ok("{\"message\": \"Dépôt effectué\"}").build();
    }
}