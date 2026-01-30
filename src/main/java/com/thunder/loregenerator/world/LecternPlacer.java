package com.thunder.loregenerator.world;

import com.thunder.loregenerator.lore.GeneratedBook;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LecternPlacer {
    public static void place(LorePlacementContext ctx) {
        ServerLevel level = ctx.level();
        BlockPos pos = ctx.pos();
        GeneratedBook book = ctx.book();

        if (!level.isEmptyBlock(pos)) return;

        // Place the lectern block
        BlockState state = Blocks.LECTERN.defaultBlockState();
        level.setBlock(pos, state, 3);

        ItemStack stack = LoreBookItemFactory.createWrittenBook(book);

        // Insert book into lectern
        if (level.getBlockEntity(pos) instanceof LecternBlockEntity lectern) {
            lectern.setBook(stack);
        }
    }
}
