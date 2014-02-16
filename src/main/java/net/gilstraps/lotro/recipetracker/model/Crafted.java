package net.gilstraps.lotro.recipetracker.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TODO
 */
public class Crafted extends AbstractIngredient implements Ingredient {

    private Profession craftedBy;
    private Set<Quantified<Ingredient>> components;
    private Set<Quantified<Ingredient>> roComponents;

    public Crafted(final String name, final Profession p, final Collection<Quantified<Ingredient>> inputs) {
        super(name);
        p.getClass(); // assure not null
        craftedBy = p;
        inputs.getClass(); // assure not null
        if ( inputs.size() < 1 ) throw new IllegalArgumentException("Crafted items require at lesat one component item.");
        components = new HashSet<Quantified<Ingredient>>(inputs);
    }

    public synchronized Set<Quantified<Ingredient>> getComponents() {
        if (roComponents == null) {
            roComponents = Collections.unmodifiableSet(components);
        }
        return roComponents;
    }

    public Map<BaseIngredient, Long> getBaseIngredients() {
        Map<BaseIngredient, Long> ingredients = new HashMap<BaseIngredient, Long>();
        addBaseIngredients(ingredients,1);
        return ingredients;
    }

    private void addBaseIngredients(final Map<BaseIngredient, Long> ingredients,final long multiplier) {
        for (Quantified<Ingredient> component : components) {
            if (component.getT() instanceof Crafted) {
                ((Crafted)component.getT()).addBaseIngredients(ingredients,component.getQuantity()*multiplier);
            }
            else if ( component.getT() instanceof BaseIngredient ) {
                BaseIngredient bi = (BaseIngredient) component.getT();
                add(ingredients,bi,component.getQuantity()*multiplier);
            }
        }
    }

    // Package level to allow unit testing
    static void add(final Map<BaseIngredient, Long> ingredients, final BaseIngredient i, long q) {
        Long existing = ingredients.get(i);
        long e = 0l;
        if ( existing != null ) e = existing;
        ingredients.put(i,e+q);
    }

    public Profession getCraftingProfession() {
        return craftedBy;
    }
}
