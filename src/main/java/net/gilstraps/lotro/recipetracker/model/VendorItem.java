package net.gilstraps.lotro.recipetracker.model;

/**
 * TODO
 */
public class VendorItem extends AbstractIngredient implements BaseIngredient {

    private CurrencyAmount cost;

    public VendorItem(final String name, final long coppers) {
        super(name);
        if ( coppers < 1 ) throw new IllegalArgumentException("Cost must be >= 1 copper");
        this.cost = new CurrencyAmount(coppers);
    }

    public CurrencyAmount getCost() {
        return cost;
    }

    // No equals or hashcode - only name matters for us.
}
