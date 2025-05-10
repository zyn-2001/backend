package ma.zyn.app.ws.dto.finance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.bean.core.finance.Caisse;
import ma.zyn.app.bean.core.finance.CompteCharge;
import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.ws.dto.locataire.TransactionDto;
import ma.zyn.app.zynerator.dto.AuditBaseDto;

import java.math.BigDecimal;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompteDto  extends AuditBaseDto {
    private BigDecimal soldeInitial = BigDecimal.ZERO;

    private BigDecimal solde  ;
    private BigDecimal debit  ;
    private BigDecimal credit  ;
    private Integer numeroCompte  = 0 ;
    private String dateCreation ;
    private String code;


    private BanqueDto banque ;
    private CaisseDto caisse;
    private CompteChargeDto compteCharge;
    private CompteLocataireDto compteLocataire;
    private List<TransactionDto> transactions ;


    public CompteDto(){
        super();
    }




    public BigDecimal getSolde(){
        return this.solde;
    }
    public void setSolde(BigDecimal solde){
        this.solde = solde;
    }


    public BigDecimal getDebit(){
        return this.debit;
    }
    public void setDebit(BigDecimal debit){
        this.debit = debit;
    }


    public BigDecimal getCredit(){
        return this.credit;
    }
    public void setCredit(BigDecimal credit){
        this.credit = credit;
    }


    public Integer getNumeroCompte(){
        return this.numeroCompte;
    }
    public void setNumeroCompte(Integer numeroCompte){
        this.numeroCompte = numeroCompte;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDateCreation(){
        return this.dateCreation;
    }
    public void setDateCreation(String dateCreation){
        this.dateCreation = dateCreation;
    }


    public BanqueDto getBanque(){
        return this.banque;
    }

    public void setBanque(BanqueDto banque){
        this.banque = banque;
    }



    public List<TransactionDto> getTransactions(){
        return this.transactions;
    }

    public void setTransactions(List<TransactionDto> transactions){
        this.transactions = transactions;
    }


    public CaisseDto getCaisse() {
        return caisse;
    }

    public void setCaisse(CaisseDto caisse) {
        this.caisse = caisse;
    }

    public CompteChargeDto getCompteCharge() {
        return compteCharge;
    }

    public void setCompteCharge(CompteChargeDto compteCharge) {
        this.compteCharge = compteCharge;
    }

    public CompteLocataireDto getCompteLocataire() {
        return compteLocataire;
    }

    public void setCompteLocataire(CompteLocataireDto compteLocataire) {
        this.compteLocataire = compteLocataire;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getSoldeInitial() {
        return soldeInitial;
    }

    public void setSoldeInitial(BigDecimal soldeInitial) {
        this.soldeInitial = soldeInitial;
    }
}
