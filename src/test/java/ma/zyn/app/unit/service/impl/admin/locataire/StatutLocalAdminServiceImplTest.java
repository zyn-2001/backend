package ma.zyn.app.unit.service.impl.admin.locataire;

import ma.zyn.app.bean.core.locataire.StatutLocal;
import ma.zyn.app.dao.facade.core.locataire.StatutLocalDao;
import ma.zyn.app.service.impl.admin.locataire.StatutLocalAdminServiceImpl;

import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;



import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class StatutLocalAdminServiceImplTest {

    @Mock
    private StatutLocalDao repository;
    private AutoCloseable autoCloseable;
    private StatutLocalAdminServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new StatutLocalAdminServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllStatutLocal() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveStatutLocal() {
        // Given
        StatutLocal toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteStatutLocal() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetStatutLocalById() {
        // Given
        Long idToRetrieve = 1L; // Example StatutLocal ID to retrieve
        StatutLocal expected = new StatutLocal(); // You need to replace StatutLocal with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        StatutLocal result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private StatutLocal constructSample(int i) {
		StatutLocal given = new StatutLocal();
        given.setIndexation(i);
        given.setCode("code-"+i);
        given.setLabel("label-"+i);
        given.setStyle("style-"+i);
        given.setColor("color-"+i);
        return given;
    }

}
