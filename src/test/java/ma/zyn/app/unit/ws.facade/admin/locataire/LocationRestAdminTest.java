package ma.zyn.app.unit.ws.facade.admin.locataire;

import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.service.impl.admin.locataire.LocationAdminServiceImpl;
import ma.zyn.app.ws.facade.admin.locataire.LocationRestAdmin;
import ma.zyn.app.ws.converter.locataire.LocationConverter;
import ma.zyn.app.ws.dto.locataire.LocationDto;
import org.aspectj.lang.annotation.Before;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationRestAdminTest {

    private MockMvc mockMvc;

    @Mock
    private LocationAdminServiceImpl service;
    @Mock
    private LocationConverter converter;

    @InjectMocks
    private LocationRestAdmin controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllLocationTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<LocationDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<LocationDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveLocationTest() throws Exception {
        // Mock data
        LocationDto requestDto = new LocationDto();
        Location entity = new Location();
        Location saved = new Location();
        LocationDto savedDto = new LocationDto();

        // Mock the converter to return the location object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved location DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<LocationDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        LocationDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved location DTO
        assertEquals(savedDto.getCode(), responseBody.getCode());
        assertEquals(savedDto.getDateCreation(), responseBody.getDateCreation());
    }

}
