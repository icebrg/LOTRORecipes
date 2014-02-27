package net.gilstraps.lotro.recipetracker;

import java.util.Map;
import net.gilstraps.lotro.recipetracker.model.GlobalNames;

import java.io.File;
import java.io.IOException;
import org.json.JSONObject;

/**
 * Main class.
 *
 * You point it at a directory. In that directory we expect this sort of structure:
 * <code>
 * Ingredients.json - JSON array of raw ingredient names
 * VendorItems.json - JSON object where attributes are item_name : cost
 * crafted - directory
 *   Scholar
 *     Apprentice
 *       Scroll of Minor Battle Lore.json - JSON file
 *
 * </code>
 *
 * TODO - add support for a base URL with the same basic structure, so we can crowdsource and share the information.
 */
public class LOTRORecipes {

    private static final String INGREDIENTS = "Ingredients.json";
    private static final String VENDOR_ITEMS = "VendorItems.json";
    private static final String CRAFTED_DIR = "crafted";

    public static void main(String[] args) throws IOException {
        if ( args.length == 0 ) {
            args = new String[] { System.getProperty("user.dir") };
        }
        File root = new File(args[0]);
        File ingredientsFile = new File(root, INGREDIENTS);
        checkPerms(ingredientsFile);
        File vendorItemsFile = new File(root, VENDOR_ITEMS);
        checkPerms(vendorItemsFile);
        File crafted = new File(root, CRAFTED_DIR);
        checkPerms(crafted);
        if ( ! crafted.isDirectory() ) {
            System.err.println(crafted.getName() + " is not a directory.");
            System.exit(1);
        }

        final GlobalNames globalNames = new GlobalNames();
        final RawIngredients rawIngredients = new RawIngredients(globalNames);
        rawIngredients.parse(ingredientsFile);
        final VendorItems vendorItems = new VendorItems(globalNames);
        vendorItems.parse(vendorItemsFile);
        final CraftedItems craftedItems = new CraftedItems(globalNames);
        craftedItems.traverse(crafted);
        try {
        craftedItems.resolveAll();
        craftedItems.printSummary(System.out);
        }
        catch (UnableToResolveException e) {
            final Map<String,JSONObject> unresolved = e.unresolved;
            for ( String name : unresolved.keySet() ) {
                System.err.println("Unable to resolve '" + name + "'");
            }
        }

    }

    private static void checkPerms( final File file ) {
        if ( ! file.exists() ) {
            System.err.println("'" + file.getAbsolutePath() + "' doesn't exist!");
            System.exit(1);
        }
        if ( ! file.canRead() ) {
            System.err.println("Don't have permission to read '" + file.getAbsolutePath() + "'!");
            System.exit(1);
        }
    }
}

