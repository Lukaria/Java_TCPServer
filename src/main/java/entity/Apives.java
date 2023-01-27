package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;


import javax.persistence.*;

@Data
@Entity
@Table(name = "apives")
public class Apives {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id")
    private int operationId;

    @JsonBackReference(value = "Company-apives")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "isActive")
    private boolean isActive;

    @Column(name = "year")
    private int year;

    @Column(name = "AP1")
    private float AP1;
    @Column(name = "AP2")
    private float AP2;
    @Column(name = "AP3")
    private float AP3;
    @Column(name = "AP4")
    private float AP4;
}
