package net.gilstraps.lotro.recipetracker.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test of AbstractIngredient
 */
public class AbstractIngredientTest {
    class C extends AbstractIngredient {
        protected C(final String name) {
            super(name);
        }
    }

    @Test(expected = NullPointerException.class)
    public void testNullName() {

        new C(null);
    }

    @Test
    public void testNameIsStored() {
        final String NAME = "name";
        C c = new C(NAME);
        assertEquals(NAME,c.getName());
    }
}
