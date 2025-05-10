package ma.zyn.app.unit.service.impl.admin.finance;

import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.dao.facade.core.finance.CompteLocataireDao;
import ma.zyn.app.service.impl.admin.finance.CompteLocataireAdminServiceImpl;

import ma.zyn.app.bean.core.finance.Banque ;
import ma.zyn.app.bean.core.locataire.Locataire ;
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
class CompteLocataireAdminServiceImplTest {

    @Mock
    private CompteLocataireDao repository;
    private AutoCloseable autoCloseable;
    private CompteLocataireAdminServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CompteLocataireAdminServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllCompteLocataire() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveCompteLocataire() {
        // Given
        CompteLocataire toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteCompteLocataire() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetCompteLocataireById() {
        // Given
        Long idToRetrieve = 1L; // Example CompteLocataire ID to retrieve
        CompteLocataire expected = new CompteLocataire(); // You need to replace CompteLocataire with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        CompteLocataire result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private CompteLocataire constructSample(int i) {
		CompteLocataire given = new CompteLocataire();
        given.setRib("rib-"+i);
        given.setBanque(new Banque(1L));
        given.setSolde(BigDecimal.TEN);
        given.setLocataire(new Locataire(1L));
        return given;
    }

}
