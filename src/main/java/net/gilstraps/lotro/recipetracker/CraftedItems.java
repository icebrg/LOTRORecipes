package net.gilstraps.lotro.recipetracker;

import net.gilstraps.lotro.recipetracker.model.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Class which loads a collection of JSON files of a crafted items, all in a common directory. The format is a JSON object with
 * attributes where the key is a string that is the name of the crafted item, and the value is a JSON object representing the
 * crafted object.
 * <p/>
 * The contents of a sample Crafted (for "Scroll of Minor Battle Lore") is: <code> <br/> {<br/> &nbsp;&nbsp;"profession" :
 * "Scholar"<br/> &nbsp;&nbsp;"components" : {<br/> &nbsp;&nbsp;&nbsp;&nbsp;"Aged Scrap of Text": 2<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;Worn Tablet Framework" : 1<br/> &nbsp;&nbsp;&nbsp;&nbsp;Early Third Age Relic": 1<br/> &nbsp;&nbsp;}<br/>
 * }<br/> <br/> </code>
 * <p/>
 * A component name can be the name of a raw ingredient, a vendor item, or a Crafted.
 * <p/>
 * We defer resolving items which refer to as-yet-unknown names. So, this class can handle the items in any order, as long as there
 * are no circular dependencies. The suggested order is alphabetical, to help avoid duplicates.
 */
public class CraftedItems {

    public static final Charset UTF8 = Charset.forName("UTF-8");

    private GlobalNames names;
    private Map<String, JSONObject> unresolved = new HashMap<>();
    private Map<String, Crafted> resolved = new HashMap<>();

    private class MyVisitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(final Path filePath, final BasicFileAttributes attrs) throws IOException {
            parse(filePath.toFile());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
            System.err.println("Problems visiting " + file);
            return FileVisitResult.CONTINUE;
        }
    }

    public CraftedItems(GlobalNames names) {
        this.names = names;
    }

    public void traverse(final File directory) throws IOException {
        MyVisitor visitor = new MyVisitor();
        Files.walkFileTree(directory.toPath(), visitor);
    }

    public Map<String, Crafted> resolveAll() throws UnableToResolveException {

        boolean makingProgress = true;
        boolean done = false;
        while (!done && makingProgress) {
            int unresolvedSize = unresolved.size();
            for (String name : unresolved.keySet()) {
                JSONObject raw = unresolved.get(name);
                tryToResolve(name, raw);
            }
            int endingSize = unresolved.size();
            done = endingSize == 0;
            makingProgress = endingSize < unresolvedSize;
        }
        if (!done) {
            throw new UnableToResolveException(unresolved);
        }
        return new HashMap<>(resolved);
    }

    public void printSummary(final PrintStream out) {
        List<String> names = new ArrayList<>(resolved.keySet());
        Collections.sort(names);
        for ( String name : names ) {
            Crafted crafted = resolved.get(name);
            out.println(crafted.toString());
        }
    }

    /**
     * Would like to move to a URL which points to a crowd-sourced file on the internet; hopefully soon.
     */
    private void parse(final File f) throws IOException {
        try {
        String text = readFully(f);
        JSONObject object = new JSONObject(text);
        @SuppressWarnings("unchecked") Set<String> keys = object.keySet();
        String craftedName = f.getName();
        if (craftedName.endsWith(".json") || craftedName.endsWith(".JSON")) {
            craftedName = craftedName.substring(0, craftedName.length() - ".json".length());
        }
        checkJSON(craftedName, object);
        }
        catch ( JSONException e ) {
            System.err.println("Problem processing '" + f.getAbsolutePath() + "'");
            throw e;
        }
    }

    private void checkJSON(final String name, final JSONObject object) {
        Crafted crafted = tryToResolve(name, object);
        if (crafted != null) {
            resolved.put(crafted.getName(), crafted);
        }
        else {
            unresolved.put(name, object);
        }
    }

    private Crafted tryToResolve(final String name, final JSONObject object) {
        String professionName = object.getString("profession");
        Profession profession = Profession.valueOf(professionName);
        JSONObject components = object.getJSONObject("components");
        List<Quantified<Thing>> recipeItems = new ArrayList<>();
        @SuppressWarnings("unchecked") Set<String> componentNames = (Set<String>) components.keySet();
        boolean resolvedAll = true;
        for (String componentName : componentNames) {
            Thing found = names.get(componentName);
            if (found == null) {
                resolvedAll = false;
                break;
            }
            Integer quantity = components.getInt(componentName);
            recipeItems.add(new Quantified<Thing>(found, quantity));
        }
        if (resolvedAll) {
            Crafted crafted = new Crafted(name, profession, recipeItems);
            names.register(crafted);
            resolved.put(name,crafted);
            unresolved.remove(name);
            return crafted;
        }
        return null;
    }

    private String readFully(File f) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(f.getAbsolutePath()));
        return UTF8.decode(ByteBuffer.wrap(encoded)).toString();
    }
}
