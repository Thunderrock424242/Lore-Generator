package com.thunder.loregenerator.lore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class LoreGenerator {
    public static List<GeneratedBook> generateBooks(String seed, int count) {
        Random random = new Random(seed.hashCode());
        List<GeneratedBook> books = new ArrayList<>();

        String[] titles = { "Wanderer's Notes", "Cultist Log", "Field Report", "The Rift Watch" };
        String[] authors = { "Unknown", "Dr. Alaric", "Elder Mira", "Archivist Lorne" };
        String[] themes = {
                "The caves are growing. Something is alive beneath the stone.",
                "I saw the sky fracture again. This time, it whispered a name.",
                "Why did they vanish? Everyone in the bunker... just gone.",
                "If you're reading this... run. It’s not sealed anymore."
        };

        for (int i = 0; i < count; i++) {
            List<String> pages = new ArrayList<>();
            pages.add(themes[random.nextInt(themes.length)]);
            pages.add("Entry #" + (random.nextInt(500) + 1) + " - Location: Sector " + (char)(65 + random.nextInt(26)) + random.nextInt(100));
            books.add(new GeneratedBook(
                    titles[random.nextInt(titles.length)],
                    authors[random.nextInt(authors.length)],
                    pages
            ));
        }

        return books;
    }
    public static GeneratedBook generateBook(Set<String> tags) {
        List<String> pages = new ArrayList<>();
        pages.add("The land here feels different...");
        pages.add("Whispers of: " + String.join(", ", tags));
        pages.add("Be cautious. You are not alone.");

        return new GeneratedBook("Strange Journal", "Unknown", pages);
    }
}