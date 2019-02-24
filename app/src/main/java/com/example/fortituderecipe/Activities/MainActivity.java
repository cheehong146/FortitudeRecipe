package com.example.fortituderecipe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.fortituderecipe.Adapter.RecipeAdapter;
import com.example.fortituderecipe.Objects.Recipe;
import com.example.fortituderecipe.Objects.RecipeType;
import com.example.fortituderecipe.ParseXML;
import com.example.fortituderecipe.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String ns = null;
    ArrayList<Recipe> masterRecipes = null;
    ArrayList<Recipe> filteredRecipes = null;
    ArrayList<RecipeType> recipeTypes = null;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    private Spinner spinnerRecipeType;
    private ImageButton ibAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        //parse XML recipe data
        masterRecipes = new ParseXML(this).parseRecipeXML();

        initPopulateList();
        setupRecipeRv();

    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        spinnerRecipeType = findViewById(R.id.spinner_recipe_types);
        ibAdd = findViewById(R.id.ib_add);

        setupSpinner();

        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRecipe.class);
                startActivity(intent);
            }
        });
    }

    private void setupRecipeRv() {
        recyclerView = findViewById(R.id.recipes_recycler_view);
        //recyclerView layout
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //recyclerView adapter/data
        mAdapter = new RecipeAdapter(filteredRecipes, this);
        recyclerView.setAdapter(mAdapter);

        //setup divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void setupSpinner() {

        try {
            recipeTypes = new ParseXML(this).parseRecipeTypeXML();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        populateSpinner();

        spinnerRecipeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: " + recipeTypes.get(position).getName());
                //get filtered list and refresh adapter
                if (position == 0) {
                    //display all recipes
                    filterList("0");
                } else {
                    //subtract one due to the "All" in the first position which offset the recipeTypes list
                    filterList(recipeTypes.get(position - 1).getmId());
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initPopulateList() {
        filteredRecipes = new ArrayList<>();
        for (Recipe recipe :
                masterRecipes) {
            filteredRecipes.add(recipe);
        }
    }

    private void populateSpinner() {
        if (recipeTypes != null) {
            ArrayList<String> recipeTypesName = new ArrayList<>();

            //add another value, "All" to display all recipes
            recipeTypesName.add("All");

            for (RecipeType recipeType :
                    recipeTypes) {
                recipeTypesName.add(recipeType.getName());
            }
            ArrayAdapter<String> recipeTypesAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, recipeTypesName);
            spinnerRecipeType.setAdapter(recipeTypesAdapter);
        }
    }

    private void filterList(String recipeTypeId) {
        filteredRecipes.clear();
        //if spinner selected all, duplicate master list, else filter according to type id of the recipe
        if (recipeTypeId.equals("0")) {
            filteredRecipes.addAll(masterRecipes);
        } else {
            for (Recipe recipe :
                    masterRecipes) {
                if (recipe.getType().equals(recipeTypeId)) {
                    filteredRecipes.add(recipe);
                }

            }
        }
    }
}
