package ma.zyn.app.unit.ws.facade.admin.locataire;

import ma.zyn.app.bean.core.locataire.TypeLocataire;
import ma.zyn.app.service.impl.admin.locataire.TypeLocataireAdminServiceImpl;
import ma.zyn.app.ws.facade.admin.locataire.TypeLocataireRestAdmin;
import ma.zyn.app.ws.converter.locataire.TypeLocataireConverter;
import ma.zyn.app.ws.dto.locataire.TypeLocataireDto;
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
public class TypeLocataireRestAdminTest {

    private MockMvc mockMvc;

    @Mock
    private TypeLocataireAdminServiceImpl service;
    @Mock
    private TypeLocataireConverter converter;

    @InjectMocks
    private TypeLocataireRestAdmin controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllTypeLocataireTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<TypeLocataireDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<TypeLocataireDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveTypeLocataireTest() throws Exception {
        // Mock data
        TypeLocataireDto requestDto = new TypeLocataireDto();
        TypeLocataire entity = new TypeLocataire();
        TypeLocataire saved = new TypeLocataire();
        TypeLocataireDto savedDto = new TypeLocataireDto();

        // Mock the converter to return the typeLocataire object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved typeLocataire DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<TypeLocataireDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        TypeLocataireDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved typeLocataire DTO
        assertEquals(savedDto.getIndexation(), responseBody.getIndexation());
        assertEquals(savedDto.getCode(), responseBody.getCode());
        assertEquals(savedDto.getLabel(), responseBody.getLabel());
        assertEquals(savedDto.getStyle(), responseBody.getStyle());
        assertEquals(savedDto.getColor(), responseBody.getColor());
    }

}
