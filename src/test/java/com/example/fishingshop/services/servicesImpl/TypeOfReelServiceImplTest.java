package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelCreationRequest;
import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelDTO;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodCreationRequest;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodDTO;
import com.example.fishingshop.exceptions.typeOfReelExceptions.TypeOfReelAlreadyExistsException;
import com.example.fishingshop.exceptions.typeOfReelExceptions.TypeOfReelIsNotExistsException;
import com.example.fishingshop.exceptions.typeOfRodExceptions.TypeOfRodAlreadyExistException;
import com.example.fishingshop.exceptions.typeOfRodExceptions.TypeOfRodIsNotExistException;
import com.example.fishingshop.models.TypeOfReel;
import com.example.fishingshop.models.TypeOfRod;
import com.example.fishingshop.repositories.TypeOfReelRepository;
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
class TypeOfReelServiceImplTest {
    @InjectMocks
    private TypeOfReelServiceImpl typeOfReelServiceImpl;
    @Mock
    private TypeOfReelRepository typeOfReelRepository;
    @Mock
    private ModelMapper modelMapper;
    @Test
    void list_whenTypeNotNull(){
        String type="type";
        List<TypeOfReel> typeOfReelList = new ArrayList<>();
        typeOfReelList.add(new TypeOfReel(1L,"type",null));
        TypeOfReelDTO typeOfReelDTO = new TypeOfReelDTO();
        typeOfReelDTO.setId(1L);
        typeOfReelDTO.setType("type");
        List<TypeOfReelDTO> expected = new ArrayList<>(List.of(typeOfReelDTO));

        Mockito.when(typeOfReelRepository.findTypeOfReelByTypeContaining(type)).thenReturn(typeOfReelList);
        Mockito.when(modelMapper.map(typeOfReelList.get(0),TypeOfReelDTO.class)).thenReturn(typeOfReelDTO);

        List<TypeOfReelDTO> actual = typeOfReelServiceImpl.list(type);

        assertEquals(expected,actual);
    }
    @Test
    void list_whenTypeNull(){
        List<TypeOfReel> typeOfReelList = new ArrayList<>();
        typeOfReelList.add(new TypeOfReel(1L,"type",null));
        TypeOfReelDTO typeOfReelDTO = new TypeOfReelDTO();
        typeOfReelDTO.setId(1L);
        typeOfReelDTO.setType("type");
        List<TypeOfReelDTO> expected = new ArrayList<>(List.of(typeOfReelDTO));

        Mockito.when(typeOfReelRepository.findAll()).thenReturn(typeOfReelList);
        Mockito.when(modelMapper.map(typeOfReelList.get(0),TypeOfReelDTO.class)).thenReturn(typeOfReelDTO);

        List<TypeOfReelDTO> actual = typeOfReelServiceImpl.list(null);

        assertEquals(expected,actual);
    }
    @Test
    void add_whenNotExists(){
        TypeOfReelCreationRequest request = new TypeOfReelCreationRequest();
        request.setType("type");
        TypeOfReel typeOfReel = new TypeOfReel();
        typeOfReel.setType(request.getType());

        Mockito.when(modelMapper.map(request,TypeOfReel.class)).thenReturn(typeOfReel);
        Mockito.when(typeOfReelRepository.existsTypeOfReelByType(request.getType())).thenReturn(false);

        typeOfReelServiceImpl.add(request);

        Mockito.verify(typeOfReelRepository,Mockito.times(1)).save(typeOfReel);
    }
    @Test
    void add_whenExists(){
        TypeOfReelCreationRequest request = new TypeOfReelCreationRequest();
        request.setType("type");

        Mockito.when(typeOfReelRepository.existsTypeOfReelByType(request.getType())).thenReturn(true);

        assertThrows(TypeOfReelAlreadyExistsException.class,()-> typeOfReelServiceImpl.add(request));
    }
    @Test
    void delete_whenExists(){
        TypeOfReel typeOfReel = new TypeOfReel();
        typeOfReel.setId(100L);

        Mockito.when(typeOfReelRepository.existsTypeOfReelById(typeOfReel.getId())).thenReturn(true);

        typeOfReelServiceImpl.delete(typeOfReel.getId());

        Mockito.verify(typeOfReelRepository,Mockito.times(1)).deleteById(typeOfReel.getId());
    }
    @Test()
    void delete_whenNotExists(){
        TypeOfReel typeOfReel = new TypeOfReel();
        typeOfReel.setId(1L);

        Mockito.when(typeOfReelRepository.existsTypeOfReelById(typeOfReel.getId())).thenReturn(false);

        assertThrows(TypeOfReelIsNotExistsException.class, () -> typeOfReelServiceImpl.delete(typeOfReel.getId()));

        Mockito.verify(typeOfReelRepository,Mockito.times(0)).deleteById(typeOfReel.getId());
    }
    @Test
    void edit_whenNotExistsById(){
        TypeOfReelDTO dto = new TypeOfReelDTO();
        dto.setId(1L);
        Mockito.when(typeOfReelRepository.existsTypeOfReelById(dto.getId())).thenReturn(false);
        assertThrows(TypeOfReelIsNotExistsException.class,()-> typeOfReelServiceImpl.edit(dto));
        Mockito.verify(typeOfReelRepository,Mockito.times(0)).save(any());
        Mockito.verify(typeOfReelRepository,Mockito.times(0)).findTypeOfReelByType(any());
    }
    @Test
    void edit_whenExistsByType(){
        TypeOfReelDTO dto = new TypeOfReelDTO();
        dto.setId(1L);
        dto.setType("type");
        TypeOfReel typeOfReel = new TypeOfReel();
        typeOfReel.setId(2L);
        typeOfReel.setType(dto.getType());

        Mockito.when(typeOfReelRepository.existsTypeOfReelById(dto.getId())).thenReturn(true);
        Mockito.when(typeOfReelRepository.findTypeOfReelByType(dto.getType())).thenReturn(typeOfReel);

        assertThrows(TypeOfReelAlreadyExistsException.class,()-> typeOfReelServiceImpl.edit(dto));

        Mockito.verify(typeOfReelRepository,Mockito.times(1)).findTypeOfReelByType(dto.getType());
        Mockito.verify(typeOfReelRepository,Mockito.times(0)).save(any());
    }
    @Test
    void edit_whenExists(){
        TypeOfReelDTO dto = new TypeOfReelDTO();
        dto.setId(1L);
        dto.setType("type");
        TypeOfReel typeOfReel = new TypeOfReel();
        typeOfReel.setId(1L);
        typeOfReel.setType("type");

        Mockito.when(typeOfReelRepository.existsTypeOfReelById(dto.getId())).thenReturn(true);
        Mockito.when(typeOfReelRepository.findTypeOfReelByType(dto.getType())).thenReturn(null);
        Mockito.when(modelMapper.map(dto,TypeOfReel.class)).thenReturn(typeOfReel);

        typeOfReelServiceImpl.edit(dto);

        Mockito.verify(typeOfReelRepository,Mockito.times(1)).findTypeOfReelByType(dto.getType());
        Mockito.verify(typeOfReelRepository,Mockito.times(1)).save(typeOfReelServiceImpl.mapToEntity(dto));
    }
    @Test
    void getById_whenExists() {
        TypeOfReelDTO typeOfReelDTO = new TypeOfReelDTO();
        typeOfReelDTO.setId(2L);
        TypeOfReel typeOfReel = new TypeOfReel();
        typeOfReel.setId(2L);

        Mockito.when(modelMapper.map(typeOfReel,TypeOfReelDTO.class)).thenReturn(typeOfReelDTO);
        Mockito.when(typeOfReelRepository.existsTypeOfReelById(2L)).thenReturn(true);
        Mockito.when(typeOfReelRepository.findById(2L)).thenReturn(Optional.of(typeOfReel));

        assertEquals(typeOfReelDTO, typeOfReelServiceImpl.getById(2L));
    }
    @Test
    void getById_whenNotExists() {
        TypeOfReelDTO dto = new TypeOfReelDTO();
        dto.setId(100L);

        Mockito.when(typeOfReelRepository.existsTypeOfReelById(dto.getId())).thenReturn(false);

        assertThrows(TypeOfReelIsNotExistsException.class, () -> typeOfReelServiceImpl.getById(dto.getId()));

        Mockito.verify(typeOfReelRepository,Mockito.times(0)).findById(dto.getId());
    }
}