package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelCreationRequest;
import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelDTO;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodCreationRequest;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodDTO;
import com.example.fishingshop.enums.Role;
import com.example.fishingshop.models.TypeOfReel;
import com.example.fishingshop.models.TypeOfRod;
import com.example.fishingshop.repositories.TypeOfReelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class TypeOfReelControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    TypeOfReelRepository typeOfReelRepository;

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
                .standaloneSetup(TypeOfReelController.class)
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
        typeOfReelRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void list() {
        TypeOfReel typeOfReel = typeOfReelRepository.save(TypeOfReel.builder()
                .type("type")
                .build());
        TypeOfReelDTO typeOfReelDTO = TypeOfReelDTO.builder()
                .id(typeOfReel.getId())
                .type(typeOfReel.getType())
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/typeOfReels/")
                        .accept("application/json")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(List.of(typeOfReelDTO))));
    }

    @Test
    @SneakyThrows
    void update() {
        TypeOfReel typeOfReel = typeOfReelRepository.save(TypeOfReel.builder()
                .type("type")
                .build());
        TypeOfReelDTO typeOfReelDTO = TypeOfReelDTO.builder()
                .id(typeOfReel.getId())
                .type("typeEdit")
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/typeOfReels/edit")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(typeOfReelDTO))
                        .with(user("testUser").authorities(Role.ADMIN.getAuthorities()))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Type with id " + typeOfReelDTO.getId() + " was updated"));
    }

    @Test
    @SneakyThrows
    void create() {
        TypeOfReelCreationRequest request = TypeOfReelCreationRequest.builder()
                .type("type")
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/typeOfReels/")
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
        TypeOfReel typeOfReel = typeOfReelRepository.save(TypeOfReel.builder()
                .type("type")
                .build());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/typeOfReels/" + typeOfReel.getId())
                        .with(user("testUser").authorities(Role.ADMIN.getAuthorities())))
                .andExpect(status().isOk())
                .andExpect(content().string("Type of reel with id " + typeOfReel.getId() + " was deleted"));
    }
}