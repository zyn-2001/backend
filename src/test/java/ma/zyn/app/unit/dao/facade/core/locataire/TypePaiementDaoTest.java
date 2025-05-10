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
public class TypePaiementDaoTest {

@Autowired
    private TypePaiementDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByCode(){
        String code = "code-1";
        TypePaiement entity = new TypePaiement();
        entity.setCode(code);
        underTest.save(entity);
        TypePaiement loaded = underTest.findByCode(code);
        assertThat(loaded.getCode()).isEqualTo(code);
    }

    @Test
    void shouldDeleteByCode() {
        String code = "code-12345678";
        int result = underTest.deleteByCode(code);

        TypePaiement loaded = underTest.findByCode(code);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        TypePaiement entity = new TypePaiement();
        entity.setId(id);
        underTest.save(entity);
        TypePaiement loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        TypePaiement entity = new TypePaiement();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        TypePaiement loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<TypePaiement> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<TypePaiement> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        TypePaiement given = constructSample(1);
        TypePaiement saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private TypePaiement constructSample(int i) {
		TypePaiement given = new TypePaiement();
        given.setIndexation(i);
        given.setCode("code-"+i);
        given.setLabel("label-"+i);
        given.setStyle("style-"+i);
        given.setColor("color-"+i);
        return given;
    }

}
