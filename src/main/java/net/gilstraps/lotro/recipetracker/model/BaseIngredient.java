package net.gilstraps.lotro.recipetracker.model;

import java.util.Comparator;

/**
 * Either a RawIngredient or a vendor-supplied ingredient
 */
public interface BaseIngredient extends Thing {

    public static final Comparator<BaseIngredient> BY_NAME = new Comparator<BaseIngredient>() {
        @Override
        public int compare(final BaseIngredient o1, final BaseIngredient o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
}
