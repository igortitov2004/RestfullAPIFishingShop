package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.rod.RodCreationRequest;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.DTOs.rod.RodEditRequest;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodCreationRequest;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodDTO;
import com.example.fishingshop.enums.Role;
import com.example.fishingshop.models.Manufacturer;
import com.example.fishingshop.models.Rod;
import com.example.fishingshop.models.TypeOfRod;
import com.example.fishingshop.repositories.ManufacturerRepository;
import com.example.fishingshop.repositories.RodRepository;
import com.example.fishingshop.repositories.TypeOfRodRepository;
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
class RodControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RodRepository rodRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private TypeOfRodRepository typeOfRodRepository;

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
        rodRepository.deleteAll();
        manufacturerRepository.deleteAll();
        typeOfRodRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void list() {
        Rod rod = rodRepository.save(Rod.builder()
                .name("rod")
                .build());
        RodDTO rodDTO = RodDTO.builder()
                .id(rod.getId())
                .name(rod.getName())
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/rods/")
                        .accept("application/json")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(List.of(rodDTO))));
    }

    @Test
    @SneakyThrows
    void update() {
        Manufacturer manufacturer = manufacturerRepository.save(Manufacturer.builder().build());
        Rod rod = rodRepository.save(Rod.builder()
                .testLoad(30)
                .price(30d)
                .type(typeOfRodRepository.save(TypeOfRod.builder().build()))
                .length(2)
                .weight(300)
                .manufacturer(manufacturer)
                .name("rod")
                .build());
        RodEditRequest request = RodEditRequest.builder()
                .id(rod.getId())
                .manufacturerId(manufacturer.getId())
                .price(40d)
                .name("rodEdit")
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/rods/edit")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .with(user("testUser").authorities(Role.ADMIN.getAuthorities()))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Data of rod with id " + request.getId() + " was updated"));
    }

    @Test
    @SneakyThrows
    void create() {
        manufacturerRepository.save(Manufacturer.builder().build());
        typeOfRodRepository.save(TypeOfRod.builder().build());
        RodCreationRequest request = RodCreationRequest.builder()
                .testLoad(30)
                .price(30d)
                .typeId(1L)
                .length(2)
                .weight(300)
                .manufacturerId(1L)
                .name("rod")
                .build();
         mockMvc.perform(MockMvcRequestBuilders
                        .post("/rods/")
                        .with(user("testUser").authorities(Role.ADMIN.getAuthorities()))
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Rod " + request.getName() + " was created"));
    }

    @Test
    @SneakyThrows
    void delete() {
        Rod rod = rodRepository.save(Rod.builder()
                .name("rod")
                .build());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/rods/" + rod.getId())
                        .with(user("testUser").authorities(Role.ADMIN.getAuthorities())))
                .andExpect(status().isOk())
                .andExpect(content().string("Rod with id " + rod.getId() + " was deleted"));
    }
    @Test
    @SneakyThrows
    void rodInfo() {
        Rod rod = rodRepository.save(Rod.builder()
                .name("rod")
                .build());
        RodDTO rodDTO = RodDTO.builder()
                .id(rod.getId())
                .name(rod.getName())
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/rods/" + rodDTO.getId())
                        .accept("application/json")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(rodDTO)));
    }
}