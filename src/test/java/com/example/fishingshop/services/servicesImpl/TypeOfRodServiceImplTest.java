package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodCreationRequest;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodDTO;
import com.example.fishingshop.exceptions.typeOfRodExceptions.TypeOfRodAlreadyExistException;
import com.example.fishingshop.exceptions.typeOfRodExceptions.TypeOfRodIsNotExistException;
import com.example.fishingshop.models.TypeOfRod;
import com.example.fishingshop.repositories.TypeOfRodRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
class TypeOfRodServiceImplTest {
    @InjectMocks
    private TypeOfRodServiceImpl typeOfRodServiceImpl;
    @Mock
    private TypeOfRodRepository typeOfRodRepository;
    @Mock
    private ModelMapper modelMapper;
    @Test
    void list_whenTypeNotNull(){
        String type="type";
        List<TypeOfRod> typeOfRodList = new ArrayList<>();
        typeOfRodList.add(new TypeOfRod(1L,"type",null));
        TypeOfRodDTO typeOfRodDTO = new TypeOfRodDTO();
        typeOfRodDTO.setId(1L);
        typeOfRodDTO.setType("type");
        List<TypeOfRodDTO> expected = new ArrayList<>(List.of(typeOfRodDTO));

        Mockito.when(typeOfRodRepository.findTypeOfRodByTypeContaining(type)).thenReturn(typeOfRodList);
        Mockito.when(modelMapper.map(typeOfRodList.get(0),TypeOfRodDTO.class)).thenReturn(typeOfRodDTO);

        List<TypeOfRodDTO> actual = typeOfRodServiceImpl.list(type);

        assertEquals(expected,actual);
    }
    @Test
    void list_whenTypeNull(){
        List<TypeOfRod> typeOfRodList = new ArrayList<>();
        typeOfRodList.add(new TypeOfRod(1L,"type",null));
        TypeOfRodDTO typeOfRodDTO = new TypeOfRodDTO();
        typeOfRodDTO.setId(1L);
        typeOfRodDTO.setType("type");
        List<TypeOfRodDTO> expected = new ArrayList<>(List.of(typeOfRodDTO));

        Mockito.when(typeOfRodRepository.findAll()).thenReturn(typeOfRodList);
        Mockito.when(modelMapper.map(typeOfRodList.get(0),TypeOfRodDTO.class)).thenReturn(typeOfRodDTO);

        List<TypeOfRodDTO> actual = typeOfRodServiceImpl.list(null);

        assertEquals(expected,actual);
    }
    @Test
    void add_whenNotExists(){
        TypeOfRodCreationRequest request = new TypeOfRodCreationRequest();
        request.setType("type");
        TypeOfRod typeOfRod = new TypeOfRod();
        typeOfRod.setType(request.getType());

        Mockito.when(modelMapper.map(request,TypeOfRod.class)).thenReturn(typeOfRod);
        Mockito.when(typeOfRodRepository.existsTypeOfRodByType(request.getType())).thenReturn(false);

        typeOfRodServiceImpl.add(request);

        Mockito.verify(typeOfRodRepository,Mockito.times(1)).save(typeOfRod);
    }
    @Test
    void add_whenExists(){
        TypeOfRodCreationRequest request = new TypeOfRodCreationRequest();
        request.setType("type");

        Mockito.when(typeOfRodRepository.existsTypeOfRodByType(request.getType())).thenReturn(true);

        assertThrows(TypeOfRodAlreadyExistException.class,()->typeOfRodServiceImpl.add(request));
    }
    @Test
    void delete_whenExists(){
        Mockito.when(typeOfRodRepository.existsTypeOfRodById(1L)).thenReturn(true);

        typeOfRodServiceImpl.delete(1L);

        Mockito.verify(typeOfRodRepository,Mockito.times(1)).deleteById(1L);
    }
    @Test()
    void delete_whenNotExists(){
        Mockito.when(typeOfRodRepository.existsTypeOfRodById(1L)).thenReturn(false);

        assertThrows(TypeOfRodIsNotExistException.class, () -> typeOfRodServiceImpl.delete(1L));

        Mockito.verify(typeOfRodRepository,Mockito.times(0)).deleteById(1L);
    }
    @Test
    void edit_whenNotExistsById(){
        TypeOfRodDTO dto = new TypeOfRodDTO();
        dto.setId(1L);
        Mockito.when(typeOfRodRepository.existsTypeOfRodById(dto.getId())).thenReturn(false);
        assertThrows(TypeOfRodIsNotExistException.class,()->typeOfRodServiceImpl.edit(dto));
        Mockito.verify(typeOfRodRepository,Mockito.times(0)).save(any());
        Mockito.verify(typeOfRodRepository,Mockito.times(0)).findTypeOfRodByType(any());
    }
    @Test
    void edit_whenExistsByType(){
        TypeOfRodDTO dto = new TypeOfRodDTO();
        dto.setId(1L);
        dto.setType("type");
        TypeOfRod typeOfRod = new TypeOfRod();
        typeOfRod.setId(2L);
        typeOfRod.setType(dto.getType());

        Mockito.when(typeOfRodRepository.existsTypeOfRodById(dto.getId())).thenReturn(true);
        Mockito.when(typeOfRodRepository.findTypeOfRodByType(dto.getType())).thenReturn(typeOfRod);

        assertThrows(TypeOfRodAlreadyExistException.class,()->typeOfRodServiceImpl.edit(dto));

        Mockito.verify(typeOfRodRepository,Mockito.times(1)).findTypeOfRodByType(dto.getType());
        Mockito.verify(typeOfRodRepository,Mockito.times(0)).save(any());
    }
    @Test
    void edit_whenExists(){
        TypeOfRodDTO dto = new TypeOfRodDTO();
        dto.setId(1L);
        dto.setType("type");
        TypeOfRod typeOfRod = new TypeOfRod();
        typeOfRod.setId(1L);
        typeOfRod.setType("type");

        Mockito.when(typeOfRodRepository.existsTypeOfRodById(dto.getId())).thenReturn(true);
        Mockito.when(typeOfRodRepository.findTypeOfRodByType(dto.getType())).thenReturn(null);
        Mockito.when(modelMapper.map(dto,TypeOfRod.class)).thenReturn(typeOfRod);

        typeOfRodServiceImpl.edit(dto);

        Mockito.verify(typeOfRodRepository,Mockito.times(1)).findTypeOfRodByType(dto.getType());
        Mockito.verify(typeOfRodRepository,Mockito.times(1)).save(typeOfRodServiceImpl.mapToEntity(dto));
    }
    @Test
    void getById_whenExists() {
        TypeOfRodDTO typeOfRodDTO = new TypeOfRodDTO();
        typeOfRodDTO.setId(2L);
        TypeOfRod typeOfRod = new TypeOfRod();
        typeOfRod.setId(2L);

        Mockito.when(modelMapper.map(typeOfRod,TypeOfRodDTO.class)).thenReturn(typeOfRodDTO);
        Mockito.when(typeOfRodRepository.existsTypeOfRodById(2L)).thenReturn(true);
        Mockito.when(typeOfRodRepository.findById(2L)).thenReturn(Optional.of(typeOfRod));

        assertEquals(typeOfRodDTO, typeOfRodServiceImpl.getById(2L));
    }
    @Test
    void getById_whenNotExists() {
        TypeOfRodDTO dto = new TypeOfRodDTO();
        dto.setId(100L);

        Mockito.when(typeOfRodRepository.existsTypeOfRodById(dto.getId())).thenReturn(false);

        assertThrows(TypeOfRodIsNotExistException.class, () -> typeOfRodServiceImpl.getById(dto.getId()));

        Mockito.verify(typeOfRodRepository,Mockito.times(0)).findById(dto.getId());
    }
}