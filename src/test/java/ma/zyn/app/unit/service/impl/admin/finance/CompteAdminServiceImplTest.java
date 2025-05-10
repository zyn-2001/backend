package ma.zyn.app.unit.service.impl.admin.finance;

import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.dao.facade.core.finance.CompteDao;
import ma.zyn.app.service.impl.admin.finance.CompteAdminServiceImpl;

import ma.zyn.app.bean.core.finance.Banque ;
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
class CompteAdminServiceImplTest {

    @Mock
    private CompteDao repository;
    private AutoCloseable autoCloseable;
    private CompteAdminServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CompteAdminServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllCompte() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveCompte() {
        // Given
        Compte toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteCompte() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetCompteById() {
        // Given
        Long idToRetrieve = 1L; // Example Compte ID to retrieve
        Compte expected = new Compte(); // You need to replace Compte with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Compte result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Compte constructSample(int i) {
		Compte given = new Compte();
        given.setRib("rib-"+i);
        given.setBanque(new Banque(1L));
        given.setSolde(BigDecimal.TEN);
        return given;
    }

}
