package com.example.springcentral;

import com.example.depotejb.ejb.DepotServiceRemote;
import com.example.pretsejb.ejb.PretServiceRemote;
import com.example.springcentral.service.EjbClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CentraleApplicationTests {

    @MockBean
    private DepotServiceRemote depotServiceRemote;

    @MockBean
    private PretServiceRemote pretServiceRemote;

    @Autowired
    private EjbClientService ejbClientService;

    @Test
    void contextLoads() {
        assertNotNull(ejbClientService);
    }
}

