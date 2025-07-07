package com.thunder.loregenerator.world;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;

public class ShrinePlacer {
    public static void place(LorePlacementContext ctx) {
        ServerLevel level = ctx.level();
        BlockPos pos = ctx.pos();

        level.setBlock(pos, Blocks.OBSIDIAN.defaultBlockState(), 3);
        level.setBlock(pos.above(), Blocks.ENCHANTING_TABLE.defaultBlockState(), 3);
    }
}