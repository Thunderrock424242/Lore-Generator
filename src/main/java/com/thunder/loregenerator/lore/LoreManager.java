package com.thunder.loregenerator.lore;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LoreManager {

    public static CompletableFuture<GeneratedBook> getBookForTagsAsync(Set<String> tags) {
        GeneratedBook pregen = PreGeneratedLoreLoader.getBookForTags(tags);
        if (pregen != null) return CompletableFuture.completedFuture(pregen);

        GeneratedBook core = CoreLoreLibrary.getBookForTags(tags);
        if (core != null) return CompletableFuture.completedFuture(core);

        return CompletableFuture.completedFuture(LoreGenerator.generateBook(tags));
    }

    public static GeneratedBook getBookForTags(Set<String> tags) {
        return getBookForTagsAsync(tags).join();
    }
}
