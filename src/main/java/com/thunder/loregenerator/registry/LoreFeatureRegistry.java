package com.thunder.loregenerator.registry;

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
        register(new LoreFeatureType("sign", Set.of("sign", "danger"), com.loregenerator.world.SignPlacer::place));
        register(new LoreFeatureType("lectern", Set.of("book", "survivor"), com.serverloregenerator.world.LecternPlacer::place));
        register(new LoreFeatureType("shrine", Set.of("magic", "cult"), com.serverloregenerator.world.ShrinePlacer::place));
        register(new LoreFeatureType("item_frame", Set.of("note", "anomaly"), com.serverloregenerator.world.ItemFramePlacer::place));
    }
}