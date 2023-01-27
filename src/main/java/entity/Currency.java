package entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "currency")

@XmlType(name = "currency")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    private int currencyId;

    @Column(name = "code")
    private int currencyCode;

    @Column(name = "symbol")
    private char symbol;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "coeff")
    private float coeff;
    @JsonManagedReference(value = "Currency-liquidities")
    @OneToMany(mappedBy = "currency", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Liquidity> liquidities;

}
