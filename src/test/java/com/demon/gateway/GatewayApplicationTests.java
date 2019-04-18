package com.demon.gateway;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayApplication.class)
@Slf4j
@AutoConfigureWebTestClient
public class GatewayApplicationTests {
    @Autowired
    protected WebTestClient webTestClient;


    @Test
    public void contextLoads() {

    }


}
