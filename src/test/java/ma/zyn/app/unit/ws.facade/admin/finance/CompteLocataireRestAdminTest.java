package ma.zyn.app.unit.ws.facade.admin.finance;

import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.service.impl.admin.finance.CompteLocataireAdminServiceImpl;
import ma.zyn.app.ws.facade.admin.finance.CompteLocataireRestAdmin;
import ma.zyn.app.ws.converter.finance.CompteLocataireConverter;
import ma.zyn.app.ws.dto.finance.CompteLocataireDto;
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
public class CompteLocataireRestAdminTest {

    private MockMvc mockMvc;

    @Mock
    private CompteLocataireAdminServiceImpl service;
    @Mock
    private CompteLocataireConverter converter;

    @InjectMocks
    private CompteLocataireRestAdmin controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllCompteLocataireTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<CompteLocataireDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<CompteLocataireDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveCompteLocataireTest() throws Exception {
        // Mock data
        CompteLocataireDto requestDto = new CompteLocataireDto();
        CompteLocataire entity = new CompteLocataire();
        CompteLocataire saved = new CompteLocataire();
        CompteLocataireDto savedDto = new CompteLocataireDto();

        // Mock the converter to return the compteLocataire object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved compteLocataire DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<CompteLocataireDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        CompteLocataireDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved compteLocataire DTO
        assertEquals(savedDto.getRib(), responseBody.getRib());
        assertEquals(savedDto.getSolde(), responseBody.getSolde());
    }

}
