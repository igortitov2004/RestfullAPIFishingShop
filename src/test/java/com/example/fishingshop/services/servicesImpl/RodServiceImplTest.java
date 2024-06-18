package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.manufacturer.ManufacturerDTO;
import com.example.fishingshop.DTOs.reel.ReelCreationRequest;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.reel.ReelEditRequest;
import com.example.fishingshop.DTOs.rod.RodCreationRequest;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.DTOs.rod.RodEditRequest;
import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelDTO;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodDTO;
import com.example.fishingshop.exceptions.reelExceptions.ReelAlreadyExistsException;
import com.example.fishingshop.exceptions.reelExceptions.ReelIsNotExistsException;
import com.example.fishingshop.exceptions.rodExceptions.RodAlreadyExistsException;
import com.example.fishingshop.exceptions.rodExceptions.RodIsNotExistException;
import com.example.fishingshop.models.*;
import com.example.fishingshop.repositories.ImageRodsLinkRepository;
import com.example.fishingshop.repositories.RodRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class RodServiceImplTest {

    @InjectMocks
    private RodServiceImpl rodServiceImpl;
    @Mock
    private RodRepository rodRepository;
    @Mock
    private TypeOfRodServiceImpl typeOfRodServiceImpl;
    @Mock
    private ManufacturerServiceImpl manufacturerServiceImpl;
    @Mock
    private ImageRodsLinkRepository imageRodsLinkRepository;
    @Mock
    private ModelMapper modelMapper;
    @Test
    void list_whenTypeNotNull(){
        String name="name";
        List<Rod> rodList = new ArrayList<>();
        rodList.add(Rod.builder()
                .name("name")
                .price(1d)
                .testLoad(1)
                .length(1)
                .weight(1)
                .type(new TypeOfRod())
                .manufacturer(new Manufacturer())
                .build());
        RodDTO dto = RodDTO.builder()
                .name("name")
                .price(1d)
                .testLoad(1)
                .length(1)
                .weight(1)
                .type(new TypeOfRodDTO())
                .manufacturer(new ManufacturerDTO())
                .build();
        List<RodDTO> expected = new ArrayList<>(List.of(dto));

        Mockito.when(rodRepository.findRodByNameContaining(name)).thenReturn(rodList);
        Mockito.when(modelMapper.map(rodList.get(0),RodDTO.class)).thenReturn(dto);

        List<RodDTO> actual = rodServiceImpl.list(name);

        assertEquals(expected,actual);
    }
    @Test
    void list_whenTypeNull(){
        List<Rod> rodList = new ArrayList<>();
        rodList.add(Rod.builder()
                .name("name")
                .price(1d)
                .testLoad(1)
                .length(1)
                .weight(1)
                .type(new TypeOfRod())
                .manufacturer(new Manufacturer())
                .build());
        RodDTO dto = RodDTO.builder()
                .name("name")
                .price(1d)
                .testLoad(1)
                .length(1)
                .weight(1)
                .type(new TypeOfRodDTO())
                .manufacturer(new ManufacturerDTO())
                .build();
        List<RodDTO> expected = new ArrayList<>(List.of(dto));

        Mockito.when(rodRepository.findAll()).thenReturn(rodList);
        Mockito.when(modelMapper.map(rodList.get(0),RodDTO.class)).thenReturn(dto);

        List<RodDTO> actual = rodServiceImpl.list(null);

        assertEquals(expected,actual);
    }
    @Test
    void add_whenNotExists(){
        RodCreationRequest request = RodCreationRequest.builder()
                .name("name")
                .price(1d)
                .testLoad(1)
                .length(1)
                .weight(1)
                .typeId(1L)
                .manufacturerId(1L)
                .build();

        Rod rod = Rod.builder()
                .name("name")
                .price(1d)
                .testLoad(1)
                .length(1)
                .weight(1)
                .type(new TypeOfRod())
                .manufacturer(new Manufacturer())
                .build();

        RodDTO rodDTO = RodDTO.builder()
                .name("name")
                .price(1d)
                .testLoad(1)
                .length(1)
                .weight(1)
                .type(new TypeOfRodDTO())
                .manufacturer(new ManufacturerDTO())
                .build();

        Mockito.when(rodRepository.existsRodByNameAndLengthAndWeightAndTestLoadAndPriceAndTypeIdAndManufacturerId(
                request.getName(),
                request.getLength(),
                request.getWeight(),
                request.getTestLoad(),
                request.getPrice(),
                request.getTypeId(),
                request.getManufacturerId())).thenReturn(false);
        Mockito.when(modelMapper.map(rodDTO,Rod.class)).thenReturn(rod);
        Mockito.when(typeOfRodServiceImpl.getById(request.getTypeId())).thenReturn(new TypeOfRodDTO());
        Mockito.when(manufacturerServiceImpl.getById(request.getManufacturerId())).thenReturn(new ManufacturerDTO());

        rodServiceImpl.add(request);
        Mockito.verify(imageRodsLinkRepository,Mockito.times(1)).save(any());
        Mockito.verify(rodRepository,Mockito.times(1)).save(rod);
    }
    @Test
    void add_whenExists(){
        RodCreationRequest request = RodCreationRequest.builder()
                .name("name")
                .price(1d)
                .testLoad(1)
                .length(1)
                .weight(1)
                .typeId(1L)
                .manufacturerId(1L)
                .build();
        Mockito.when(rodRepository.existsRodByNameAndLengthAndWeightAndTestLoadAndPriceAndTypeIdAndManufacturerId(
                request.getName(),
                request.getLength(),
                request.getWeight(),
                request.getTestLoad(),
                request.getPrice(),
                request.getTypeId(),
                request.getManufacturerId())).thenReturn(true);

        assertThrows(RodAlreadyExistsException.class,()-> rodServiceImpl.add(request));
    }

    @Test
    void edit_whenNotExistsById(){
        RodEditRequest request = RodEditRequest.builder()
                .id(1L)
                .build();
        Mockito.when(rodRepository.existsRodById(request.getId())).thenReturn(false);

        assertThrows(RodIsNotExistException.class,()->rodServiceImpl.edit(new RodEditRequest()));

        Mockito.verify(rodRepository,Mockito.times(0)).save(any());
    }
    @Test
    void edit_whenSuchReelExists(){
        RodEditRequest request = RodEditRequest.builder()
                .id(1L)
                .name("name")
                .price(1d)
                .manufacturerId(1L)
                .build();
        RodDTO dto = RodDTO.builder()
                .id(1L)
                .name("name")
                .length(1)
                .testLoad(1)
                .weight(1)
                .price(1d)
                .manufacturer(new ManufacturerDTO())
                .type(new TypeOfRodDTO())
                .build();
        Rod rod = Rod.builder()
                .id(1L)
                .name("name")
                .length(1)
                .testLoad(1)
                .weight(1)
                .price(1d)
                .manufacturer(new Manufacturer())
                .type(new TypeOfRod())
                .build();
        Rod rodForOptional = Rod.builder()
                .id(2L)
                .name("name")
                .length(1)
                .testLoad(1)
                .weight(1)
                .price(1d)
                .manufacturer(new Manufacturer())
                .type(new TypeOfRod())
                .build();
        Mockito.when(rodRepository.existsRodById(request.getId())).thenReturn(true);
        Mockito.when(rodRepository.findById(request.getId())).thenReturn(Optional.ofNullable(rod));
        Mockito.when(modelMapper.map(rod,RodDTO.class)).thenReturn(dto);
        Mockito.when(rodRepository.findRodByNameAndLengthAndWeightAndTestLoadAndPriceAndTypeIdAndManufacturerId(
                request.getName(),
                dto.getLength(),
                dto.getWeight(),
                dto.getTestLoad(),
                request.getPrice(),
                dto.getType().getId(),
                request.getManufacturerId())).thenReturn(Optional.ofNullable(rodForOptional));
        assertThrows(RodAlreadyExistsException.class,()->rodServiceImpl.edit(request));
    }
    @Test
    void edit_whenSuchReelIsNotExists(){
        RodEditRequest request = RodEditRequest.builder()
                .id(1L)
                .name("name")
                .price(1d)
                .manufacturerId(1L)
                .build();
        RodDTO dto = RodDTO.builder()
                .id(1L)
                .name("name")
                .length(1)
                .testLoad(1)
                .weight(1)
                .price(1d)
                .manufacturer(new ManufacturerDTO())
                .type(new TypeOfRodDTO())
                .build();
        Rod rod = Rod.builder()
                .id(1L)
                .name("name")
                .length(1)
                .testLoad(1)
                .weight(1)
                .price(1d)
                .manufacturer(new Manufacturer())
                .type(new TypeOfRod())
                .build();

        Mockito.when(rodRepository.existsRodById(request.getId())).thenReturn(true);
        Mockito.when(rodRepository.findById(request.getId())).thenReturn(Optional.ofNullable(rod));
        Mockito.when(modelMapper.map(rod,RodDTO.class)).thenReturn(dto);
        Mockito.when(rodRepository.findRodByNameAndLengthAndWeightAndTestLoadAndPriceAndTypeIdAndManufacturerId(
                request.getName(),
                dto.getLength(),
                dto.getWeight(),
                dto.getTestLoad(),
                request.getPrice(),
                dto.getType().getId(),
                request.getManufacturerId())).thenReturn(Optional.ofNullable(rod));
        Mockito.when(manufacturerServiceImpl.getById(1L)).thenReturn(new ManufacturerDTO());
        Mockito.when(modelMapper.map(dto,Rod.class)).thenReturn(rod);

        rodServiceImpl.edit(request);

        assertNotNull(rod);

        Mockito.verify(rodRepository,Mockito.times(1)).save(rod);
    }
    @Test
    void delete_whenExists(){


        Mockito.when(rodRepository.existsRodById(1L)).thenReturn(true);

        rodServiceImpl.delete(1L);

        Mockito.verify(rodRepository,Mockito.times(1)).deleteById(1L);
    }
    @Test
    void delete_whenNotExists(){
        Mockito.when(rodRepository.existsRodById(1L)).thenReturn(false);

        assertThrows(RodIsNotExistException.class, () -> rodServiceImpl.delete(1L));

        Mockito.verify(rodRepository,Mockito.times(0)).deleteById(1L);
    }
    @Test
    void getById_whenExists() {
        RodDTO dto = new RodDTO();
        dto.setId(2L);
        Rod rod = new Rod();
        rod.setId(2L);

        Mockito.when(modelMapper.map(rod,RodDTO.class)).thenReturn(dto);
        Mockito.when(rodRepository.existsRodById(2L)).thenReturn(true);
        Mockito.when(rodRepository.findById(2L)).thenReturn(Optional.of(rod));

        assertEquals(dto, rodServiceImpl.getById(2L));
    }
    @Test
    void getById_whenNotExists() {
        Rod rod = new Rod();
        rod.setId(100L);

        Mockito.when(rodRepository.existsRodById(rod.getId())).thenReturn(false);

        assertThrows(RodIsNotExistException.class, () -> rodServiceImpl.getById(rod.getId()));

        Mockito.verify(rodRepository,Mockito.times(0)).findById(rod.getId());
    }
}