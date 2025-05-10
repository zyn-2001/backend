package ma.zyn.app.unit.ws.facade.admin.locataire;

import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.service.impl.admin.locataire.TransactionAdminServiceImpl;
import ma.zyn.app.ws.facade.admin.locataire.TransactionRestAdmin;
import ma.zyn.app.ws.converter.locataire.TransactionConverter;
import ma.zyn.app.ws.dto.locataire.TransactionDto;
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
public class TransactionRestAdminTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionAdminServiceImpl service;
    @Mock
    private TransactionConverter converter;

    @InjectMocks
    private TransactionRestAdmin controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllTransactionTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<TransactionDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<TransactionDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveTransactionTest() throws Exception {
        // Mock data
        TransactionDto requestDto = new TransactionDto();
        Transaction entity = new Transaction();
        Transaction saved = new Transaction();
        TransactionDto savedDto = new TransactionDto();

        // Mock the converter to return the transaction object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved transaction DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<TransactionDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        TransactionDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved transaction DTO
        assertEquals(savedDto.getDate(), responseBody.getDate());
        assertEquals(savedDto.getMontant(), responseBody.getMontant());
        assertEquals(savedDto.getDescription(), responseBody.getDescription());
    }

}
