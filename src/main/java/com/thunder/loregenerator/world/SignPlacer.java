package com.thunder.loregenerator.world;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SignPlacer {
    public static void place(LorePlacementContext ctx) {
        ServerLevel level = ctx.level();
        BlockPos pos = ctx.pos();
        BlockState state = Blocks.OAK_SIGN.defaultBlockState();

        level.setBlock(pos, state, 3);
        if (level.getBlockEntity(pos) instanceof SignBlockEntity sign) {
            sign.setLine(0, Component.literal("Note:"));
            sign.setLine(1, Component.literal("Beware the glow."));
        }
    }
}