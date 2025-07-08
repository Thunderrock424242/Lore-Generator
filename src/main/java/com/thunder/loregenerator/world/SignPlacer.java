package com.thunder.loregenerator.world;

import com.thunder.loregenerator.lore.GeneratedBook;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.SignText;

public class SignPlacer {
    public static void place(LorePlacementContext ctx) {
        ServerLevel level = ctx.level();
        BlockPos pos = ctx.pos();
        GeneratedBook book = ctx.book();

        // Place the standing sign
        BlockState state = Blocks.OAK_SIGN.defaultBlockState();
        level.setBlock(pos, state, 3);

        if (level.getBlockEntity(pos) instanceof SignBlockEntity sign) {
            SignText text = sign.getFrontText();

            // Set the first 2 lines using the new API
            text = text.setMessage(0, Component.literal("Note:"));
            text = text.setMessage(1, Component.literal("Beware the glow."));

            // Apply the updated SignText back to the sign
            sign.setText(text, true);
        }
    }
}
