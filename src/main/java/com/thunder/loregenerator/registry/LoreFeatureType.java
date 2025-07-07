package com.thunder.loregenerator.registry;

import com.thunder.loregenerator.world.LorePlacementContext;

import java.util.Set;
import java.util.function.Consumer;

public record LoreFeatureType(String id, Set<String> tags, Consumer<LorePlacementContext> placer) {}

