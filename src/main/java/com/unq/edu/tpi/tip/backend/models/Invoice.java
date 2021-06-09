package com.unq.edu.tpi.tip.backend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "invoice")
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Getter
    private Long prefix;

    @Getter
    @Column(name = "number", columnDefinition = "serial", updatable = false)
    private Long number;

    @Getter
    @Setter
    private String paymentType;

    @OneToMany
    @Getter
    @Setter
    private List<CustomerOrder> customerOrder;

}
