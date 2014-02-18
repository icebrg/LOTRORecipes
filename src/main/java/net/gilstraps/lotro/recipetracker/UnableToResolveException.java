package net.gilstraps.lotro.recipetracker;

import java.util.Map;
import org.json.JSONObject;

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
