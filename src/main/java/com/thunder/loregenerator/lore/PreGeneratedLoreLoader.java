package com.thunder.loregenerator.lore;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;

public class PreGeneratedLoreLoader {
    private static final Map<String, List<GeneratedBook>> PREGEN_BY_TAG = new HashMap<>();

    public static void load() {
        PREGEN_BY_TAG.clear();

        try (Reader reader = new FileReader("config/tagged_books.json")) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

            for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
                String tag = entry.getKey();
                JsonArray array = entry.getValue().getAsJsonArray();

                List<GeneratedBook> books = new ArrayList<>();
                for (JsonElement elem : array) {
                    JsonObject obj = elem.getAsJsonObject();
                    String title = obj.get("title").getAsString();
                    String author = obj.get("author").getAsString();

                    List<String> pages = new ArrayList<>();
                    for (JsonElement page : obj.get("pages").getAsJsonArray()) {
                        pages.add(page.getAsString());
                    }

                    books.add(new GeneratedBook(title, author, pages));
                }

                PREGEN_BY_TAG.put(tag, books);
            }

            System.out.println("[ServerLoreGenerator] Loaded pre-generated lore from tagged_books.json");
        } catch (Exception e) {
            System.err.println("[ServerLoreGenerator] No valid tagged_books.json found. Skipping pre-generated lore.");
        }
    }

    public static GeneratedBook getBookForTags(Set<String> tags) {
        List<GeneratedBook> allCandidates = new ArrayList<>();

        for (String tag : tags) {
            List<GeneratedBook> books = PREGEN_BY_TAG.get(tag);
            if (books != null) {
                allCandidates.addAll(books);
            }
        }

        if (allCandidates.isEmpty()) return null;
        return allCandidates.get(new Random().nextInt(allCandidates.size()));
    }
}