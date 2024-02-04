package com.example.fishingshop.interfaces;

public interface Map <T,V>{
    T mapToDTO(V entity);
    V mapToEntity(T dto);
}
