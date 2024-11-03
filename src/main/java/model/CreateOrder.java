package model;

public class CreateOrder {
    private String ingredient;

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public CreateOrder(String ingredient) {
        this.ingredient = ingredient;
    }

}
