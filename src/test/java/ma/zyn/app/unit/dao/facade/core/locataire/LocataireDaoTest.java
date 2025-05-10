package ma.zyn.app.unit.dao.facade.core.locataire;

import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.dao.facade.core.locataire.LocataireDao;

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

import ma.zyn.app.bean.core.locataire.TypeLocataire ;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class LocataireDaoTest {

@Autowired
    private LocataireDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByCode(){
        String code = "code-1";
        Locataire entity = new Locataire();
        entity.setCode(code);
        underTest.save(entity);
        Locataire loaded = underTest.findByCode(code);
        assertThat(loaded.getCode()).isEqualTo(code);
    }

    @Test
    void shouldDeleteByCode() {
        String code = "code-12345678";
        int result = underTest.deleteByCode(code);

        Locataire loaded = underTest.findByCode(code);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        Locataire entity = new Locataire();
        entity.setId(id);
        underTest.save(entity);
        Locataire loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Locataire entity = new Locataire();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Locataire loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Locataire> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Locataire> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Locataire given = constructSample(1);
        Locataire saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private Locataire constructSample(int i) {
		Locataire given = new Locataire();
        given.setCode("code-"+i);
        given.setLibelle("libelle-"+i);
        given.setNom("nom-"+i);
        given.setPrenom("prenom-"+i);
        given.setTypeLocataire(new TypeLocataire(1L));
        given.setDateCreation(LocalDateTime.now());
        return given;
    }

}
