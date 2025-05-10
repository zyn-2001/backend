package ma.zyn.app.unit.service.impl.admin.locataire;

import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.dao.facade.core.locataire.TransactionDao;
import ma.zyn.app.service.impl.admin.locataire.TransactionAdminServiceImpl;

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
class TransactionAdminServiceImplTest {

    @Mock
    private TransactionDao repository;
    private AutoCloseable autoCloseable;
    private TransactionAdminServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new TransactionAdminServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllTransaction() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveTransaction() {
        // Given
        Transaction toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteTransaction() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetTransactionById() {
        // Given
        Long idToRetrieve = 1L; // Example Transaction ID to retrieve
        Transaction expected = new Transaction(); // You need to replace Transaction with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Transaction result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Transaction constructSample(int i) {
		Transaction given = new Transaction();
        given.setDate(LocalDateTime.now());
        given.setMontant(BigDecimal.TEN);
        given.setDescription("description-"+i);
        given.setModePaiement(new ModePaiement(1L));
        given.setTypePaiement(new TypePaiement(1L));
        given.setCompteSource(new Compte(1L));
        given.setCompteDestination(new Compte(1L));
        return given;
    }

}
