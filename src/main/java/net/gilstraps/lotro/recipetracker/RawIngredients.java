package net.gilstraps.lotro.recipetracker;

import net.gilstraps.lotro.recipetracker.model.GlobalNames;
import net.gilstraps.lotro.recipetracker.model.RawIngredient;
import net.gilstraps.lotro.recipetracker.util.FileToString;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Class which loads a JSON file of raw ingredients. The format is a JSON array of names of raw ingredients
 */
public class RawIngredients {

    public static final Charset UTF8 = Charset.forName("UTF-8");

    private GlobalNames names;

    public RawIngredients(GlobalNames names) {
        this.names = names;
    }

    /**
     * Would like to move to a URL which points to a crowd-sourced file on the internet; hopefully soon.
     */
    public void parse(final File f ) throws IOException {
        String text = FileToString.readFully(f);
        JSONArray object = new JSONArray(text);
        for ( int i = 0 ; i < object.length(); i++ ) {
            String name = object.getString(i);
            RawIngredient ri = new RawIngredient(name);
            names.register(ri);
        }
    }

}
