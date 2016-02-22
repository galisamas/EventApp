package com.itworks.eventapp;

import android.support.v4.app.Fragment;
import com.itworks.eventapp.models.BaseModel;

public class BaseFragment extends Fragment{
    public BaseModel baseModel;
    void setBaseModel (BaseModel baseModel){
        this.baseModel = baseModel;
    };
}
