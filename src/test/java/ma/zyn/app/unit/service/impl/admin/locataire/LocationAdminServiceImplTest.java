package ma.zyn.app.unit.service.impl.admin.locataire;

import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.dao.facade.core.locataire.LocationDao;
import ma.zyn.app.service.impl.admin.locataire.LocationAdminServiceImpl;

import ma.zyn.app.bean.core.locataire.Transaction ;
import ma.zyn.app.bean.core.finance.Compte ;
import ma.zyn.app.bean.core.finance.CompteLocataire ;
import ma.zyn.app.bean.core.locataire.TypeLocataire ;
import ma.zyn.app.bean.core.locataire.Locataire ;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class LocationAdminServiceImplTest {

    @Mock
    private LocationDao repository;
    private AutoCloseable autoCloseable;
    private LocationAdminServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new LocationAdminServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllLocation() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveLocation() {
        // Given
        Location toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteLocation() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetLocationById() {
        // Given
        Long idToRetrieve = 1L; // Example Location ID to retrieve
        Location expected = new Location(); // You need to replace Location with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Location result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Location constructSample(int i) {
		Location given = new Location();
        given.setCode("code-"+i);
        given.setLocataire(new Locataire(1L));
        given.setCompte(new Compte(1L));
        given.setCompteLocataire(new CompteLocataire(1L));
        given.setTransaction(new Transaction(1L));
        given.setTypeLocataire(new TypeLocataire(1L));
        given.setTypePaiement(new TypePaiement(1L));
        given.setDateCreation(LocalDateTime.now());
        return given;
    }

}
