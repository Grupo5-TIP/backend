package com.unq.edu.tpi.tip.backend.models;

import javax.persistence.Entity;

@Entity
public class AvailableState extends State {
    public AvailableState() {
        super("empty");
    }
}
