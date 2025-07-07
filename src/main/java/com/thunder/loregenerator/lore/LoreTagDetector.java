package com.thunder.loregenerator.lore;

import java.util.HashSet;
import java.util.Set;

public class LoreTagDetector {
    public static Set<String> detectTags(String worldDescription) {
        String desc = worldDescription.toLowerCase();
        Set<String> tags = new HashSet<>();

        if (desc.contains("cave")) tags.add("cave");
        if (desc.contains("magic")) tags.add("magic");
        if (desc.contains("cult")) tags.add("cult");
        if (desc.contains("journal")) tags.add("book");
        if (desc.contains("warning")) tags.add("sign");
        if (desc.contains("bunker") || desc.contains("camp")) tags.add("survivor");
        if (desc.contains("glow") || desc.contains("strange")) tags.add("anomaly");

        return tags;
    }
}