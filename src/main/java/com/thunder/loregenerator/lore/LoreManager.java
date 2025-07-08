package com.thunder.loregenerator.lore;


import com.thunder.loregenerator.config.LoreConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LoreManager {

    public static GeneratedBook getBookForTags(Set<String> tags) {
        // ✅ 1. Check for pre-generated lore
        GeneratedBook pregen = PreGeneratedLoreLoader.getBookForTags(tags);
        if (pregen != null) return pregen;

        // ✅ 2. Use OpenAI if enabled and configured
        String key = LoreConfig.OPENAI_API_KEY.get();
        if (!key.isBlank() && LoreConfig.LORE_GENERATION_MODE.get().equalsIgnoreCase("live")) {
            GeneratedBook aiBook = OpenAILoreGenerator.generateBook(tags, LoreConfig.WORLD_DESCRIPTION.get(), key);
            if (aiBook != null) return aiBook;
        }

        // ✅ 3. Fallback to template/local generator
        return LoreGenerator.generateBook(tags);
    }
}