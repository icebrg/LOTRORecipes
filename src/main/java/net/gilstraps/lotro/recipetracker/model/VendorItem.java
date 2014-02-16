package net.gilstraps.lotro.recipetracker.model;

/**
 * TODO
 */
public class VendorItem extends AbstractIngredient implements BaseIngredient {

    private CurrencyAmount approximateCost;

    public VendorItem(final String name, final long coppers) {
        super(name);
        if ( coppers < 1 ) throw new IllegalArgumentException("Cost must be >= 1 copper");
        this.approximateCost = new CurrencyAmount(coppers);
    }

    public CurrencyAmount getApproximateCost() {
        return approximateCost;
    }

    // No equals or hashcode - only name matters for us.
}
