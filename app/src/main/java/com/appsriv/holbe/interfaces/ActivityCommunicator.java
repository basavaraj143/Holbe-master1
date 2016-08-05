package com.appsriv.holbe.interfaces;


import com.appsriv.holbe.models.Group;

import java.util.ArrayList;

/**
 * Created by appsriv-02 on 1/8/16.
 */
public interface ActivityCommunicator{
    public void passDataToActivity(ArrayList<Group> list);
}