package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.carts.CartResponse;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelForCartResponse;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelsCartCreationRequest;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelsCartIncreaseAmountRequest;
import com.example.fishingshop.DTOs.carts.rodsCart.RodForCartResponse;
import com.example.fishingshop.DTOs.orders.OrderRequest;
import com.example.fishingshop.DTOs.orders.OrderResponse;
import com.example.fishingshop.DTOs.orders.ReelsForOrderResponse;
import com.example.fishingshop.DTOs.orders.RodsForOrderResponse;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.enums.Role;
import com.example.fishingshop.models.*;
import com.example.fishingshop.models.Order;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReelRepository reelRepository;

    @Autowired
    private RodRepository rodRepository;

    @Autowired
    private ReelsCartRepository reelsCartRepository;

    @Autowired
    private RodsCartRepository rodsCartRepository;

    @Autowired
    private ReelsOrderRepository reelsOrderRepository;

    @Autowired
    private RodsOrderRepository rodsOrderRepository;

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
        rodsCartRepository.deleteAll();
        reelsOrderRepository.deleteAll();
        rodsOrderRepository.deleteAll();
        rodRepository.deleteAll();
        reelRepository.deleteAll();
        orderRepository.deleteAll();
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

//        reelsCartRepository.save(ReelsCart.builder().reel(reel).user(user).amount(2).build());
//        rodsCartRepository.save(RodsCart.builder().rod(rod).user(user).amount(3).build());
    }

    @Test
    @SneakyThrows
    void list() {
        Reel reel = reelRepository.save(Reel.builder().name("reelName").price(30d).build());
        Rod rod = rodRepository.save(Rod.builder().name("rodName").price(40d).build());
        Order order = orderRepository.save(Order.builder()
                .address("address")
                .user(user)
                .totalPrice(180d)
                .build());
        reelsOrderRepository.save(ReelsOrder.builder().order(order).reel(reel).amount(2).build());
        rodsOrderRepository.save(RodsOrder.builder().order(order).rod(rod).amount(3).build());
        ReelDTO reelDTO = ReelDTO.builder()
                .id(1L)
                .name("reelName")
                .price(30d)
                .build();
        RodDTO rodDTO = RodDTO.builder()
                .id(1L)
                .name("rodName")
                .price(40d)
                .build();
        OrderResponse orderResponse = OrderResponse.builder()
                .reelsForOrderResponseList(List.of(ReelsForOrderResponse.builder()
                        .reel(reelDTO)
                        .amount(2)
                        .build()))
                .rodsForOrderResponseList(List.of(RodsForOrderResponse.builder()
                        .rod(rodDTO)
                        .amount(3)
                        .build()))
                .totalPrice(180d)
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/orders/")
                        .accept("application/json")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(List.of(orderResponse))));
    }

//    @Test
//    @SneakyThrows
//    void create() {
//        OrderRequest request = OrderRequest.builder().address("address").build();
//
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/orders/")
//                        .contentType("application/json")
//                        .content(new ObjectMapper().writeValueAsString(request))
//                        .accept("application/json"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Order is processed: date and time " + any().getClass().equals(LocalDateTime.class)));
//    }

    @Test
    @SneakyThrows
    void delete() {
        Order order = orderRepository.save(Order.builder()
                .address("address")
                .user(user)
                .build());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/orders/" + order.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Order with id " + order.getId() + " was deleted"));
    }
}