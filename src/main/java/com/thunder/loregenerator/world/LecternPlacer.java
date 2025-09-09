package com.thunder.loregenerator.world;

import com.thunder.loregenerator.lore.GeneratedBook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.network.Filterable;

import java.util.ArrayList;
import java.util.List;

public class LecternPlacer {
    public static void place(LorePlacementContext ctx) {
        ServerLevel level = ctx.level();
        BlockPos pos = ctx.pos();
        GeneratedBook book = ctx.book();

        if (!level.isEmptyBlock(pos)) return;

        // Place the lectern block
        BlockState state = Blocks.LECTERN.defaultBlockState();
        level.setBlock(pos, state, 3);

        // Create the written book using Filterable<Component> pages
        ItemStack stack = new ItemStack(Items.WRITTEN_BOOK);
        List<Filterable<Component>> pages = new ArrayList<>();
        for (String text : book.pages()) {
            pages.add(Filterable.passThrough(Component.literal(text)));
        }

        WrittenBookContent content = new WrittenBookContent(
                Filterable.passThrough(book.title()),  // title
                book.author(),                         // author
                0,                                      // generation
                pages,                                  // pages
                true                                    // resolved
        );

        stack.set(DataComponents.WRITTEN_BOOK_CONTENT, content);

        // Insert book into lectern
        if (level.getBlockEntity(pos) instanceof LecternBlockEntity lectern) {
            lectern.setBook(stack);
        }
    }
}
