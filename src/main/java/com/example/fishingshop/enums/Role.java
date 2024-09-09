package com.example.fishingshop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.fishingshop.enums.Permission.*;

@RequiredArgsConstructor
public enum Role{
    ADMIN,USER
}
