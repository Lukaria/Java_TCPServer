package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;


import javax.persistence.*;

@Data
@Entity
@Table (name = "profitability")
public class Profit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id")
    private int operationId;

    @JsonBackReference(value = "Company-profits")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "ROA")
    private float ROA;

    @Column(name = "ROS")
    private float ROS;

    @Column(name = "ROE")
    private float ROE;

    @Column(name = "ROI")
    private float ROI;

    @Column(name = "year")
    private int year;
}
