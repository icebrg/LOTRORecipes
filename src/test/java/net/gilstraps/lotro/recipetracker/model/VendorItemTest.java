package net.gilstraps.lotro.recipetracker.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test of VendorItem
 */
public class VendorItemTest {

    @Test
    public void testConstructor() {
        VendorItem blueBalloon = new VendorItem("blue balloon",1);
        assertEquals("blue balloon",blueBalloon.getName());
        final CurrencyAmount amt = new CurrencyAmount(1);
        assertEquals(amt,blueBalloon.getApproximateCost());
    }

}
