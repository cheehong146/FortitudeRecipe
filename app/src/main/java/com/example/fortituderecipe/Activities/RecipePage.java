package com.example.fortituderecipe.Activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fortituderecipe.Objects.Recipe;
import com.example.fortituderecipe.R;

public class RecipePage extends AppCompatActivity {

    Recipe recipe;

    TextView tvTitle, tvTimetaken, tvDescription, tvIngredients, tvInstructions;
    ImageView ivRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        setupBackButton();
        initView();

        //get passed recipe
        Bundle bundle = getIntent().getExtras();
        recipe = bundle.getParcelable("recipe");

        if (recipe != null) {
            setupPage(recipe);
        }

    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_recipe_page_title);
        tvTimetaken = findViewById(R.id.tv_recipe_page_timetaken);
        tvDescription = findViewById(R.id.tv_recipe_page_description);
        tvIngredients = findViewById(R.id.tv_recipe_page_ingredient);
        tvInstructions = findViewById(R.id.tv_recipe_page_instructions);
        ivRecipe = findViewById(R.id.iv_recipe_page);
    }

    private void setupBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupPage(Recipe recipe) {
        tvTitle.setText(recipe.getTitle());
        tvTimetaken.setText(recipe.getTimeTaken());
        tvDescription.setText(recipe.getDescription());
        tvIngredients.setText(formatString(recipe.getIngredients()));
        tvInstructions.setText(formatString(recipe.getInstructions()));

        //load url image
        Glide.with(this)
                .asBitmap()
                .load(recipe.getImageUrl())
                .into(ivRecipe);
    }

    private String formatString(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c :
                str.toCharArray()) {
            if (c == '\t') {
                //remove tabs in string
            } else if (c == '\n') {
                sb.append('\n').append('\n');
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
