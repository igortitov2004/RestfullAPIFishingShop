package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.carts.CartResponse;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelForCartResponse;
import com.example.fishingshop.DTOs.carts.rodsCart.RodCartIncreaseAmountRequest;
import com.example.fishingshop.DTOs.carts.rodsCart.RodForCartResponse;
import com.example.fishingshop.DTOs.carts.rodsCart.RodsCartCreationRequest;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.enums.Role;
import com.example.fishingshop.models.*;
import com.example.fishingshop.repositories.*;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RodsCartRepository rodsCartRepository;

    @Autowired
    private ReelsCartRepository reelsCartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RodRepository rodRepository;

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

    @BeforeEach
    public void setData() {
        User user = User.builder()
                .email("testUser")
                .password(passwordEncoder.encode("123"))
                .role(Role.USER)
                .build();
        user = userRepository.save(user);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getUsername());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, Role.USER.getAuthorities()));

        Reel reel = reelRepository.save(Reel.builder().name("reelName").price(1d).build());
        Rod rod = rodRepository.save(Rod.builder().name("rodName").price(2d).build());

        reelsCartRepository.save(ReelsCart.builder().reel(reel).user(user).amount(2).build());
        rodsCartRepository.save(RodsCart.builder().rod(rod).user(user).amount(3).build());
    }

    @Test
    @SneakyThrows
    void list() {
        ReelDTO reelDTO = ReelDTO.builder()
                .id(1L)
                .name("reelName")
                .price(1d)
                .build();
        RodDTO rodDTO = RodDTO.builder()
                .id(1L)
                .name("rodName")
                .price(2d)
                .build();
        CartResponse cartResponse = CartResponse.builder()
                .reelForCartResponseList(List.of(ReelForCartResponse.builder()
                        .id(1L)
                        .reel(reelDTO)
                        .amount(2)
                        .build()))
                .rodForCartResponseList(List.of(RodForCartResponse.builder()
                        .id(1L)
                        .rod(rodDTO)
                        .amount(3)
                        .build()))
                .totalPrice(8d)
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/carts")
                        .accept("application/json")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(cartResponse)));
    }
}