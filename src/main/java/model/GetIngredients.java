package model;

import java.util.ArrayList;
import java.util.List;

public class GetIngredients {
    private List<String> ingredients = new ArrayList<>();
    public List<String> getIngredients() {
        return ingredients;
    }
    public void add(String id) {
        ingredients.add(id);
    }


}
