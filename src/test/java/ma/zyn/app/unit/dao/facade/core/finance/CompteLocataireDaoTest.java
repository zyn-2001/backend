package ma.zyn.app.unit.dao.facade.core.finance;

import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.dao.facade.core.finance.CompteLocataireDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.IntStream;
import java.time.LocalDateTime;

import ma.zyn.app.bean.core.finance.Banque ;
import ma.zyn.app.bean.core.locataire.Locataire ;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CompteLocataireDaoTest {

@Autowired
    private CompteLocataireDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }


    @Test
    void shouldFindById(){
        Long id = 1L;
        CompteLocataire entity = new CompteLocataire();
        entity.setId(id);
        underTest.save(entity);
        CompteLocataire loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        CompteLocataire entity = new CompteLocataire();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        CompteLocataire loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<CompteLocataire> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<CompteLocataire> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        CompteLocataire given = constructSample(1);
        CompteLocataire saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
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
