package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.carts.rodsCart.RodCartIncreaseAmountRequest;
import com.example.fishingshop.DTOs.carts.rodsCart.RodForCartResponse;
import com.example.fishingshop.DTOs.carts.rodsCart.RodsCartCreationRequest;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.enums.Role;
import com.example.fishingshop.models.Rod;
import com.example.fishingshop.models.RodsCart;
import com.example.fishingshop.models.User;
import com.example.fishingshop.repositories.RodRepository;
import com.example.fishingshop.repositories.RodsCartRepository;
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
class RodsCartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RodsCartRepository rodsCartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RodRepository rodRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsService userDetailsService;

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
        rodsCartRepository.deleteAll();
        rodRepository.deleteAll();
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
        Rod rod = rodRepository.save(Rod.builder().build());

        RodsCart rodsCart = rodsCartRepository.save(RodsCart.builder()
                .rod(rod)
                .amount(1)
                .user(user)
                .build());
        RodForCartResponse response = RodForCartResponse.builder()
                .id(rodsCart.getId())
                .rod(RodDTO.builder()
                        .id(rod.getId())
                        .build())
                .amount(1)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/rodsCarts/")
                        .accept("application/json")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(List.of(response))));
    }

    @Test
    @SneakyThrows
    void update() {
        Rod rod = rodRepository.save(Rod.builder().name("name").build());
        RodsCart rodsCart = rodsCartRepository.save(RodsCart.builder()
                .rod(rod)
                .user(user)
                .amount(1)
                .build());
        RodCartIncreaseAmountRequest request = new RodCartIncreaseAmountRequest();
        request.setId(rodsCart.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/rodsCarts/edit")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Data of rod with id " + request.getId() + " was updated"));
    }

    @Test
    @SneakyThrows
    void create() {
        Rod rod = rodRepository.save(Rod.builder().name("name").build());
        RodsCartCreationRequest request = new RodsCartCreationRequest();
        request.setRodId(rod.getId());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/rodsCarts/")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Rod with id " + request.getRodId() + " added to cart"));
    }

    @Test
    @SneakyThrows
    void delete() {
        RodsCart rodsCart = rodsCartRepository.save(RodsCart.builder()
                .user(null)
                .rod(null)
                .amount(1)
                .build());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/rodsCarts/" + rodsCart.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Rods cart with id " + rodsCart.getId() + " deleted from cart"));
    }

    @Test
    @SneakyThrows
    void deleteByUserId() {
        rodsCartRepository.save(RodsCart.builder()
                .user(user)
                .rod(null)
                .amount(1)
                .build());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/rodsCarts/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("Rods cart(s) with user id " + user.getId() + " deleted from cart"));
    }
}