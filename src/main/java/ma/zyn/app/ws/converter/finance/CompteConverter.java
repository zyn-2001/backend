package ma.zyn.app.ws.converter.finance;

import ma.zyn.app.bean.core.finance.Banque;
import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.ws.converter.finance.ModePaiementConverter;
import ma.zyn.app.ws.converter.finance.TypePaiementConverter;
import ma.zyn.app.ws.converter.locataire.TransactionConverter;
import ma.zyn.app.ws.converter.locataire.TypeTransactiontConverter;
import ma.zyn.app.ws.dto.finance.CompteDto;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;
import ma.zyn.app.zynerator.util.DateUtil;
import ma.zyn.app.zynerator.util.ListUtil;
import ma.zyn.app.zynerator.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompteConverter {

    @Autowired
    private TransactionConverter transactionConverter ;
    @Autowired
    private TypePaiementConverter typePaiementConverter ;
    @Autowired
    private BanqueConverter banqueConverter ;
    @Autowired
    private ModePaiementConverter modePaiementConverter ;
    @Autowired
    private TypeTransactiontConverter typeTransactiontConverter ;
    private boolean caisse;
    private boolean compteCharge;
    private boolean compteLocataire;
    private boolean banque;
    private boolean transactions;
    @Autowired
    private CaisseConverter caisseConverter;
    @Autowired
    private CompteChargeConverter compteChargeConverter;
    @Autowired
    private CompteLocataireConverter compteLocataireConverter;

    public  CompteConverter() {
        init(true);
    }

    public Compte toItem(CompteDto dto) {
        if (dto == null) {
            return null;
        } else {
        Compte item = new Compte();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getSoldeInitial()))
                item.setSoldeInitial(dto.getSoldeInitial());
            if(StringUtil.isNotEmpty(dto.getSolde()))
                item.setSolde(dto.getSolde());
            if(StringUtil.isNotEmpty(dto.getDebit()))
                item.setDebit(dto.getDebit());
            if(StringUtil.isNotEmpty(dto.getCredit()))
                item.setCredit(dto.getCredit());
            if(StringUtil.isNotEmpty(dto.getNumeroCompte()))
                item.setNumeroCompte(dto.getNumeroCompte());
            if(StringUtil.isNotEmpty(dto.getDateCreation()))
                item.setDateCreation(DateUtil.stringEnToDate(dto.getDateCreation()));
            if(this.caisse && dto.getCaisse()!=null)
                item.setCaisse(caisseConverter.toItem(dto.getCaisse())) ;
            if(this.compteCharge && dto.getCompteCharge()!=null)
                item.setCompteCharge(compteChargeConverter.toItem(dto.getCompteCharge())) ;
            if(this.compteLocataire && dto.getCompteLocataire()!=null)
                item.setCompteLocataire(compteLocataireConverter.toItem(dto.getCompteLocataire())) ;
            if(this.banque && dto.getBanque()!=null)
                item.setBanque(banqueConverter.toItem(dto.getBanque())) ;
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(this.transactions && ListUtil.isNotEmpty(dto.getTransactions()))
                item.setTransactions(transactionConverter.toItem(dto.getTransactions()));


        return item;
        }
    }


    public CompteDto toDto(Compte item) {
        if (item == null) {
            return null;
        } else {
            CompteDto dto = new CompteDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getSolde()))
                dto.setSolde(item.getSolde());
            if(StringUtil.isNotEmpty(item.getDebit()))
                dto.setDebit(item.getDebit());
            if(StringUtil.isNotEmpty(item.getCredit()))
                dto.setCredit(item.getCredit());
            if(StringUtil.isNotEmpty(item.getSoldeInitial()))
                dto.setSoldeInitial(item.getSoldeInitial());
            if(StringUtil.isNotEmpty(item.getNumeroCompte()))
                dto.setNumeroCompte(item.getNumeroCompte());
            if(item.getDateCreation()!=null)
                dto.setDateCreation(DateUtil.dateTimeToString(item.getDateCreation()));
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(this.caisse && item.getCaisse()!=null) {
                dto.setCaisse(caisseConverter.toDto(item.getCaisse())) ;
            }
            if(this.compteCharge && item.getCompteCharge()!=null) {
                dto.setCompteCharge(compteChargeConverter.toDto(item.getCompteCharge())) ;

            }
            if(this.compteLocataire && item.getCompteLocataire()!=null) {
                dto.setCompteLocataire(compteLocataireConverter.toDto(item.getCompteLocataire())) ;

            }
            if(this.banque && item.getBanque()!=null) {
                dto.setBanque(banqueConverter.toDto(item.getBanque())) ;

            }
       /* if(this.transactions && ListUtil.isNotEmpty(item.getTransactions())){
            transactionConverter.init(true);
            dto.setTransactions(transactionConverter.toDto(item.getTransactions()));

        }*/


        return dto;
        }
    }

    public void init(boolean value) {
        initList(value);
    }

    public void initList(boolean value) {
        this.transactions = value;
    }
    public void initObject(boolean value) {
        this.compteLocataire = value;
        this.banque = value;
        this.caisse = value;
    }
	
    public List<Compte> toItem(List<CompteDto> dtos) {
        List<Compte> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (CompteDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<CompteDto> toDto(List<Compte> items) {
        List<CompteDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Compte item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(CompteDto dto, Compte t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getBanque() == null  && dto.getBanque() != null){
            t.setBanque(new Banque());
        }else if (t.getBanque() != null  && dto.getBanque() != null){
            t.setBanque(null);
            t.setBanque(new Banque());
        }
        if (dto.getTransactions() != null)
            t.setTransactions(transactionConverter.copy(dto.getTransactions()));
        if (dto.getBanque() != null)
        banqueConverter.copy(dto.getBanque(), t.getBanque());
    }

    public List<Compte> copy(List<CompteDto> dtos) {
        List<Compte> result = new ArrayList<>();
        if (dtos != null) {
            for (CompteDto dto : dtos) {
                Compte instance = new Compte();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public TransactionConverter getTransactionConverter(){
        return this.transactionConverter;
    }
    public void setTransactionConverter(TransactionConverter transactionConverter ){
        this.transactionConverter = transactionConverter;
    }
    public TypePaiementConverter getTypePaiementConverter(){
        return this.typePaiementConverter;
    }
    public void setTypePaiementConverter(TypePaiementConverter typePaiementConverter ){
        this.typePaiementConverter = typePaiementConverter;
    }
    public BanqueConverter getBanqueConverter(){
        return this.banqueConverter;
    }
    public void setBanqueConverter(BanqueConverter banqueConverter ){
        this.banqueConverter = banqueConverter;
    }
    public ModePaiementConverter getModePaiementConverter(){
        return this.modePaiementConverter;
    }
    public void setModePaiementConverter(ModePaiementConverter modePaiementConverter ){
        this.modePaiementConverter = modePaiementConverter;
    }
    public TypeTransactiontConverter getTypeTransactiontConverter(){
        return this.typeTransactiontConverter;
    }
    public void setTypeTransactiontConverter(TypeTransactiontConverter typeTransactiontConverter ){
        this.typeTransactiontConverter = typeTransactiontConverter;
    }
    public boolean  isBanque(){
        return this.banque;
    }
    public void  setBanque(boolean banque){
        this.banque = banque;
    }
    public boolean  isTransactions(){
        return this.transactions ;
    }
    public void  setTransactions(boolean transactions ){
        this.transactions  = transactions ;
    }
}
