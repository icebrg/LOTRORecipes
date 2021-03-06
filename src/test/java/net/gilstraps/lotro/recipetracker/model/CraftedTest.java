package net.gilstraps.lotro.recipetracker.model;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Unit test of Crafted
 */
public class CraftedTest {

    @Test
    public void testProfession() {
        RawIngredient ri = new RawIngredient("Double Scrap of Rohirric Text");
        Crafted scrollOfPoetry = new Crafted("Scroll of Poetry", Profession.Scholar, Arrays.asList(new Quantified<Thing>(ri,1)));
        assertSame(Profession.Scholar, scrollOfPoetry.getCraftingProfession());
    }

    @Test
    public void testRawIngredient1By1() {
        RawIngredient ri = new RawIngredient("Double Scrap of Rohirric Text");
        Crafted scrollOfPoetry = new Crafted("Scroll of Poetry", Profession.Scholar, Arrays.asList(new Quantified<Thing>(ri,1)));
        Set<Quantified<Thing>> components = scrollOfPoetry.getComponents();
        assertEquals(1,components.size());
        Quantified<Thing> ingredient = components.iterator().next();
        assertEquals(1,ingredient.getQuantity());
        assertSame(ri, ingredient.getT());
    }

    @Test
    public void testRawIngredient1By2() {
        RawIngredient sort = new RawIngredient("Scrap of Rohirric Text");
        Crafted scrollOfPoetry = new Crafted("Scroll of Poetry", Profession.Scholar, Arrays.asList(new Quantified<Thing>(sort,2)));
        Set<Quantified<Thing>> components = scrollOfPoetry.getComponents();
        assertEquals(1,components.size());
        Quantified<Thing> ingredient = components.iterator().next();
        assertEquals(2,ingredient.getQuantity());
        assertSame(sort, ingredient.getT());
    }

    @Test
    public void testRawIngredient2By2() {
        RawIngredient onion = new RawIngredient("Onion");
        RawIngredient carrot = new RawIngredient("Carrot");
        Set<Quantified<Thing>> ingredients = new HashSet<>(Arrays.asList( new Quantified<Thing>(onion,2), new Quantified<Thing>(carrot,4)));
        Crafted onionsAndCarrots = new Crafted("Onions & Carrots", Profession.Cook, ingredients);
        Set<Quantified<Thing>> components = onionsAndCarrots.getComponents();
        assertEquals(2,components.size());
        boolean sawOnion = false;
        boolean sawCarrot = false;
        for ( Quantified<Thing> c : components ) {
            if ( c.getT() == onion ) {
                sawOnion = true;
                assertEquals(2,c.getQuantity());
            }
            if ( c.getT() == carrot ) {
                sawCarrot = true;
                assertEquals(4,c.getQuantity());
            }
        }
        if ( ! sawOnion ) fail( "Didn't encounter onion ingredient");
        if ( ! sawCarrot ) fail( "Didn't encounter carrt ingredient");
    }

    @Test
    public void testVendorItem1By1() {
        VendorItem blueBalloon = new VendorItem("Blue balloon",1);
        Crafted aBlueBalloon = new Crafted("A blue balloon", Profession.Prospector, Arrays.asList(new Quantified<Thing>(blueBalloon,1)));
        Set<Quantified<Thing>> components = aBlueBalloon.getComponents();
        assertEquals(1,components.size());
        Quantified<Thing> ingredient = components.iterator().next();
        assertEquals(1,ingredient.getQuantity());
        assertSame(blueBalloon, ingredient.getT());
    }

    @Test
    public void testVendorItem1By10() {
        VendorItem blueBalloon = new VendorItem("Blue balloon",1);
        Crafted aBlueBalloon = new Crafted("A bunch of blue balloons", Profession.Prospector, Arrays.asList(new Quantified<Thing>(blueBalloon,10)));
        Set<Quantified<Thing>> components = aBlueBalloon.getComponents();
        assertEquals(1,components.size());
        Quantified<Thing> ingredient = components.iterator().next();
        assertEquals(10,ingredient.getQuantity());
        assertSame(blueBalloon, ingredient.getT());
    }

    @Test
    public void testAddUnseen() {
        final BaseIngredient baseIngredient = new RawIngredient("i");
        Map<BaseIngredient,Long> start = new HashMap<>();
        Crafted.add(start, baseIngredient, 1);
        assertEquals(new Long(1), start.get(baseIngredient));
    }

    @Test
    public void testAddSeen() {
        final BaseIngredient baseIngredient = new RawIngredient("i");
        Map<BaseIngredient,Long> start = new HashMap<>();
        start.put(baseIngredient,2l);
        Crafted.add(start,baseIngredient,1);
        assertEquals(new Long(3),start.get(baseIngredient));
    }

