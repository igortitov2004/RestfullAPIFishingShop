package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.manufacturer.ManufacturerCreationRequest;
import com.example.fishingshop.DTOs.manufacturer.ManufacturerDTO;
import com.example.fishingshop.DTOs.reel.ReelCreationRequest;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelDTO;
import com.example.fishingshop.exceptions.manufacturerExceptions.ManufacturerAlreadyExistException;
import com.example.fishingshop.exceptions.manufacturerExceptions.ManufacturerIsNotExistException;
import com.example.fishingshop.exceptions.reelExceptions.ReelAlreadyExistsException;
import com.example.fishingshop.exceptions.reelExceptions.ReelIsNotExistsException;
import com.example.fishingshop.models.Manufacturer;
import com.example.fishingshop.models.Reel;
import com.example.fishingshop.models.TypeOfReel;
import com.example.fishingshop.repositories.ReelRepository;
import com.example.fishingshop.services.TypeOfReelService;
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
class ReelServiceImplTest {
    @InjectMocks
    private ReelServiceImpl reelServiceImpl;
    @Mock
    private ReelRepository reelRepository;
    @Mock
    private TypeOfReelServiceImpl typeOfReelServiceImpl;
    @Mock
    private ManufacturerServiceImpl manufacturerServiceImpl;
    @Mock
    private ModelMapper modelMapper;
    @Test
    void list_whenTypeNotNull(){
        String name="name";
        List<Reel> reelList = new ArrayList<>();
        reelList.add(new Reel(1L,"name",1d,null,null,null,null));
        ReelDTO dto = new ReelDTO();
        dto.setId(1L);
        dto.setName("name");
        dto.setPrice(1d);
        dto.setType(null);
        dto.setManufacturer(null);
        List<ReelDTO> expected = new ArrayList<>(List.of(dto));

        Mockito.when(reelRepository.findReelByNameContaining(name)).thenReturn(reelList);
        Mockito.when(modelMapper.map(reelList.get(0),ReelDTO.class)).thenReturn(dto);

        List<ReelDTO> actual = reelServiceImpl.list(name);

        assertEquals(expected,actual);
    }
    @Test
    void list_whenTypeNull(){
        List<Reel> reelList = new ArrayList<>();
        reelList.add(new Reel(1L,"name",1d,null,null,null,null));
        ReelDTO dto = new ReelDTO();
        dto.setId(1L);
        dto.setName("name");
        dto.setPrice(1d);
        dto.setType(null);
        dto.setManufacturer(null);
        List<ReelDTO> expected = new ArrayList<>(List.of(dto));

        Mockito.when(reelRepository.findAll()).thenReturn(reelList);
        Mockito.when(modelMapper.map(reelList.get(0),ReelDTO.class)).thenReturn(dto);

        List<ReelDTO> actual = reelServiceImpl.list(null);

        assertEquals(expected,actual);
    }
    @Test
    void add_whenNotExists(){
        ReelCreationRequest request = new ReelCreationRequest();
        request.setName("name");
        request.setPrice(1d);
        request.setTypeId(1L);
        request.setManufacturerId(1L);

        Reel reel = new Reel();
        reel.setName(request.getName());
        reel.setPrice(request.getPrice());
        reel.setType(new TypeOfReel());
        reel.setManufacturer(new Manufacturer());

        ReelDTO reelDTO = new ReelDTO();
        reelDTO.setName(request.getName());
        reelDTO.setPrice(request.getPrice());
        reelDTO.setType(new TypeOfReelDTO());
        reelDTO.setManufacturer(new ManufacturerDTO());

        Mockito.when(reelRepository.existsReelByNameAndPriceAndTypeIdAndManufacturerId(
                request.getName(),
                request.getPrice(),
                request.getTypeId(),
                request.getManufacturerId())).thenReturn(false);
        Mockito.when(modelMapper.map(reelDTO,Reel.class)).thenReturn(reel);
        Mockito.when(typeOfReelServiceImpl.getById(request.getTypeId())).thenReturn(new TypeOfReelDTO());
        Mockito.when(manufacturerServiceImpl.getById(request.getManufacturerId())).thenReturn(new ManufacturerDTO());

        reelServiceImpl.add(request);

        Mockito.verify(reelRepository,Mockito.times(1)).save(reel);
    }
    @Test
    void add_whenExists(){
        ReelCreationRequest request = new ReelCreationRequest();
        request.setName("name");
        request.setPrice(1d);
        request.setTypeId(1L);
        request.setManufacturerId(1L);


        Mockito.when(reelRepository.existsReelByNameAndPriceAndTypeIdAndManufacturerId(
                request.getName(),
                request.getPrice(),
                request.getTypeId(),
                request.getManufacturerId())).thenReturn(true);

        assertThrows(ReelAlreadyExistsException.class,()-> reelServiceImpl.add(request));
    }
    @Test
    void delete_whenExists(){
        Reel reel = new Reel();
        reel.setId(100L);

        Mockito.when(reelRepository.existsReelById(reel.getId())).thenReturn(true);

        reelServiceImpl.delete(reel.getId());

        Mockito.verify(reelRepository,Mockito.times(1)).deleteById(reel.getId());
    }
    @Test()
    void delete_whenNotExists(){
        Reel reel = new Reel();
        reel.setId(1L);

        Mockito.when(reelRepository.existsReelById(reel.getId())).thenReturn(false);

        assertThrows(ReelIsNotExistsException.class, () -> reelServiceImpl.delete(reel.getId()));

        Mockito.verify(reelRepository,Mockito.times(0)).deleteById(reel.getId());
    }
    @Test
    void getById_whenExists() {
        ReelDTO dto = new ReelDTO();
        dto.setId(2L);
        Reel reel = new Reel();
        reel.setId(2L);

        Mockito.when(modelMapper.map(reel,ReelDTO.class)).thenReturn(dto);
        Mockito.when(reelRepository.existsReelById(2L)).thenReturn(true);
        Mockito.when(reelRepository.findById(2L)).thenReturn(Optional.of(reel));

        assertEquals(dto, reelServiceImpl.getById(2L));
    }
    @Test
    void getById_whenNotExists() {
        Reel reel = new Reel();
        reel.setId(100L);

        Mockito.when(reelRepository.existsReelById(reel.getId())).thenReturn(false);

        assertThrows(ReelIsNotExistsException.class, () -> reelServiceImpl.getById(reel.getId()));

        Mockito.verify(reelRepository,Mockito.times(0)).findById(reel.getId());
    }
}