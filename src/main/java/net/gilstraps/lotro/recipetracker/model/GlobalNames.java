package net.gilstraps.lotro.recipetracker.model;

import java.util.HashMap;
import java.util.Map;

/**
 * LOTRO's ingredient and crafted item names are global. We use this class to make sure we don't make a typo. We could use a simple
 * Set of strings but would lose fast problem diagnosis when an issue arises and also some type safety.
 */
public class GlobalNames {
    private Map<String,Thing> nameToThing = new HashMap<>();

    public synchronized void register( final Thing t ) throws AlreadyRegisteredException {
       Thing existing = nameToThing.get(t.getName());
        if ( existing != null ) throw new AlreadyRegisteredException(existing);
        nameToThing.put(t.getName(),t);
    }
}
