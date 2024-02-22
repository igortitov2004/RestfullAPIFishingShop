package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.carts.reelsCart.ReelForCartResponse;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelsCartCreationRequest;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelsCartIncreaseAmountRequest;
import com.example.fishingshop.DTOs.carts.rodsCart.RodCartIncreaseAmountRequest;
import com.example.fishingshop.DTOs.carts.rodsCart.RodForCartResponse;
import com.example.fishingshop.DTOs.carts.rodsCart.RodsCartCreationRequest;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.enums.Role;
import com.example.fishingshop.models.*;
import com.example.fishingshop.repositories.ReelRepository;
import com.example.fishingshop.repositories.ReelsCartRepository;
import com.example.fishingshop.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class ReelsCartControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ReelsCartRepository reelsCartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReelRepository reelRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

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
        SecurityContextHolder.getContext().setAuthentication(null);
        reelsCartRepository.deleteAll();
        reelRepository.deleteAll();
        userRepository.deleteAll();
    }

    User user;

    @BeforeEach
    public void setData() {
        user = User.builder()
                .email("testUser")
                .password(passwordEncoder.encode("123"))
                .role(Role.USER)
                .build();
        user = userRepository.save(user);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getUsername());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, Role.USER.getAuthorities()));
    }


    @Test
    @SneakyThrows
    void list() {
        Reel reel = reelRepository.save(Reel.builder().build());

        ReelsCart reelsCart = reelsCartRepository.save(ReelsCart.builder()
                .reel(reel)
                .amount(1)
                .user(user)
                .build());
        ReelForCartResponse response = ReelForCartResponse.builder()
                .id(reelsCart.getId())
                .reel(ReelDTO.builder()
                        .id(reel.getId())
                        .build())
                .amount(1)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/reelsCarts/")
                        .accept("application/json")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(List.of(response))));
    }

    @Test
    @SneakyThrows
    void update() {
        Reel reel = reelRepository.save(Reel.builder().name("name").build());
        ReelsCart reelsCart = reelsCartRepository.save(ReelsCart.builder()
                .reel(reel)
                .user(user)
                .amount(1)
                .build());
        ReelsCartIncreaseAmountRequest request = new ReelsCartIncreaseAmountRequest();
        request.setId(reelsCart.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/reelsCarts/edit")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Data of reel with id " + request.getId() + " was updated"));
    }

    @Test
    @SneakyThrows
    void create() {
        Reel reel = reelRepository.save(Reel.builder().name("name").build());
        ReelsCartCreationRequest request = new ReelsCartCreationRequest();
        request.setReelId(reel.getId());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reelsCarts/")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reel with id " + request.getReelId() + " added to cart"));
    }

    @Test
    @SneakyThrows
    void delete() {
        ReelsCart reelsCart = reelsCartRepository.save(ReelsCart.builder()
                .user(null)
                .reel(null)
                .amount(1)
                .build());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reelsCarts/" + reelsCart.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Reels cart with id " + reelsCart.getId() + " deleted from cart"));
    }

    @Test
    @SneakyThrows
    void deleteByUserId() {
        reelsCartRepository.save(ReelsCart.builder()
                .user(user)
                .reel(null)
                .amount(1)
                .build());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reelsCarts/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reels cart(s) with user id " + user.getId() + " deleted from cart"));
    }
}