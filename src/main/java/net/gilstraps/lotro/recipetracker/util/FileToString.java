package net.gilstraps.lotro.recipetracker.util;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Definitely not suitable for very large files (or high performance), as we might strain or blow out memory. For smaller/reasonable-sized/not-too-big files,
 * greatly simplifies providing a string to things which want them.
 */
public class FileToString {

    public static String readFully(File f) throws IOException {
        String content = readFully(f, StandardCharsets.UTF_8);
        return content;
    }

    public static String readFully(File f, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(f.toPath());
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }
}
