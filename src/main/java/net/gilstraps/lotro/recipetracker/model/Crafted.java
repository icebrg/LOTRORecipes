package net.gilstraps.lotro.recipetracker.model;

import java.util.*;

/**
 * TODO
 */
public class Crafted extends AbstractIngredient implements Thing {

    private Profession profession;
    private Set<Quantified<Thing>> components;
    private Set<Quantified<Thing>> roComponents;

    public Crafted(final String name, final Profession p, final Collection<Quantified<Thing>> inputs) {
        super(name);
        p.getClass(); // assure not null
        profession = p;
        inputs.getClass(); // assure not null
        if (inputs.size() < 1) throw new IllegalArgumentException("Crafted items require at lesat one component item.");
        components = new HashSet<Quantified<Thing>>(inputs);
    }

    public synchronized Set<Quantified<Thing>> getComponents() {
        if (roComponents == null) {
            roComponents = Collections.unmodifiableSet(components);
        }
        return roComponents;
    }

    public Map<BaseIngredient, Long> getBaseIngredients() {
        Map<BaseIngredient, Long> ingredients = new HashMap<BaseIngredient, Long>();
        addBaseIngredients(ingredients, 1);
        return ingredients;
    }

    private void addBaseIngredients(final Map<BaseIngredient, Long> ingredients, final long multiplier) {
        for (Quantified<Thing> component : components) {
            if (component.getT() instanceof Crafted) {
                ((Crafted) component.getT()).addBaseIngredients(ingredients, component.getQuantity() * multiplier);
            }
            else if (component.getT() instanceof BaseIngredient) {
                BaseIngredient bi = (BaseIngredient) component.getT();
                add(ingredients, bi, component.getQuantity() * multiplier);
            }
        }
    }

    // Package level to allow unit testing
    static void add(final Map<BaseIngredient, Long> ingredients, final BaseIngredient i, long q) {
        Long existing = ingredients.get(i);
        long e = 0l;
        if (existing != null) e = existing;
        ingredients.put(i, e + q);
    }

    public Profession getCraftingProfession() {
        return profession;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.getName() + "{\n"
                + " profession=" + profession + "\n"
                + " components= {\n");
        for (Quantified<Thing> component : components) {
            sb.append("    " + component.getT().getName() + ": " + component.getQuantity() + "\n");
        }
        sb.append("  }\n");
        sb.append("  baseIngredients= {\n");
        Map<BaseIngredient, Long> baseIngredients = getBaseIngredients();
        List<BaseIngredient> baseIngredientsList = new ArrayList<>();
        for (BaseIngredient baseIngredient : baseIngredients.keySet()) {
            baseIngredientsList.add(baseIngredient);
        }
        Collections.sort(baseIngredientsList, BaseIngredient.BY_NAME);
        for (BaseIngredient baseIngredient : baseIngredientsList) {
            sb.append("    " + baseIngredient.getName() + ": " + baseIngredients.get(baseIngredient) + "\n");
        }
        sb.append("  }\n}\n");
        return sb.toString();
    }

    public String describe() {
        StringBuilder sb = new StringBuilder(super.getName() + " (" + profession + "): ");
        boolean first = true;
        for (Quantified<Thing> component : components) {
            if (!first) {
                sb.append(", ");
            }
            else {
                first = false;
            }
            sb.append("'" + component.getT().getName() + "' x " + component.getQuantity());
        }

        sb.append("\n");
        Map<BaseIngredient, Long> baseIngredients = getBaseIngredients();
        List<BaseIngredient> baseIngredientsList = new ArrayList<>();
        List<BaseIngredient> vendorIngredientsList = new ArrayList<>();
        for (BaseIngredient baseIngredient : baseIngredients.keySet()) {
            if (baseIngredient instanceof VendorItem) {
                vendorIngredientsList.add(baseIngredient);
            }
            else {
                baseIngredientsList.add(baseIngredient);
            }
        }
        Collections.sort(baseIngredientsList, BaseIngredient.BY_NAME);
        Collections.sort(vendorIngredientsList, BaseIngredient.BY_NAME);
        for (BaseIngredient baseIngredient : baseIngredientsList) {
            sb.append("  " + baseIngredients.get(baseIngredient) + " x '" + baseIngredient.getName() + "'\n");
        }
        for (BaseIngredient baseIngredient : vendorIngredientsList) {
            sb.append("  Buy " + baseIngredients.get(baseIngredient) + " x '" + baseIngredient.getName() + "'\n");
        }
        return sb.toString();
    }
}
