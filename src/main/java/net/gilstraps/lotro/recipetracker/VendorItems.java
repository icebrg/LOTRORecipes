package net.gilstraps.lotro.recipetracker;

import net.gilstraps.lotro.recipetracker.model.CurrencyAmount;
import net.gilstraps.lotro.recipetracker.model.GlobalNames;
import net.gilstraps.lotro.recipetracker.model.VendorItem;
import net.gilstraps.lotro.recipetracker.util.FileToString;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Class which loads a JSON file of vendor items. The format is a JSON object with mappings from names to
 * approximate costs.
 */
public class VendorItems {

    public static final Charset UTF8 = Charset.forName("UTF-8");

    private GlobalNames names;

    public VendorItems(GlobalNames names) {
        this.names = names;
    }

    /**
     * Would like to move to a URL which points to a crowd-sourced file on the internet; hopefully soon.
     */
    public void parse(final File f ) throws IOException {
        String text = FileToString.readFully(f);
        JSONObject object = new JSONObject(text);
        @SuppressWarnings("unchecked") Set<String> keys = object.keySet();
        for ( String key : keys ) {
            CurrencyAmount approximateCost = new CurrencyAmount( object.getString(key) );
            VendorItem item = new VendorItem(key,approximateCost);
            names.register(item);
        }
    }

}
