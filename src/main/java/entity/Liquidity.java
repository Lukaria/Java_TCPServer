package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;


import javax.persistence.*;

@Data
@Entity
@Table (name = "liquidity")
public class Liquidity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id")
    private int operationId;

    @JsonBackReference(value = "Company-liquidities")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "current_ratio")
    private float currentRatio;

    @Column(name = "expected_ratio")
    private float expectedRatio;

    @Column(name = "year")
    private int year;

    @Column(name = "absolute_ratio")
    private float absoluteRatio;
    @Column(name = "quick_ratio")
    private float quickRatio;
    @Column(name = "current_ratio_coeff")
    private float currentRatioCoeff;
    @Column(name = "state_of_balance")
    private boolean stateOfBalance;

    @JsonBackReference(value = "Currency-liquidities")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private Currency currency;
}
