package com.unq.edu.tpi.tip.backend.models;

import javax.persistence.*;
import java.util.List;

@Entity
public abstract class State {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    protected List<OrderTable> orderTable;

    public State() {}

}
