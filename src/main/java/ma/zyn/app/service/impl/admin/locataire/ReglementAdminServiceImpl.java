package ma.zyn.app.service.impl.admin.locataire;


import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.bean.core.locataire.Reglement;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.dao.criteria.core.locataire.ReglementCriteria;
import ma.zyn.app.dao.facade.core.locataire.ReglementDao;
import ma.zyn.app.dao.specification.core.locataire.ReglementSpecification;
import ma.zyn.app.service.facade.admin.finance.CompteAdminService;
import ma.zyn.app.service.facade.admin.finance.CompteLocataireAdminService;
import ma.zyn.app.service.facade.admin.finance.TypePaiementAdminService;
import ma.zyn.app.service.facade.admin.locataire.LocationAdminService;
import ma.zyn.app.service.facade.admin.locataire.ReglementAdminService;
import ma.zyn.app.service.facade.admin.locataire.TransactionAdminService;
import ma.zyn.app.service.facade.admin.locataire.TypeTransactiontAdminService;
import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.zynerator.util.RefelexivityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ma.zyn.app.zynerator.util.ListUtil.isEmpty;
import static ma.zyn.app.zynerator.util.ListUtil.isNotEmpty;
@Service
public class ReglementAdminServiceImpl implements ReglementAdminService {

    @Autowired
    private TransactionAdminService transactionAdminService;
    @Autowired private TypeTransactiontAdminService typeTransactiontAdminService;
    @Autowired
    private TypePaiementAdminService typePaiementAdminService;
    @Autowired
    private CompteLocataireAdminService compteLocataireAdminService;
    @Autowired
    private CompteAdminService compteAdminService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Reglement update(Reglement t) {
        Reglement loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Reglement.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Reglement findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Reglement findOrSave(Reglement t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Reglement result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Reglement> findAll() {
        return dao.findAll();
    }

    public List<Reglement> findByCriteria(ReglementCriteria criteria) {
        List<Reglement> content = null;
        if (criteria != null) {
            ReglementSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private ReglementSpecification constructSpecification(ReglementCriteria criteria) {
        ReglementSpecification mySpecification =  (ReglementSpecification) RefelexivityUtil.constructObjectUsingOneParam(ReglementSpecification.class, criteria);
        return mySpecification;
    }

    public List<Reglement> findPaginatedByCriteria(ReglementCriteria criteria, int page, int pageSize, String order, String sortField) {
        ReglementSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(ReglementCriteria criteria) {
        ReglementSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Reglement> findByLocationId(Long id){
        return dao.findByLocationId(id);
    }
    public int deleteByLocationId(Long id){
        return dao.deleteByLocationId(id);
    }
    public long countByLocationCode(String code){
        return dao.countByLocationCode(code);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean deleteById(Long id) {
        boolean condition = (id != null);
        if (condition) {
            dao.deleteById(id);
        }
        return condition;
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Reglement> delete(List<Reglement> list) {
		List<Reglement> result = new ArrayList();
        if (list != null) {
            for (Reglement t : list) {
                if(dao.findById(t.getId()).isEmpty()){
					result.add(t);
				}else{
                    dao.deleteById(t.getId());
                }
            }
        }
		return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Reglement create(Reglement t) {
        Reglement loaded = findByReferenceEntity(t);
        Reglement saved;
        if (loaded == null) {
            t.setLocation(locationService.findByReferenceEntity(t.getLocation()));
            saved = dao.save(t);
            createTransaction(saved);
        }else {
            saved = null;
        }
        return saved;
    }

    public void createTransaction(Reglement reglement) {
        // Validation du règlement
        if (reglement == null || reglement.getMontant() == null || reglement.getMontant().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Règlement invalide.");
        }
        Transaction transaction = new Transaction();
        transaction.setDate(reglement.getDate());
        transaction.setMontant(reglement.getMontant());
        transaction.setDescription(reglement.getMotif());
        // Récupération ou sauvegarde des objets
        transaction.setTypeTransaction(typeTransactiontAdminService.findReglement());
        transaction.setTypePaiement(typePaiementAdminService.findCredit());
        transaction.setModePaiement(reglement.getModePaiement());
        CompteLocataire byLocataireId = compteLocataireAdminService.findByLocataireId(reglement.getLocation().getLocataire().getId());
        transaction.setCompteLocataire(byLocataireId);
        if (reglement.getBanque() != null) {
            Compte comteBanque = compteAdminService.findByCode(reglement.getBanque().getCode());
            if (comteBanque != null) {
                transaction.setCompteDestination(comteBanque);
            }
        }
        if (reglement.getCaisse() != null) {
            Compte comteCaisse = compteAdminService.fintByCaisse(reglement.getCaisse());
            if (comteCaisse != null) {
                transaction.setCompteDestination(comteCaisse);
            }
        }

        // Création de la transaction
        transactionAdminService.create(transaction);
    }




    public Reglement findWithAssociatedLists(Long id){
        Reglement result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Reglement> update(List<Reglement> ts, boolean createIfNotExist) {
        List<Reglement> result = new ArrayList<>();
        if (ts != null) {
            for (Reglement t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Reglement loadedItem = dao.findById(t.getId()).orElse(null);
                    if (isEligibleForCreateOrUpdate(createIfNotExist, t, loadedItem)) {
                        dao.save(t);
                    } else {
                        result.add(t);
                    }
                }
            }
        }
        return result;
    }


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Reglement t, Reglement loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Reglement findByReferenceEntity(Reglement t) {
        return t == null || t.getId() == null ? null : findById(t.getId());
    }
    public void findOrSaveAssociatedObject(Reglement t){
        if( t != null) {
            t.setLocation(locationService.findOrSave(t.getLocation()));
        }
    }



    public List<Reglement> findAllOptimized() {
        return dao.findAll();
    }

    @Override
    public List<List<Reglement>> getToBeSavedAndToBeDeleted(List<Reglement> oldList, List<Reglement> newList) {
        List<List<Reglement>> result = new ArrayList<>();
        List<Reglement> resultDelete = new ArrayList<>();
        List<Reglement> resultUpdateOrSave = new ArrayList<>();
        if (isEmpty(oldList) && isNotEmpty(newList)) {
            resultUpdateOrSave.addAll(newList);
        } else if (isEmpty(newList) && isNotEmpty(oldList)) {
            resultDelete.addAll(oldList);
        } else if (isNotEmpty(newList) && isNotEmpty(oldList)) {
			extractToBeSaveOrDelete(oldList, newList, resultUpdateOrSave, resultDelete);
        }
        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }

    private void extractToBeSaveOrDelete(List<Reglement> oldList, List<Reglement> newList, List<Reglement> resultUpdateOrSave, List<Reglement> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Reglement myOld = oldList.get(i);
                Reglement t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Reglement myNew = newList.get(i);
                Reglement t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }







    @Autowired
    private LocationAdminService locationService ;

    public ReglementAdminServiceImpl(ReglementDao dao) {
        this.dao = dao;
    }

    private ReglementDao dao;
}
