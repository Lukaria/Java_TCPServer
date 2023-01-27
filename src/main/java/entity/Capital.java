package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;


import javax.persistence.*;
import javax.persistence.ManyToOne;
@Data
@Entity
@Table (name = "capital")
public class Capital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id")
    private int operationId;

    @JsonBackReference(value = "Company-capitals")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "year")
    private int year;

    @Column(name = "net_profit")
    private float netProfit; //чистая прибыль

    @Column(name = "actives")
    private float actives; //активы (любые, можно считать рентабельность для любых активов)

    @Column(name = "RWVAT")
    private float RWVAT; //revenue without VAT выручка без ндс

    @Column(name = "net_worth")
    private float netWorth;//собственный капитал

    @Column(name = "invested_funds")
    private float investedFunds;//вложенные средства

}
