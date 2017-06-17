package com.piatt.udacity.bakeaide.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class FetchRecipesEvent {
    @Getter boolean fetching;
    @Getter boolean success;
    @Getter String message;
    @Getter @Setter List<Recipe> recipes;

    public FetchRecipesEvent(boolean fetching) {
        this.fetching = fetching;
    }

    public FetchRecipesEvent(String message) {
        this.message = message;
    }

    public FetchRecipesEvent(List<Recipe> recipes) {
        success = true;
        this.recipes = recipes;
    }

    public boolean hasMessage() {
        return message != null && !message.isEmpty();
    }

    public boolean hasRecipes() {
        return recipes != null;
    }
}