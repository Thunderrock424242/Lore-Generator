package com.thunder.loregenerator.lore;

import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class BiomeTagHelper {
    private static final Map<ResourceLocation, Set<String>> userDefinedTags = new HashMap<>();

    public static void loadCustomBiomeTags(Map<ResourceLocation, List<String>> data) {
        userDefinedTags.clear();
        for (Map.Entry<ResourceLocation, List<String>> entry : data.entrySet()) {
            userDefinedTags.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }
    }

    public static Set<String> getTagsForBiome(ResourceLocation biomeId) {
        if (userDefinedTags.containsKey(biomeId)) return userDefinedTags.get(biomeId);
        return inferTagsFromName(biomeId);
    }

    private static Set<String> inferTagsFromName(ResourceLocation biomeId) {
        Set<String> tags = new HashSet<>();
        String name = biomeId.getPath();

        if (name.contains("cave")) tags.add("underground");
        if (name.contains("glow")) tags.add("magic");
        if (name.contains("swamp") || name.contains("bog")) tags.add("wet");
        if (name.contains("desert") || name.contains("waste")) tags.add("dry");
        if (name.contains("wood") || name.contains("forest")) tags.add("forest");
        if (name.contains("corrupt") || name.contains("ominous") || name.contains("grave")) tags.add("cursed");

        return tags;
    }
}