package net.gilstraps.lotro.recipetracker.model;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Class which loads a JSON file of crafted items. The format is a JSON object with attributes where the key is a
 * string that is the name of the crafted item, and the value is a JSON object representing the crafted object.
 *
 * A sample Crafted is:
 * <code>
 * {
 *     "profession" : "Cook"
 *     "components" : [
 *         {
 *             thing-name : quantity
 *         }
 *     ]
 * }
 * </code>
 *
 * A 'thing-name' can be the name of a raw ingredient, a vendor item, or a Crafted.
 *
 * We defer resolving items which refer to as-yet-unknown names. So, this class can handle the items in any order,
 * as long as there are no circular dependencies. The suggested order is alphabetical, to help avoid duplicates.
 */
public class CraftedItems {

    public static final Charset UTF8 = Charset.forName("UTF-8");

    private GlobalNames names;

    public CraftedItems(GlobalNames names) {
        this.names = names;
    }

    /**
     * Would like to move to a URL which points to a crowd-sourced file on the internet; hopefully soon.
     */
    public void parse(final File f ) throws IOException {
        String text = readFully(f);
        JSONObject object = new JSONObject(text);
        @SuppressWarnings("unchecked") Set<String> keys = object.keySet();
        throw new UnsupportedOperationException("kaboom!");
    }

    private String readFully(File f) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(f.getAbsolutePath()));
        return UTF8.decode(ByteBuffer.wrap(encoded)).toString();
    }
}
