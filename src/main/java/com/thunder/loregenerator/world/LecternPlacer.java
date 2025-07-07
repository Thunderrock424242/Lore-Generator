package com.thunder.loregenerator.world;

import com.thunder.loregenerator.lore.GeneratedBook;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LecternPlacer {
    public static void place(LorePlacementContext ctx) {
        ServerLevel level = ctx.level();
        BlockPos pos = ctx.pos();
        GeneratedBook book = ctx.book();

        BlockState state = Blocks.LECTERN.defaultBlockState();
        level.setBlock(pos, state, 3);

        ItemStack stack = new ItemStack(Items.WRITTEN_BOOK);
        CompoundTag tag = new CompoundTag();
        tag.putString("title", book.title());
        tag.putString("author", book.author());

        ListTag pages = new ListTag();
        for (String page : book.pages()) {
            pages.add(StringTag.valueOf("{\"text\":\"" + page + "\"}"));
        }
        tag.put("pages", pages);
        stack.setTag(tag);

        if (level.getBlockEntity(pos) instanceof LecternBlockEntity lectern) {
            lectern.setBook(stack);
        }
    }
}