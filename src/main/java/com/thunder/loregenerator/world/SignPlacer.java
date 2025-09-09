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

        if (!level.isEmptyBlock(pos)) return;

        // Place the standing sign
        BlockState state = Blocks.OAK_SIGN.defaultBlockState();
        level.setBlock(pos, state, 3);

        if (level.getBlockEntity(pos) instanceof SignBlockEntity sign) {
            SignText text = sign.getFrontText();

            String content = book.pages().isEmpty() ? book.title() : book.pages().get(0);
            String[] lines = content.split("\\R");
            if (lines.length > 0) {
                text = text.setMessage(0, Component.literal(truncate(lines[0])));
            }
            if (lines.length > 1) {
                text = text.setMessage(1, Component.literal(truncate(lines[1])));
            }

            sign.setText(text, true);
        }
    }

    private static String truncate(String s) {
        return s.length() > 15 ? s.substring(0, 15) : s;
    }
}
