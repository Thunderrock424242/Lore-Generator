package com.thunder.loregenerator.lore;


import java.util.ArrayList;
import java.util.List;

public class LoreManager {
    private static final List<GeneratedBook> BOOK_CACHE = new ArrayList<>();

    public static void loadInitialLore() {
        BOOK_CACHE.clear();
        BOOK_CACHE.addAll(LoreBookLoader.loadBooksFromJson());
    }

    public static GeneratedBook getRandomBook() {
        if (BOOK_CACHE.isEmpty()) return null;
        return BOOK_CACHE.get((int) (Math.random() * BOOK_CACHE.size()));
    }
}