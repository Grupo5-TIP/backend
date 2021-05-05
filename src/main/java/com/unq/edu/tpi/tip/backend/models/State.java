package com.unq.edu.tpi.tip.backend.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    private String state;

    @OneToMany
    @Getter
    protected List<OrderTable> orderTable;

    public State(String state) {
        this.state = state;
    }

}
