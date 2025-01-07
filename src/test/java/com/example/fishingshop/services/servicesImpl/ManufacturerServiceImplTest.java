package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.manufacturer.ManufacturerCreationRequest;
import com.example.fishingshop.DTOs.manufacturer.ManufacturerDTO;
import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelCreationRequest;
import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelDTO;
import com.example.fishingshop.exceptions.manufacturerExceptions.ManufacturerAlreadyExistException;
import com.example.fishingshop.exceptions.manufacturerExceptions.ManufacturerIsNotExistException;
import com.example.fishingshop.exceptions.typeOfReelExceptions.TypeOfReelAlreadyExistsException;
import com.example.fishingshop.exceptions.typeOfReelExceptions.TypeOfReelIsNotExistsException;
import com.example.fishingshop.models.Manufacturer;
import com.example.fishingshop.models.TypeOfReel;
import com.example.fishingshop.repositories.ManufacturerRepository;
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
class  ManufacturerServiceImplTest {
    @InjectMocks
    private ManufacturerServiceImpl manufacturerServiceImpl;
    @Mock
    private ManufacturerRepository manufacturerRepository;
    @Mock
    private ModelMapper modelMapper;
    @Test
    void list_whenTypeNotNull(){
        String name="name";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        manufacturerList.add(new Manufacturer(1L,"name",null,null));
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO();
        manufacturerDTO.setId(1L);
        manufacturerDTO.setName("name");
        List<ManufacturerDTO> expected = new ArrayList<>(List.of(manufacturerDTO));

        Mockito.when(manufacturerRepository.findManufacturerByNameContaining(name)).thenReturn(manufacturerList);
        Mockito.when(modelMapper.map(manufacturerList.get(0),ManufacturerDTO.class)).thenReturn(manufacturerDTO);

        List<ManufacturerDTO> actual = manufacturerServiceImpl.list(name);

        assertEquals(expected,actual);
    }
    @Test
    void list_whenTypeNull(){
        List<Manufacturer> manufacturerList = new ArrayList<>();
        manufacturerList.add(new Manufacturer(1L,"name",null,null));
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO();
        manufacturerDTO.setId(1L);
        manufacturerDTO.setName("name");
        List<ManufacturerDTO> expected = new ArrayList<>(List.of(manufacturerDTO));

        Mockito.when(manufacturerRepository.findAll()).thenReturn(manufacturerList);
        Mockito.when(modelMapper.map(manufacturerList.get(0),ManufacturerDTO.class)).thenReturn(manufacturerDTO);

        List<ManufacturerDTO> actual = manufacturerServiceImpl.list(null);

        assertEquals(expected,actual);
    }
    @Test
    void add_whenNotExists(){
        ManufacturerCreationRequest request = new ManufacturerCreationRequest();
        request.setName("name");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(request.getName());

        Mockito.when(modelMapper.map(request,Manufacturer.class)).thenReturn(manufacturer);
        Mockito.when(manufacturerRepository.existsManufacturersByName(request.getName())).thenReturn(false);

        manufacturerServiceImpl.add(request);

        Mockito.verify(manufacturerRepository,Mockito.times(1)).save(manufacturer);
    }
    @Test
    void add_whenExists(){
        ManufacturerCreationRequest request = new ManufacturerCreationRequest();
        request.setName("name");

        Mockito.when(manufacturerRepository.existsManufacturersByName(request.getName())).thenReturn(true);

        assertThrows(ManufacturerAlreadyExistException.class,()-> manufacturerServiceImpl.add(request));
    }
    @Test
    void delete_whenExists(){
        Mockito.when(manufacturerRepository.existsManufacturersById(1L)).thenReturn(true);

        manufacturerServiceImpl.delete(1L);

        Mockito.verify(manufacturerRepository,Mockito.times(1)).deleteById(1L);
    }
    @Test()
    void delete_whenNotExists(){
        Mockito.when(manufacturerRepository.existsManufacturersById(1L)).thenReturn(false);

        assertThrows(ManufacturerIsNotExistException.class, () -> manufacturerServiceImpl.delete(1L));

        Mockito.verify(manufacturerRepository,Mockito.times(0)).deleteById(1L);
    }
    @Test
    void edit_whenNotExistsById(){
        ManufacturerDTO dto = new ManufacturerDTO();
        dto.setId(1L);
        Mockito.when(manufacturerRepository.existsManufacturersById(dto.getId())).thenReturn(false);
        assertThrows(ManufacturerIsNotExistException.class,()-> manufacturerServiceImpl.edit(dto));
        Mockito.verify(manufacturerRepository,Mockito.times(0)).save(any());
        Mockito.verify(manufacturerRepository,Mockito.times(0)).findManufacturerByName(any());
    }
    @Test
    void edit_whenExistsByName(){
        ManufacturerDTO dto = new ManufacturerDTO();
        dto.setId(1L);
        dto.setName("name");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(2L);
        manufacturer.setName(dto.getName());

        Mockito.when(manufacturerRepository.existsManufacturersById(dto.getId())).thenReturn(true);
        Mockito.when(manufacturerRepository.findManufacturerByName(dto.getName())).thenReturn(manufacturer);

        assertThrows(ManufacturerAlreadyExistException.class,()-> manufacturerServiceImpl.edit(dto));

        Mockito.verify(manufacturerRepository,Mockito.times(1)).findManufacturerByName(dto.getName());
        Mockito.verify(manufacturerRepository,Mockito.times(0)).save(any());
    }
    @Test
    void edit_whenExists(){
        ManufacturerDTO dto = new ManufacturerDTO();
        dto.setId(1L);
        dto.setName("name");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1L);
        manufacturer.setName("name");

        Mockito.when(manufacturerRepository.existsManufacturersById(dto.getId())).thenReturn(true);
        Mockito.when(manufacturerRepository.findManufacturerByName(dto.getName())).thenReturn(null);
        Mockito.when(modelMapper.map(dto,Manufacturer.class)).thenReturn(manufacturer);

        manufacturerServiceImpl.edit(dto);

        Mockito.verify(manufacturerRepository,Mockito.times(1)).findManufacturerByName(dto.getName());
        Mockito.verify(manufacturerRepository,Mockito.times(1)).save(manufacturerServiceImpl.mapToEntity(dto));
    }
    @Test
    void getById_whenExists() {
        ManufacturerDTO dto = new ManufacturerDTO();
        dto.setId(2L);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(2L);

        Mockito.when(modelMapper.map(manufacturer,ManufacturerDTO.class)).thenReturn(dto);
        Mockito.when(manufacturerRepository.existsManufacturersById(2L)).thenReturn(true);
        Mockito.when(manufacturerRepository.findById(2L)).thenReturn(Optional.of(manufacturer));

        assertEquals(dto, manufacturerServiceImpl.getById(2L));
    }
    @Test
    void getById_whenNotExists() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(100L);

        Mockito.when(manufacturerRepository.existsManufacturersById(manufacturer.getId())).thenReturn(false);

        assertThrows(ManufacturerIsNotExistException.class, () -> manufacturerServiceImpl.getById(manufacturer.getId()));

        Mockito.verify(manufacturerRepository,Mockito.times(0)).findById(manufacturer.getId());
    }
}