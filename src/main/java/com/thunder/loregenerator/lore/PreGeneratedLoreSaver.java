package com.thunder.loregenerator.lore;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.List;
import java.util.Set;

public class PreGeneratedLoreSaver {
    private static final File FILE = new File("config/tagged_books.json");

    public static synchronized void save(String title, String author, List<String> pages, Set<String> tags) {
        try {
            FILE.getParentFile().mkdirs();
            JsonObject book = new JsonObject();
            book.addProperty("title", title);
            book.addProperty("author", author);

            JsonArray jsonPages = new JsonArray();
            for (String page : pages) {
                jsonPages.add(page);
            }
            book.add("pages", jsonPages);

            JsonObject allData;
            if (FILE.exists()) {
                try (Reader reader = new FileReader(FILE)) {
                    allData = JsonParser.parseReader(reader).getAsJsonObject();
                }
            } else {
                allData = new JsonObject();
            }

            for (String tag : tags) {
                JsonArray list = allData.has(tag) ? allData.getAsJsonArray(tag) : new JsonArray();
                list.add(book);
                allData.add(tag, list);
            }

            try (Writer writer = new FileWriter(FILE)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(allData, writer);
            }
        } catch (Exception e) {
            System.err.println("Failed to save generated book: " + e.getMessage());
        }
    }
}