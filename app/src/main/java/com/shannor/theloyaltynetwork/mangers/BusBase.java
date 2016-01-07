package com.shannor.theloyaltynetwork.mangers;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Shannor on 1/6/2016.
 */
public class BusBase {
    private static Bus instance = null;

    private BusBase(){
        //Default
    }

    public static Bus getInstance(){
        if (instance == null){
            instance = new Bus(ThreadEnforcer.MAIN);
        }
        return instance;
    }
}
