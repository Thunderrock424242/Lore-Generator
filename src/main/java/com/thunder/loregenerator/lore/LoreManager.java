package com.thunder.loregenerator.lore;


import com.thunder.loregenerator.config.LoreConfig;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LoreManager {

    public static CompletableFuture<GeneratedBook> getBookForTagsAsync(Set<String> tags) {
        GeneratedBook pregen = PreGeneratedLoreLoader.getBookForTags(tags);
        if (pregen != null) return CompletableFuture.completedFuture(pregen);

        if (!LoreConfig.AI_GENERATED.get()) {
            return CompletableFuture.completedFuture(LoreGenerator.generateBook(tags));
        }

        String key = LoreConfig.OPENAI_API_KEY.get();
        if (key == null || key.isBlank()) {
            key = System.getenv("OPENAI_API_KEY");
        }
        if (key != null && !key.isBlank() && LoreConfig.LORE_GENERATION_MODE.get().equalsIgnoreCase("live")) {
            return OpenAILoreGenerator.generateBookAsync(tags, LoreConfig.WORLD_DESCRIPTION.get(), key)
                    .thenApply(book -> book != null ? book : LoreGenerator.generateBook(tags));
        }

        return CompletableFuture.completedFuture(LoreGenerator.generateBook(tags));
    }

    public static GeneratedBook getBookForTags(Set<String> tags) {
        return getBookForTagsAsync(tags).join();
    }
}