package ma.zyn.app.unit.dao.facade.core.finance;

import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.dao.facade.core.finance.CompteDao;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CompteDaoTest {

@Autowired
    private CompteDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }


    @Test
    void shouldFindById(){
        Long id = 1L;
        Compte entity = new Compte();
        entity.setId(id);
        underTest.save(entity);
        Compte loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Compte entity = new Compte();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Compte loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Compte> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Compte> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Compte given = constructSample(1);
        Compte saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private Compte constructSample(int i) {
		Compte given = new Compte();
        given.setRib("rib-"+i);
        given.setBanque(new Banque(1L));
        given.setSolde(BigDecimal.TEN);
        return given;
    }

}
