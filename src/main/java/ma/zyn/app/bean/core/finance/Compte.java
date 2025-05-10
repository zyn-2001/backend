package ma.zyn.app.bean.core.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.zynerator.bean.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "compte")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="compte_seq",sequenceName="compte_seq",allocationSize=1, initialValue = 1)
public class Compte  extends BaseEntity     {

    private BigDecimal solde = BigDecimal.ZERO;

    private BigDecimal debit = BigDecimal.ZERO;

    private BigDecimal credit = BigDecimal.ZERO;


    public BigDecimal getSoldeInitial() {
        return soldeInitial;
    }

    public void setSoldeInitial(BigDecimal soldeInitial) {
        this.soldeInitial = soldeInitial;
    }



    public void setCredits(List<BigDecimal> credits) {
        this.credits = credits;
    }



    public void setDebits(List<BigDecimal> debits) {
        this.debits = debits;
    }

    private BigDecimal soldeInitial = BigDecimal.ZERO;
    private List<BigDecimal> credits = new ArrayList<>();
    private List<BigDecimal> debits = new ArrayList<>();

    private Integer numeroCompte = 0;

    private LocalDateTime dateCreation ;

    private CompteAdmin compteAdmin;

    private String code;
    private Banque banque ;
    private Caisse caisse;
    private CompteCharge compteCharge;
    private CompteLocataire compteLocataire;
    private List<Transaction> transactions ;

    public Compte(){
        super();
    }

    public Compte(Long id){
        this.id = id;
    }

    public void addCredit(BigDecimal montant) {
        credit = credit.add(montant);
        updateTotaux();
    }

    public void addDebit(BigDecimal montant) {
        debit = debit.add(montant);
        updateTotaux();
    }


    public void annulerDerniereEntree() {
        if (!credits.isEmpty()) {
            credits.remove(credits.size() - 1);
            updateTotaux();
        }
    }

    public void annulerDerniereSortie() {
        if (!debits.isEmpty()) {
            debits.remove(debits.size() - 1);
            updateTotaux();
        }
    }

    private void updateTotaux() {
        this.solde = credit.subtract(debit);
    }


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="compte_seq")
      @Override
    public Long getId(){
        return this.id;
    }
        @Override
    public void setId(Long id){
        this.id = id;
    }
    public BigDecimal getSolde(){
        return this.solde;
    }
    public void setSolde(BigDecimal solde){
        this.solde = solde;
    }
    public BigDecimal getDebit(){
        return debit;
    }
    public void setDebit(BigDecimal debit){
        this.debit = debit;
    }
    public BigDecimal getCredit(){
        return credit;
    }
    public void setCredit(BigDecimal credit){
        this.credit = credit;
        updateSolde();
    }
    public Integer getNumeroCompte(){
        return this.numeroCompte;
    }
    public void setNumeroCompte(Integer numeroCompte){
        this.numeroCompte = numeroCompte;
    }
    @OneToMany(mappedBy = "compteSource")
    public List<Transaction> getTransactions(){
        return this.transactions;
    }

    public void setTransactions(List<Transaction> transactions){
        this.transactions = transactions;
    }
    public LocalDateTime getDateCreation(){
        return this.dateCreation;
    }
    public void setDateCreation(LocalDateTime dateCreation){
        this.dateCreation = dateCreation;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banque")
    public Banque getBanque(){
        return this.banque;
    }
    public void setBanque(Banque banque){
        this.banque = banque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compte compte = (Compte) o;
        return id != null && id.equals(compte.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compteAdmin")
    public CompteAdmin getCompteAdmin() {
        return compteAdmin;
    }

    public void setCompteAdmin(CompteAdmin compteAdmin) {
        this.compteAdmin = compteAdmin;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caisse")
    public Caisse getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compteCharge")
    public CompteCharge getCompteCharge() {
        return compteCharge;
    }

    public void setCompteCharge(CompteCharge compteCharge) {
        this.compteCharge = compteCharge;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compteLocataire")
    public CompteLocataire getCompteLocataire() {
        return compteLocataire;
    }
    private void updateSolde() {
        this.solde = credit.subtract(debit);
    }
    public void setCompteLocataire(CompteLocataire compteLocataire) {
        this.compteLocataire = compteLocataire;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

