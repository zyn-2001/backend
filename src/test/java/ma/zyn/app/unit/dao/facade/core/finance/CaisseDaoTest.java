package ma.zyn.app.unit.dao.facade.core.finance;

import ma.zyn.app.bean.core.finance.Caisse;
import ma.zyn.app.dao.facade.core.finance.CaisseDao;

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


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CaisseDaoTest {

@Autowired
    private CaisseDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByCode(){
        String code = "code-1";
        Caisse entity = new Caisse();
        entity.setCode(code);
        underTest.save(entity);
        Caisse loaded = underTest.findByCode(code);
        assertThat(loaded.getCode()).isEqualTo(code);
    }

    @Test
    void shouldDeleteByCode() {
        String code = "code-12345678";
        int result = underTest.deleteByCode(code);

        Caisse loaded = underTest.findByCode(code);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        Caisse entity = new Caisse();
        entity.setId(id);
        underTest.save(entity);
        Caisse loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Caisse entity = new Caisse();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Caisse loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Caisse> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Caisse> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Caisse given = constructSample(1);
        Caisse saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private Caisse constructSample(int i) {
		Caisse given = new Caisse();
        given.setCode("code-"+i);
        given.setLibelle("libelle-"+i);
        given.setSolde(BigDecimal.TEN);
        return given;
    }

}
