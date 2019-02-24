package com.example.fortituderecipe.Objects;

public class RecipeType {

    private String mId;
    private String name;

    public RecipeType() {
    }

    public RecipeType(String mId) {
        this.mId = mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmId() {
        return mId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
