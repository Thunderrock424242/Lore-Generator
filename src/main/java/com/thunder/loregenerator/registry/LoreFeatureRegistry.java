package com.thunder.loregenerator.registry;

import com.thunder.loregenerator.world.ItemFramePlacer;
import com.thunder.loregenerator.world.LecternPlacer;
import com.thunder.loregenerator.world.ShrinePlacer;
import com.thunder.loregenerator.world.SignPlacer;

import java.util.*;
import java.util.stream.Collectors;

public class LoreFeatureRegistry {
    private static final List<LoreFeatureType> FEATURES = new ArrayList<>();

    public static void register(LoreFeatureType feature) {
        FEATURES.add(feature);
    }

    public static List<LoreFeatureType> getFeaturesMatchingTags(Set<String> tags) {
        return FEATURES.stream()
                .filter(f -> !Collections.disjoint(f.tags(), tags))
                .collect(Collectors.toList());
    }

    public static LoreFeatureType getWeightedFeature(Set<String> tags) {
        List<LoreFeatureType> candidates = getFeaturesMatchingTags(tags);
        if (candidates.isEmpty()) return null;
        return candidates.get(new Random().nextInt(candidates.size()));
    }

    public static void bootstrap() {
        register(new LoreFeatureType("sign", Set.of("sign", "danger"), SignPlacer::place));
        register(new LoreFeatureType("lectern", Set.of("book", "survivor"), LecternPlacer::place));
        register(new LoreFeatureType("shrine", Set.of("magic", "cult"), ShrinePlacer::place));
        register(new LoreFeatureType("item_frame", Set.of("note", "anomaly"), ItemFramePlacer::place));

    }
}