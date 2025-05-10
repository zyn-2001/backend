package ma.zyn.app.unit.dao.facade.core.locataire;

import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.dao.facade.core.locataire.TransactionDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.time.LocalDateTime;

import ma.zyn.app.bean.core.finance.Compte ;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TransactionDaoTest {

@Autowired
    private TransactionDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }


    @Test
    void shouldFindById(){
        Long id = 1L;
        Transaction entity = new Transaction();
        entity.setId(id);
        underTest.save(entity);
        Transaction loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Transaction entity = new Transaction();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Transaction loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Transaction> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Transaction> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Transaction given = constructSample(1);
        Transaction saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
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
