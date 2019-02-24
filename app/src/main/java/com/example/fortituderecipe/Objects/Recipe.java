package com.example.fortituderecipe.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class Recipe implements Parcelable {

    private static int mId;
    private String title;
    private String type;
    private String description;
    private String timeTaken;
    private String imageUrl;
    private String ingredients;
    private String instructions;

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        title = in.readString();
        type = in.readString();
        description = in.readString();
        timeTaken = in.readString();
        imageUrl = in.readString();
        ingredients = in.readString();
        instructions = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(description);
        dest.writeString(timeTaken);
        dest.writeString(imageUrl);
        dest.writeString(ingredients);
        dest.writeString(instructions);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public static int getmId() {
        return mId;
    }

    public static void setmId(int mId) {
        Recipe.mId = mId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}
