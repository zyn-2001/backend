package ma.zyn.app.dao.facade.core.finance;

import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CompteDao extends AbstractRepository<Compte,Long>  {

    List<Compte> findByBanqueId(Long id);
    int deleteByBanqueId(Long id);
    long countByBanqueCode(String code);


    Compte findByCode(String code);
}
