package com.thunder.loregenerator.world;

import com.thunder.loregenerator.lore.BiomeTagHelper;
import com.thunder.loregenerator.lore.LoreManager;
import com.thunder.loregenerator.lore.LoreTagDetector;
import com.thunder.loregenerator.registry.LoreFeatureRegistry;
import com.thunder.loregenerator.registry.LoreFeatureType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import java.util.HashSet;
import java.util.Set;

public class LorePlacementHandler {

    public static void tryPlaceLore(ServerLevel level, BlockPos pos) {
        var biomeHolder = level.getBiome(pos);
        ResourceLocation biomeId = level.registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.BIOME).getKey(biomeHolder.value());

        Set<String> tags = new HashSet<>();
        tags.addAll(LoreTagDetector.detectTags(com.LoreGenerator.config.ServerLoreConfig.WORLD_DESCRIPTION.get()));
        tags.addAll(BiomeTagHelper.getTagsForBiome(biomeId));

        var book = LoreManager.getRandomBook();
        if (book == null) return;

        LoreFeatureType feature = LoreFeatureRegistry.getWeightedFeature(tags);
        if (feature != null) {
            feature.placer().accept(new LorePlacementContext(level, pos, book, tags));
        }
    }
}