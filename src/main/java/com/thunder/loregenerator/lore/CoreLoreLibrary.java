package com.thunder.loregenerator.lore;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CoreLoreLibrary {
    private static final String CORE_LORE_PATH = "/loregenerator/core_lore.json";
    private static List<TaggedBook> cachedBooks;

    public static GeneratedBook getBookForTags(Set<String> tags) {
        List<TaggedBook> books = getBooks();
        if (books.isEmpty()) {
            return null;
        }

        List<GeneratedBook> candidates = new ArrayList<>();
        for (TaggedBook taggedBook : books) {
            if (tags.isEmpty() || !java.util.Collections.disjoint(tags, taggedBook.tags())) {
                candidates.add(taggedBook.book());
            }
        }

        if (candidates.isEmpty()) {
            candidates.addAll(books.stream().map(TaggedBook::book).toList());
        }

        return candidates.get((int) (Math.random() * candidates.size()));
    }

    private static List<TaggedBook> getBooks() {
        if (cachedBooks == null) {
            cachedBooks = LoreBookLoader.loadTaggedBooksFromJson(CORE_LORE_PATH);
        }
        return cachedBooks;
    }
}
