package net.gilstraps.lotro.recipetracker.model;

/**
 * Represents a quanity of some thing
 */
public class Quantified<T> {

    private T t;
    private long quantity;

    public Quantified(final T t, final long quantity) {
        this.t = t;
        if ( quantity < 1 ) throw new IllegalArgumentException("Quantities must be >=1");
        this.quantity = quantity;
    }

    public T getT() {
        return t;
    }

    public long getQuantity() {
        return quantity;
    }
}
