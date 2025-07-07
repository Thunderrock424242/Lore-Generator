package com.thunder.loregenerator.world;

import com.thunder.loregenerator.lore.GeneratedBook;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Set;

public record LorePlacementContext(ServerLevel level, BlockPos pos, GeneratedBook book, Set<String> tags) {}