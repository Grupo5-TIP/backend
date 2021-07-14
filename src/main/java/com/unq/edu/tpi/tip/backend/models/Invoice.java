package com.unq.edu.tpi.tip.backend.models;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Getter
    @Setter
    private Long prefix;

    @Getter
    @Column(name = "number", columnDefinition = "serial", updatable = false)
    private Long number;

    @Getter
    @Setter
    private String paymentType;

    @Getter
    @Setter
    private LocalDateTime date;

    @Getter
    @Setter
    private Double Ammount;

    @OneToMany
    @Getter
    @Setter
    private List<CustomerOrder> customerOrder;

    public Invoice(){
        Properties prop = new Properties();
        InputStream input = Test.class.getResourceAsStream("/application.properties");
        try {
            prop.load(input);
            this.prefix = Long.parseLong(prop.getProperty("easyfood.prefix", "1"));
        } catch (IOException e) {
            //Prefix 1 by default
            this.prefix = 1L;
        }
    }
}
