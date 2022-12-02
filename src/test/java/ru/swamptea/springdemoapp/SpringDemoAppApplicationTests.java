package ru.swamptea.springdemoapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringDemoAppApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;



    private final GenericContainer<?> devapp = new GenericContainer<>("devapp:latest")
            .withExposedPorts(8080);


    private final GenericContainer<?> prodapp = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(8081);

    @BeforeEach
    public void setUp() {
        devapp.start();
        prodapp.start();
    }

    @Test
    void contextLoads() {

        Integer devappPort = devapp.getMappedPort(8080);
        Integer prodappPort = prodapp.getMappedPort(8081);

        ResponseEntity<String> entityFromDevapp = restTemplate.getForEntity("http://localhost:" + devappPort + "/profile", String.class);
        ResponseEntity<String> entityFromProdapp = restTemplate.getForEntity("http://localhost:" + prodappPort + "/profile", String.class);

        String answerForDev = entityFromDevapp.getBody();
        String answerForProd = entityFromProdapp.getBody();

        Assertions.assertEquals("Current profile is dev", answerForDev);
        Assertions.assertEquals("Current profile is production", answerForProd);


    }
}

