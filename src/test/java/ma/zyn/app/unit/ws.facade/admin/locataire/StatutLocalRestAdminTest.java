package ma.zyn.app.unit.ws.facade.admin.locataire;

import ma.zyn.app.bean.core.locataire.StatutLocal;
import ma.zyn.app.service.impl.admin.locataire.StatutLocalAdminServiceImpl;
import ma.zyn.app.ws.facade.admin.locataire.StatutLocalRestAdmin;
import ma.zyn.app.ws.converter.locataire.StatutLocalConverter;
import ma.zyn.app.ws.dto.locataire.StatutLocalDto;
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
public class StatutLocalRestAdminTest {

    private MockMvc mockMvc;

    @Mock
    private StatutLocalAdminServiceImpl service;
    @Mock
    private StatutLocalConverter converter;

    @InjectMocks
    private StatutLocalRestAdmin controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllStatutLocalTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<StatutLocalDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<StatutLocalDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveStatutLocalTest() throws Exception {
        // Mock data
        StatutLocalDto requestDto = new StatutLocalDto();
        StatutLocal entity = new StatutLocal();
        StatutLocal saved = new StatutLocal();
        StatutLocalDto savedDto = new StatutLocalDto();

        // Mock the converter to return the statutLocal object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved statutLocal DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<StatutLocalDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        StatutLocalDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved statutLocal DTO
        assertEquals(savedDto.getIndexation(), responseBody.getIndexation());
        assertEquals(savedDto.getCode(), responseBody.getCode());
        assertEquals(savedDto.getLabel(), responseBody.getLabel());
        assertEquals(savedDto.getStyle(), responseBody.getStyle());
        assertEquals(savedDto.getColor(), responseBody.getColor());
    }

}
