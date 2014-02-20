package net.gilstraps.lotro.recipetracker.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test of CurrencyAmount
 */
public class CurrencyAmountTest {

    @Test
    public void testTotalAmount() {
        for (long l = -10; l <= 10; l++) {
            CurrencyAmount currencyAmount = new CurrencyAmount(l);
            assertEquals(l, currencyAmount.getTotal());
        }
    }

    @Test
    public void testCoppersNoSilver() {
        for (int i = -99; i < 100; i++) {
            CurrencyAmount amt = new CurrencyAmount(i);
            assertEquals(i, amt.getCoppers());
        }
    }

    @Test
    public void testCoppersWithSilver() {
        // Negative cases
        for (long s = -10; s < 0; s++) {
            for (long c = -99; c <= 0; c++) {
                CurrencyAmount amt = new CurrencyAmount(s, c);
                assertEquals(c, amt.getCoppers());
            }
        }
        // Non-negative cases
        for (long s = 0; s < 10; s++) {
            for (long c = 0; c < 100; c++) {
                CurrencyAmount amt = new CurrencyAmount(s, c);
                assertEquals(c, amt.getCoppers());
            }
        }
    }

    @Test
    public void testSilvers() {
        // Negative cases
        for (int s = -99; s < 0; s++) {
            for (long c = -99; c <= 0; c++) {
                CurrencyAmount amt = new CurrencyAmount(s, c);
                assertEquals(s, amt.getSilvers());
            }
        }
        // Non-negative cases
        for (int s = 0; s < 100; s++) {
            for (long c = 0; c < 100; c++) {
                CurrencyAmount amt = new CurrencyAmount(s, c);
                assertEquals(s, amt.getSilvers());
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPositiveSilverNegativeCopper() {
        new CurrencyAmount(1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeSilverPositiveCopper() {
        new CurrencyAmount(-1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPositiveCopperTooLarge() {
        new CurrencyAmount(1, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCopperTooLarge() {
        new CurrencyAmount(-1, -100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPositiveCoppersWithNegativeSilver() {
        new CurrencyAmount(-1,1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCoppersWithPositiveSilver() {
        new CurrencyAmount(1,-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPositiveCoppersWithNegativeGold() {
        new CurrencyAmount(-1,0,1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCoppersWithPositiveGold() {
        new CurrencyAmount(1,0,-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPositiveSilversWithNegativeGold() {
        new CurrencyAmount(-1,1,0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeSilversWithPositiveGold() {
        new CurrencyAmount(1,-1,0);
    }

    @Test
    public void testGoldNoSilver() {
        // Negative cases
        for (int g = -99; g < 0; g++) {
            for (long c = -99; c <= 0; c++) {
                CurrencyAmount amt = new CurrencyAmount(g, 0, c);
                assertEquals(g, amt.getGolds());
            }
        }
        // Non-negative cases
        for (int g = 0; g < 00; g++) {
            for (long c = 0; c < 100; c++) {
                CurrencyAmount amt = new CurrencyAmount(g, 0, c);
                assertEquals(g, amt.getGolds());
            }
        }
    }

    @Test
    public void testGoldNoCopper() {
        // Negative cases
        for (int g = -99; g < 0; g++) {
            for (long s = -99; s <= 0; s++) {
                CurrencyAmount amt = new CurrencyAmount(g, s, 0);
                assertEquals(g, amt.getGolds());
            }
        }
        // Non-negative cases
        for (int g = 0; g < 00; g++) {
            for (long s = 0; s < 100; s++) {
                CurrencyAmount amt = new CurrencyAmount(g, s, 0);
                assertEquals(g, amt.getGolds());
            }
        }
    }

    @Test
    public void testGetters() {
        for (int g = -100; g <= 0; g++) {
            for (int s = -99; s <= 0; s++) {
                for (int c = -99; c <= 0; c++) {
                    CurrencyAmount amt = new CurrencyAmount(g,s,c);
                    assertEquals(g,amt.getGolds());
                    assertEquals(s,amt.getSilvers());
                    assertEquals(c,amt.getCoppers());
                }
            }
        }
        for (int g = 0; g <= 100; g++) {
            for (int s = 0; s < 100; s++) {
                for (int c = 0; c < 100; c++) {
                    CurrencyAmount amt = new CurrencyAmount(g,s,c);
                    assertEquals(g,amt.getGolds());
                    assertEquals(s,amt.getSilvers());
                    assertEquals(c,amt.getCoppers());
                }
            }
        }
    }

    @Test
    public void testToString() {
        CurrencyAmount amt = new CurrencyAmount(0);
        assertEquals("0c", amt.toString());
        amt = new CurrencyAmount(1);
        assertEquals("1c", amt.toString());
        amt = new CurrencyAmount(-1);
        assertEquals("-1c", amt.toString());
        amt = new CurrencyAmount(101);
        assertEquals("1s,1c", amt.toString());
        amt = new CurrencyAmount(-101);
        assertEquals("-1s,-1c", amt.toString());
        amt = new CurrencyAmount(10001);
        assertEquals("1g,0s,1c", amt.toString());
        amt = new CurrencyAmount(10101);
        assertEquals("1g,1s,1c", amt.toString());
        amt = new CurrencyAmount(-10001);
        assertEquals("-1g,0s,-1c", amt.toString());
        amt = new CurrencyAmount(-10101);
        assertEquals("-1g,-1s,-1c", amt.toString());

        amt = new CurrencyAmount(0, 0);
        assertEquals("0c", amt.toString());
        amt = new CurrencyAmount(0, 1);
        assertEquals("1c", amt.toString());
        amt = new CurrencyAmount(1, 0);
        assertEquals("1s,0c", amt.toString());
        amt = new CurrencyAmount(1, 1);
        assertEquals("1s,1c", amt.toString());

        amt = new CurrencyAmount(0, -1);
        assertEquals("-1c", amt.toString());
        amt = new CurrencyAmount(-1, 0);
        assertEquals("-1s,0c", amt.toString());
        amt = new CurrencyAmount(-1, -1);
        assertEquals("-1s,-1c", amt.toString());

        amt = new CurrencyAmount(0, 0, 0);
        assertEquals("0c", amt.toString());
        amt = new CurrencyAmount(0, 0, 1);
        assertEquals("1c", amt.toString());
        amt = new CurrencyAmount(0, 1, 0);
        assertEquals("1s,0c", amt.toString());
        amt = new CurrencyAmount(1, 0, 0);
        assertEquals("1g,0s,0c", amt.toString());
        amt = new CurrencyAmount(1, 0, 1);
        assertEquals("1g,0s,1c", amt.toString());
        amt = new CurrencyAmount(1, 1, 0);
        assertEquals("1g,1s,0c", amt.toString());
        amt = new CurrencyAmount(1, 1, 1);
        assertEquals("1g,1s,1c", amt.toString());

        amt = new CurrencyAmount(0, 0, -1);
        assertEquals("-1c", amt.toString());
        amt = new CurrencyAmount(0, -1, 0);
        assertEquals("-1s,0c", amt.toString());
        amt = new CurrencyAmount(0, -1, -1);
        assertEquals("-1s,-1c", amt.toString());
        amt = new CurrencyAmount(-1, 0, 0);
        assertEquals("-1g,0s,0c", amt.toString());
        amt = new CurrencyAmount(-1, 0, -1);
        assertEquals("-1g,0s,-1c", amt.toString());
        amt = new CurrencyAmount(-1, -1, 0);
        assertEquals("-1g,-1s,0c", amt.toString());
        amt = new CurrencyAmount(-1, -1, -1);
        assertEquals("-1g,-1s,-1c", amt.toString());
    }

    @Test
    public void testHashCode() {
        CurrencyAmount ca1 = new CurrencyAmount(1);
        CurrencyAmount ca1_1 = new CurrencyAmount(0,1);
        CurrencyAmount ca1_2 = new CurrencyAmount(0,0,1);
        CurrencyAmount ca2 = new CurrencyAmount(2);
        CurrencyAmount ca2_1 = new CurrencyAmount(0,2);
        CurrencyAmount ca2_2 = new CurrencyAmount(0,0,2);

        assertEquals(ca1.hashCode(),ca1_1.hashCode());
        assertEquals(ca1.hashCode(),ca1_2.hashCode());
        assertEquals(ca2.hashCode(),ca2_1.hashCode());
        assertEquals(ca2.hashCode(),ca2_2.hashCode());
    }
}
