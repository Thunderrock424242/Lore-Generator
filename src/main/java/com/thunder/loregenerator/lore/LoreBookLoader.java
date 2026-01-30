package com.thunder.loregenerator.lore;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoreBookLoader {
    public static List<TaggedBook> loadTaggedBooksFromJson(String resourcePath) {
        List<TaggedBook> books = new ArrayList<>();

        try (var reader = new InputStreamReader(
                LoreBookLoader.class.getResourceAsStream(resourcePath))) {

            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                String title = obj.get("title").getAsString();
                String author = obj.get("author").getAsString();
                Set<String> tags = new HashSet<>();
                if (obj.has("tags")) {
                    for (JsonElement tag : obj.getAsJsonArray("tags")) {
                        tags.add(tag.getAsString());
                    }
                }
                List<String> pages = new ArrayList<>();
                for (JsonElement page : obj.get("pages").getAsJsonArray()) {
                    pages.add(page.getAsString());
                }
                books.add(new TaggedBook(new GeneratedBook(title, author, pages), tags));
            }
        } catch (Exception e) {
            System.err.println("Failed to load lore books: " + e.getMessage());
        }

        return books;
    }
}
