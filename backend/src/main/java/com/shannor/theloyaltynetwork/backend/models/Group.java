package com.shannor.theloyaltynetwork.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Shannor on 12/25/2015.
 */
@Entity
public class Group {
    @Id
    private Long key;

    private String groupName;

    private String leader;


}
