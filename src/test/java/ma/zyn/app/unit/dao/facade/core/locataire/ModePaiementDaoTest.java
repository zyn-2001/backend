package ma.zyn.app.unit.dao.facade.core.locataire;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ModePaiementDaoTest {

@Autowired
    private ModePaiementDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByCode(){
        String code = "code-1";
        ModePaiement entity = new ModePaiement();
        entity.setCode(code);
        underTest.save(entity);
        ModePaiement loaded = underTest.findByCode(code);
        assertThat(loaded.getCode()).isEqualTo(code);
    }

    @Test
    void shouldDeleteByCode() {
        String code = "code-12345678";
        int result = underTest.deleteByCode(code);

        ModePaiement loaded = underTest.findByCode(code);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        ModePaiement entity = new ModePaiement();
        entity.setId(id);
        underTest.save(entity);
        ModePaiement loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        ModePaiement entity = new ModePaiement();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        ModePaiement loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<ModePaiement> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<ModePaiement> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        ModePaiement given = constructSample(1);
        ModePaiement saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private ModePaiement constructSample(int i) {
		ModePaiement given = new ModePaiement();
        given.setIndexation(i);
        given.setCode("code-"+i);
        given.setLabel("label-"+i);
        given.setStyle("style-"+i);
        given.setColor("color-"+i);
        return given;
    }

}
