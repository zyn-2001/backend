package ma.zyn.app.service.facade.admin.finance;

import ma.zyn.app.bean.core.finance.Caisse;
import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.dao.criteria.core.finance.CompteCriteria;

import java.util.List;


public interface CompteAdminService {



    List<Compte> findByBanqueId(Long id);
    int deleteByBanqueId(Long id);
    long countByBanqueCode(String code);




	Compte create(Compte t);

    Compte update(Compte t);

    List<Compte> update(List<Compte> ts,boolean createIfNotExist);

    Compte findById(Long id);

    Compte findOrSave(Compte t);

    Compte findByReferenceEntity(Compte t);

    Compte findWithAssociatedLists(Long id);

    List<Compte> findAllOptimized();

    List<Compte> findAll();

    List<Compte> findByCriteria(CompteCriteria criteria);

    List<Compte> findPaginatedByCriteria(CompteCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(CompteCriteria criteria);

    List<Compte> delete(List<Compte> ts);

    boolean deleteById(Long id);

    List<List<Compte>> getToBeSavedAndToBeDeleted(List<Compte> oldList, List<Compte> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

    Compte findByCode(String code);

    Compte fintByCaisse(Caisse caisse);

    Compte findCharge();
}
