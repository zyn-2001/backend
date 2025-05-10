package ma.zyn.app.unit.ws.facade.admin.locataire;

import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.service.impl.admin.locataire.LocataireAdminServiceImpl;
import ma.zyn.app.ws.facade.admin.locataire.LocataireRestAdmin;
import ma.zyn.app.ws.converter.locataire.LocataireConverter;
import ma.zyn.app.ws.dto.locataire.LocataireDto;
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
public class LocataireRestAdminTest {

    private MockMvc mockMvc;

    @Mock
    private LocataireAdminServiceImpl service;
    @Mock
    private LocataireConverter converter;

    @InjectMocks
    private LocataireRestAdmin controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllLocataireTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<LocataireDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<LocataireDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveLocataireTest() throws Exception {
        // Mock data
        LocataireDto requestDto = new LocataireDto();
        Locataire entity = new Locataire();
        Locataire saved = new Locataire();
        LocataireDto savedDto = new LocataireDto();

        // Mock the converter to return the locataire object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved locataire DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<LocataireDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        LocataireDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved locataire DTO
        assertEquals(savedDto.getCode(), responseBody.getCode());
        assertEquals(savedDto.getLibelle(), responseBody.getLibelle());
        assertEquals(savedDto.getNom(), responseBody.getNom());
        assertEquals(savedDto.getPrenom(), responseBody.getPrenom());
        assertEquals(savedDto.getDateCreation(), responseBody.getDateCreation());
    }

}
