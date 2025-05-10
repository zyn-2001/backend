package ma.zyn.app.unit.dao.facade.core.locaux;

import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.dao.facade.core.locaux.LocalDao;

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

import ma.zyn.app.bean.core.locataire.StatutLocal ;
import ma.zyn.app.bean.core.locataire.TypeLocataire ;
import ma.zyn.app.bean.core.locataire.Locataire ;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class LocalDaoTest {

@Autowired
    private LocalDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByCode(){
        String code = "code-1";
        Local entity = new Local();
        entity.setCode(code);
        underTest.save(entity);
        Local loaded = underTest.findByCode(code);
        assertThat(loaded.getCode()).isEqualTo(code);
    }

    @Test
    void shouldDeleteByCode() {
        String code = "code-12345678";
        int result = underTest.deleteByCode(code);

        Local loaded = underTest.findByCode(code);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        Local entity = new Local();
        entity.setId(id);
        underTest.save(entity);
        Local loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Local entity = new Local();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Local loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Local> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Local> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Local given = constructSample(1);
        Local saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private Local constructSample(int i) {
		Local given = new Local();
        given.setCode("code-"+i);
        given.setLabel("label-"+i);
        given.setAdresse("adresse-"+i);
        given.setDescription("description-"+i);
        given.setTypeLocataire(new TypeLocataire(1L));
        given.setLocataire(new Locataire(1L));
        given.setStatutLocal(new StatutLocal(1L));
        given.setMontantMensuel(BigDecimal.TEN);
        return given;
    }

}
