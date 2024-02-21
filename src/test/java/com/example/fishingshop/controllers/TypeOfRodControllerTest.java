package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodCreationRequest;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodDTO;
import com.example.fishingshop.enums.Role;
import com.example.fishingshop.models.TypeOfRod;
import com.example.fishingshop.repositories.TypeOfRodRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import java.util.List;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class TypeOfRodControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    TypeOfRodRepository typeOfRodRepository;

    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(TypeOfRodController.class)
                .build();
    }

    @BeforeAll
    static void mysqlContainerStart() {
        mySQLContainer.start();
    }

    @AfterAll
    static void mysqlContainerStop() {
        mySQLContainer.stop();
    }

    @AfterEach
    void deleteData() {
        typeOfRodRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void list() {
        TypeOfRod typeOfRod = typeOfRodRepository.save(TypeOfRod.builder()
                .type("type")
                .build());
        TypeOfRodDTO typeOfRodDTO = TypeOfRodDTO.builder()
                .id(typeOfRod.getId())
                .type(typeOfRod.getType())
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/typesOfRods/")
                        .accept("application/json")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(List.of(typeOfRodDTO))));
    }

    @Test
    @SneakyThrows
    void update() {
        TypeOfRod typeOfRod = typeOfRodRepository.save(TypeOfRod.builder()
                .type("type")
                .build());
        TypeOfRodDTO typeOfRodDTO = TypeOfRodDTO.builder()
                .id(typeOfRod.getId())
                .type("typeEdit")
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/typesOfRods/edit")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(typeOfRodDTO))
                        .with(user("testUser").authorities(Role.ADMIN.getAuthorities()))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Type with id " + typeOfRodDTO.getId() + " was updated"));
    }

    @Test
    @SneakyThrows
    void create() {
        TypeOfRodCreationRequest request = TypeOfRodCreationRequest.builder()
                .type("type")
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/typesOfRods/")
                        .with(user("testUser").authorities(Role.ADMIN.getAuthorities()))
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Type with name " + request.getType() + " was created"));
    }

    @Test
    @SneakyThrows
    void delete() {
        TypeOfRod typeOfRod = typeOfRodRepository.save(TypeOfRod.builder()
                .type("type")
                .build());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/typesOfRods/" + typeOfRod.getId())
                        .with(user("testUser").authorities(Role.ADMIN.getAuthorities())))
                .andExpect(status().isOk())
                .andExpect(content().string("Type of rod with id " + typeOfRod.getId() + " was deleted"));
    }
}