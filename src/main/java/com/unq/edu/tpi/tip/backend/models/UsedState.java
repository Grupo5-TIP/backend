package com.unq.edu.tpi.tip.backend.models;

import javax.persistence.Entity;

@Entity
public class UsedState extends State {
    public UsedState(){
        super("inUse");
    }
}
