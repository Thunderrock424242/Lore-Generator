package com.thunder.loregenerator.world;

import com.thunder.loregenerator.lore.GeneratedBook;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;

public class ChestPlacer {
    public static void place(LorePlacementContext ctx) {
        ServerLevel level = ctx.level();
        BlockPos pos = ctx.pos();
        GeneratedBook book = ctx.book();

        if (level.isEmptyBlock(pos)) {
            level.setBlock(pos, Blocks.CHEST.defaultBlockState(), 3);
        }

        if (!(level.getBlockEntity(pos) instanceof ChestBlockEntity chest)) {
            return;
        }

        int slot = findEmptySlot(chest);
        if (slot < 0) {
            return;
        }

        ItemStack stack = LoreBookItemFactory.createWrittenBook(book);
        chest.setItem(slot, stack);
    }

    private static int findEmptySlot(ChestBlockEntity chest) {
        for (int i = 0; i < chest.getContainerSize(); i++) {
            if (chest.getItem(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }
}
