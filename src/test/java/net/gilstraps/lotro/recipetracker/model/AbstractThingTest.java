package net.gilstraps.lotro.recipetracker.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Unit test of AbstractIngredient
 */
public class AbstractThingTest {
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

    @Test
    public void testEquals() {
        C c1 = new C("c1");
        C c1_1 = new C("c1");
        C c2 = new C("c2");
        C b1 = new C("b1");

        assertEquals(c1,c1_1);
        assertFalse(c1.equals(c2));
        assertFalse(c1.equals(b1));
    }
}
