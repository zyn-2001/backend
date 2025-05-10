package ma.zyn.app.unit.service.impl.admin.finance;

import ma.zyn.app.bean.core.finance.Charge;
import ma.zyn.app.dao.facade.core.finance.ChargeDao;
import ma.zyn.app.service.impl.admin.finance.ChargeAdminServiceImpl;

import ma.zyn.app.bean.core.finance.Compte ;

import java.math.BigDecimal;
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
class ChargeAdminServiceImplTest {

    @Mock
    private ChargeDao repository;
    private AutoCloseable autoCloseable;
    private ChargeAdminServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ChargeAdminServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllCharge() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveCharge() {
        // Given
        Charge toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteCharge() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetChargeById() {
        // Given
        Long idToRetrieve = 1L; // Example Charge ID to retrieve
        Charge expected = new Charge(); // You need to replace Charge with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Charge result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Charge constructSample(int i) {
		Charge given = new Charge();
        given.setCode("code-"+i);
        given.setLibelle("libelle-"+i);
        given.setMontant(BigDecimal.TEN);
        given.setDate(LocalDateTime.now());
        given.setTypePaiement(new TypePaiement(1L));
        given.setCompte(new Compte(1L));
        given.setDescription("description-"+i);
        return given;
    }

}
