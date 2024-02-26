package com.sber.chat.messenger;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;


import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Log4j2
@ActiveProfiles("test-vlad")
@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class WebSocketTest {

    @Value("${local.server.port}")
    private int port;

    private static WebClient client;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

}
