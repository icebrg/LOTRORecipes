package net.gilstraps.lotro.recipetracker.model;

/**
 * TODO
 */
public abstract class AbstractIngredient implements Thing {

    private String name;

    protected AbstractIngredient(final String name) {
        name.getClass(); // assure not null
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractIngredient)) return false;

        final AbstractIngredient that = (AbstractIngredient) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "'" + name + "'";
    }
}
