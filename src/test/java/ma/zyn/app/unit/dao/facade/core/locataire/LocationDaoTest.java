package ma.zyn.app.unit.dao.facade.core.locataire;

import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.dao.facade.core.locataire.LocationDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.time.LocalDateTime;

import ma.zyn.app.bean.core.locataire.Transaction ;
import ma.zyn.app.bean.core.finance.Compte ;
import ma.zyn.app.bean.core.finance.CompteLocataire ;
import ma.zyn.app.bean.core.locataire.TypeLocataire ;
import ma.zyn.app.bean.core.locataire.Locataire ;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class LocationDaoTest {

@Autowired
    private LocationDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByCode(){
        String code = "code-1";
        Location entity = new Location();
        entity.setCode(code);
        underTest.save(entity);
        Location loaded = underTest.findByCode(code);
        assertThat(loaded.getCode()).isEqualTo(code);
    }

    @Test
    void shouldDeleteByCode() {
        String code = "code-12345678";
        int result = underTest.deleteByCode(code);

        Location loaded = underTest.findByCode(code);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        Location entity = new Location();
        entity.setId(id);
        underTest.save(entity);
        Location loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Location entity = new Location();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Location loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Location> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Location> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Location given = constructSample(1);
        Location saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private Location constructSample(int i) {
		Location given = new Location();
        given.setCode("code-"+i);
        given.setLocataire(new Locataire(1L));
        given.setCompte(new Compte(1L));
        given.setCompteLocataire(new CompteLocataire(1L));
        given.setTransaction(new Transaction(1L));
        given.setTypeLocataire(new TypeLocataire(1L));
        given.setTypePaiement(new TypePaiement(1L));
        given.setDateCreation(LocalDateTime.now());
        return given;
    }

}
