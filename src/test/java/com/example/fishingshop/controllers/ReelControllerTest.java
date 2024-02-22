package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.reel.ReelCreationRequest;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.reel.ReelEditRequest;
import com.example.fishingshop.DTOs.rod.RodCreationRequest;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.DTOs.rod.RodEditRequest;
import com.example.fishingshop.enums.Role;
import com.example.fishingshop.models.*;
import com.example.fishingshop.repositories.ManufacturerRepository;
import com.example.fishingshop.repositories.ReelRepository;
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
class ReelControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ReelRepository reelRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private TypeOfReelRepository typeOfReelRepository;

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
        reelRepository.deleteAll();
        manufacturerRepository.deleteAll();
        typeOfReelRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void list() {
        Reel reel = reelRepository.save(Reel.builder()
                .name("reel")
                .build());
        ReelDTO reelDTO = ReelDTO.builder()
                .id(reel.getId())
                .name(reel.getName())
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/reels/")
                        .accept("application/json")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(List.of(reelDTO))));
    }

    @Test
    @SneakyThrows
    void update() {
        Manufacturer manufacturer = manufacturerRepository.save(Manufacturer.builder().build());
        Reel reel = reelRepository.save(Reel.builder()
                .price(1d)
                .type(typeOfReelRepository.save(TypeOfReel.builder().build()))
                .manufacturer(manufacturer)
                .name("reel")
                .build());
        ReelEditRequest request = ReelEditRequest.builder()
                .id(reel.getId())
                .manufacturerId(manufacturer.getId())
                .price(2d)
                .name("reelEdit")
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/reels/edit")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .with(user("testUser").authorities(Role.ADMIN.getAuthorities()))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Data of reel with id " + request.getId() + " was updated"));
    }

    @Test
    @SneakyThrows
    void create() {
        manufacturerRepository.save(Manufacturer.builder().build());
        typeOfReelRepository.save(TypeOfReel.builder().build());
        ReelCreationRequest request = ReelCreationRequest.builder()
                .price(1d)
                .typeId(1L)
                .manufacturerId(1L)
                .name("reel")
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reels/")
                        .with(user("testUser").authorities(Role.ADMIN.getAuthorities()))
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reel " + request.getName() + " was created"));
    }

    @Test
    @SneakyThrows
    void delete() {
        Reel reel = reelRepository.save(Reel.builder()
                .name("reel")
                .build());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reels/" + reel.getId())
                        .with(user("testUser").authorities(Role.ADMIN.getAuthorities())))
                .andExpect(status().isOk())
                .andExpect(content().string("Reel with id " + reel.getId() + " was deleted"));
    }
    @Test
    @SneakyThrows
    void reelInfo() {
        Reel reel = reelRepository.save(Reel.builder()
                .name("reel")
                .build());
        ReelDTO reelDTO = ReelDTO.builder()
                .id(reel.getId())
                .name(reel.getName())
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/reels/" + reelDTO.getId())
                        .accept("application/json")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(reelDTO)));
    }
}