    @Test
    public void testBaseIngredientsAllRaw() {
        RawIngredient onion = new RawIngredient("Onion");
        RawIngredient carrot = new RawIngredient("Carrot");
        Set<Quantified<Thing>> ingredients = new HashSet<>(Arrays.asList( new Quantified<Thing>(onion,2), new Quantified<Thing>(carrot,4)));
        Crafted onionsAndCarrots = new Crafted("Onions & Carrots", Profession.Cook, ingredients);
        Map<BaseIngredient,Long> aggregated = onionsAndCarrots.getBaseIngredients();
        assertEquals(2, aggregated.keySet().size());
        assertEquals(2, aggregated.get(onion).longValue());
        assertEquals(4,aggregated.get(carrot).longValue());
    }

    @Test
    public void testBaseIngredientsAllVendor() {
        VendorItem onion = new VendorItem("Onion",22);
        VendorItem carrot = new VendorItem("Carrot",33);
        Set<Quantified<Thing>> ingredients = new HashSet<>(Arrays.asList( new Quantified<Thing>(onion,2), new Quantified<Thing>(carrot,4)));
        Crafted onionsAndCarrots = new Crafted("Onions & Carrots", Profession.Cook, ingredients);
        Map<BaseIngredient,Long> aggregated = onionsAndCarrots.getBaseIngredients();
        assertEquals(2,aggregated.keySet().size());
        assertEquals(2,aggregated.get(onion).longValue());
        assertEquals(4,aggregated.get(carrot).longValue());
    }

    @Test
    public void testBaseIngredientsRawAndVendor() {
        RawIngredient onion = new RawIngredient("Onion");
        VendorItem carrot = new VendorItem("Carrot",33);
        Set<Quantified<Thing>> ingredients = new HashSet<>(Arrays.asList( new Quantified<Thing>(onion,2), new Quantified<Thing>(carrot,4)));
        Crafted onionsAndCarrots = new Crafted("Onions & Carrots", Profession.Cook, ingredients);
        Map<BaseIngredient,Long> aggregated = onionsAndCarrots.getBaseIngredients();
        assertEquals(2,aggregated.keySet().size());
        assertEquals(2,aggregated.get(onion).longValue());
        assertEquals(4,aggregated.get(carrot).longValue());
    }

    @Test
    public void testBaseIngredientsRawAndCrafted() {
        RawIngredient onion = new RawIngredient("Onion");
        VendorItem carrot = new VendorItem("Carrot",33);
        Crafted slicedCarrots = new Crafted("sliced carrots",Profession.Cook,Arrays.asList(new Quantified<Thing>(carrot,3)));
        Set<Quantified<Thing>> ingredients = new HashSet<>(Arrays.asList( new Quantified<Thing>(onion,2), new Quantified<Thing>(slicedCarrots,1)));
        Crafted onionsAndSlicedCarrots = new Crafted("Onions & Sliced Carrots", Profession.Cook, ingredients);
        Map<BaseIngredient,Long> aggregated = onionsAndSlicedCarrots.getBaseIngredients();
        assertEquals(2,aggregated.keySet().size());
        assertEquals(2,aggregated.get(onion).longValue());
        assertEquals(3,aggregated.get(carrot).longValue());

    }

    @Test
    public void testBaseIngredientsMultipleCrafted() {
        RawIngredient onion = new RawIngredient("Onion");
        VendorItem carrot = new VendorItem("Carrot",33);
        Crafted slicedCarrots = new Crafted("sliced carrots",Profession.Cook,Arrays.asList(new Quantified<Thing>(carrot,3)));
        Set<Quantified<Thing>> ingredients = new HashSet<>(Arrays.asList( new Quantified<Thing>(onion,2), new Quantified<Thing>(slicedCarrots,1)));

        RawIngredient ranchDressing = new RawIngredient("Ranch dressing");
        Crafted carrotsAndRanchDressing = new Crafted("carrots and ranch dressing",Profession.Cook,Arrays.asList(
                new Quantified<Thing>(slicedCarrots,7), new Quantified<Thing>(ranchDressing,3)
        ));
        Map<BaseIngredient,Long> aggregated = carrotsAndRanchDressing.getBaseIngredients();
        assertEquals(2,aggregated.keySet().size());
        assertEquals(21,aggregated.get(carrot).longValue());
        assertEquals(3,aggregated.get(ranchDressing).longValue());

    }

//    @Test
//    public void testBaseIngredientsRawAllCrafted() {
//        throw new UnsupportedOperationException("Kaboom!"); // TODO
//    }
}
