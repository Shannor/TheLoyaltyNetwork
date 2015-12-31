package com.shannor.theloyaltynetwork.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Shannor on 12/31/2015.
 */
@Entity
public class Bean {

    @Id
    String name;

    public Bean() {
        name = null;
    }

    public Bean(String newName) {
        name = newName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
