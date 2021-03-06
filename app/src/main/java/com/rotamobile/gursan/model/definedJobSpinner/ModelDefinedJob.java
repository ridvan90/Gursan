package com.rotamobile.gursan.model.definedJobSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDefinedJob {

    @SerializedName("ID")
    @Expose
    private Integer ID;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Selected")
    @Expose
    private boolean Selected;

    public boolean getSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public Integer getID(){

        return ID;
    }

    public void setID(Integer ID){

        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }




}
