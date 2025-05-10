package ma.zyn.app.unit.dao.facade.core.finance;

import ma.zyn.app.bean.core.finance.Banque;
import ma.zyn.app.dao.facade.core.finance.BanqueDao;

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
public class BanqueDaoTest {

@Autowired
    private BanqueDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByCode(){
        String code = "code-1";
        Banque entity = new Banque();
        entity.setCode(code);
        underTest.save(entity);
        Banque loaded = underTest.findByCode(code);
        assertThat(loaded.getCode()).isEqualTo(code);
    }

    @Test
    void shouldDeleteByCode() {
        String code = "code-12345678";
        int result = underTest.deleteByCode(code);

        Banque loaded = underTest.findByCode(code);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        Banque entity = new Banque();
        entity.setId(id);
        underTest.save(entity);
        Banque loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Banque entity = new Banque();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Banque loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Banque> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Banque> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Banque given = constructSample(1);
        Banque saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private Banque constructSample(int i) {
		Banque given = new Banque();
        given.setCode("code-"+i);
        given.setLibelle("libelle-"+i);
        given.setSolde(BigDecimal.TEN);
        return given;
    }

}
