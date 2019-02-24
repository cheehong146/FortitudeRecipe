package com.example.fortituderecipe.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fortituderecipe.Objects.Recipe;
import com.example.fortituderecipe.Objects.RecipeType;
import com.example.fortituderecipe.ParseXML;
import com.example.fortituderecipe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity {

    private static final String TAG = "AddRecipe";
    private static String ns = null;
    EditText tfTitle, tfDescription, tfIngredients, tfInstructions;
    Spinner spinnerTimetakenHour, spinnerTimetakenMinutes, spinnerRecipeType;
    Button btnAddRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        setupBackButton();
        initView();
        setupTimetakenSpinner();
        populateRecipeTypeSpinner();
    }

    private void setupBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        tfTitle = findViewById(R.id.tf_title);
        spinnerTimetakenMinutes = findViewById(R.id.spinner_timetaken_minute);
        spinnerTimetakenHour = findViewById(R.id.spinner_timetaken_hour);
        tfDescription = findViewById(R.id.tf_description);
        tfIngredients = findViewById(R.id.tf_ingredient);
        tfInstructions = findViewById(R.id.tf_instructions);
        spinnerRecipeType = findViewById(R.id.spinner_recipe_type_add);
        btnAddRecipe = findViewById(R.id.btn_add_recipe);

        setBtnAddRecipeListener();

    }

    private void setBtnAddRecipeListener() {
        btnAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dummt data as of now TODO get user input
                Recipe recipe = new Recipe();
                recipe.setType("Something");
                recipe.setImageUrl("tester");
                recipe.setInstructions("instructionsTester");
                recipe.setIngredients("ingredientsTester");
                recipe.setTimeTaken("2 hr 50 m");
                recipe.setDescription("Tester");
                recipe.setTitle("Best KFC");
                //append recipe to jsonfile
                appendRecipe(recipe);

            }
        });
    }

    private void setupTimetakenSpinner() {
        ArrayAdapter<String> minuteArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<String> hourArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);

        for (int minute = 0; minute <= 60; minute += 5) {
            minuteArrayAdapter.add((String.valueOf(minute)));
        }
        for (int hour = 0; hour <= 24; hour++) {
            hourArrayAdapter.add(String.valueOf(hour));
        }
        spinnerTimetakenMinutes.setAdapter(minuteArrayAdapter);
        spinnerTimetakenHour.setAdapter(hourArrayAdapter);
    }

    private void populateRecipeTypeSpinner() {
        ArrayAdapter<String> recipeTypeArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        try {
            ArrayList<RecipeType> recipeTypes = new ParseXML(this).parseRecipeTypeXML();
            for (RecipeType type :
                    recipeTypes) {
                recipeTypeArrayAdapter.add(type.getName());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        spinnerRecipeType.setAdapter(recipeTypeArrayAdapter);
    }

    private void writeToJSON(Recipe recipe) {
        String filename = "recipes.json";
        File file = new File(getFilesDir(), filename);
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        String response = null;
        try {

            if (!file.exists()) {
                file.createNewFile();
                fileWriter = new FileWriter(file.getAbsoluteFile());
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("{}");
                bufferedWriter.close();


                StringBuffer stringBuffer = new StringBuffer();
                fileReader = new FileReader(file.getAbsoluteFile());
                bufferedReader = new BufferedReader(fileReader);

                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }

                response = stringBuffer.toString();

                bufferedReader.close();

                //start writing here


                JSONObject messageDetails = new JSONObject(response);
                Boolean isUserExisting = messageDetails.has("Username");

                int id = 1;
                if (!isUserExisting) {
                    JSONArray newUserMessage = new JSONArray();
                    newUserMessage.put(id);
                    messageDetails.put("Username", newUserMessage);
                } else {
                    JSONArray userMessages = (JSONArray) messageDetails.get("Username");
                    userMessages.put(id);
                }

                fileWriter = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write(messageDetails.toString());
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void appendRecipe(Recipe recipe) {
        try {
            File file = new File(getFilesDir(), "recipes.json");
            if (!file.exists()) {
                file.createNewFile();
            } else {
                String response = getRecipeJson();
                JSONArray recipesJson;
                if (response.equals("")) {
                    recipesJson = new JSONArray();
                } else {
                    recipesJson = new JSONArray(response);
                }

                FileWriter fileWriter = new FileWriter(file, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                JSONObject recipeJson = new JSONObject();
                recipeJson.put("id", "1");//dummy id
                recipeJson.put("title", recipe.getTitle());
                recipeJson.put("type", recipe.getType());
                recipeJson.put("imageurl", recipe.getImageUrl());
                recipeJson.put("description", recipe.getDescription());
                recipeJson.put("timetaken", recipe.getTimeTaken());
                recipeJson.put("ingredients", recipe.getIngredients());
                recipeJson.put("instructions", recipe.getInstructions());

                recipesJson.put(recipeJson);

                bufferedWriter.write(recipesJson.toString());
                bufferedWriter.close();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private String getRecipeJson() {
        try {
            File file = new File(getFilesDir(), "recipes.json");
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line = "";
                StringBuilder sb = new StringBuilder();

                if ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                String response = sb.toString();
                Log.d(TAG, "read: " + response);
                bufferedReader.close();
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
