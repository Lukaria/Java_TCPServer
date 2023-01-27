package entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private int companyId;
    @JsonBackReference(value = "User-companies")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;
    @JsonManagedReference(value = "Company-apives")
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apives> apives;
    @JsonManagedReference(value = "Company-capitals")
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Capital> capitals;
    @JsonManagedReference(value = "Company-liquidities")
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Liquidity> liquidities;
    @JsonManagedReference(value = "Company-profits")
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Profit> profits;

    public Company(User user, String name) {
        this.user = user;
        this.name = name;
        this.apives = new ArrayList<>();
        this.capitals = new ArrayList<>();
        this.liquidities = new ArrayList<>();
        this.profits = new ArrayList<>();
    }

    public void nullAll() {
        this.apives = null;
        this.capitals = null;
        this.liquidities = null;
        this.profits = null;
    }

    public Company(int companyId, String name, List<Apives> apives, List<Capital> capitals, List<Liquidity> liquidities, List<Profit> profits) {
        this.capitals = capitals;
        this.companyId = companyId;
        this.apives = apives;
        this.liquidities = liquidities;
        this.profits = profits;
        this.name = name;
    }

}
