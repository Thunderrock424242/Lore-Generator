package com.thunder.loregenerator.lore;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LoreBookLoader {
    public static List<GeneratedBook> loadBooksFromJson() {
        List<GeneratedBook> books = new ArrayList<>();

        try (var reader = new InputStreamReader(
                LoreBookLoader.class.getResourceAsStream("/loregenerator/generated_books.json"))) {

            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();

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
        } catch (Exception e) {
            System.err.println("Failed to load AI-generated books: " + e.getMessage());
        }

        return books;
    }
}