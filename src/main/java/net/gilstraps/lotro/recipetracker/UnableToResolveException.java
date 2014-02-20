package net.gilstraps.lotro.recipetracker;

import org.json.JSONObject;

import java.util.Map;

/**
 * TODO
 */
public class UnableToResolveException extends Throwable {

    public final Map<String, JSONObject> unresolved;

    public UnableToResolveException(final Map<String, JSONObject> unresolved) {
        super();
        this.unresolved = unresolved;
    }
}
