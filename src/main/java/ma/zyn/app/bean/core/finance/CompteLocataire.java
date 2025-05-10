package ma.zyn.app.bean.core.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.zynerator.bean.BaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "compte_locataire")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="compte_locataire_seq", sequenceName="compte_locataire_seq", allocationSize=1, initialValue = 1)
public class CompteLocataire extends BaseEntity {


    private BigDecimal soldeInitial = BigDecimal.ZERO;
    private List<BigDecimal> credits = new ArrayList<>();
    private List<BigDecimal> debits = new ArrayList<>();

    private BigDecimal solde = BigDecimal.ZERO;
    private BigDecimal debit = BigDecimal.ZERO;
    private BigDecimal credit = BigDecimal.ZERO;

    private Locataire locataire;
    private Location location;
    private List<Transaction> transactions;

    public CompteLocataire() {
        super();
    }

    public CompteLocataire(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compte_locataire_seq")
    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSolde() {
        return this.solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
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



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locataire")
    public Locataire getLocataire() {
        return this.locataire;
    }

    public void setLocataire(Locataire locataire) {
        this.locataire = locataire;
    }

    @OneToMany(mappedBy = "compteLocataire")
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location")
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompteLocataire that = (CompteLocataire) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public BigDecimal getSoldeInitial() {
        return soldeInitial;
    }

    public void setSoldeInitial(BigDecimal soldeInitial) {
        this.soldeInitial = soldeInitial;
    }
}
