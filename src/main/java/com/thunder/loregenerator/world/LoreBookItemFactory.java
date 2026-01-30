package com.thunder.loregenerator.world;

import com.thunder.loregenerator.lore.GeneratedBook;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WrittenBookContent;

import java.util.ArrayList;
import java.util.List;

public class LoreBookItemFactory {
    public static ItemStack createWrittenBook(GeneratedBook book) {
        ItemStack stack = new ItemStack(Items.WRITTEN_BOOK);
        List<Filterable<Component>> pages = new ArrayList<>();
        for (String text : book.pages()) {
            pages.add(Filterable.passThrough(Component.literal(text)));
        }

        WrittenBookContent content = new WrittenBookContent(
                Filterable.passThrough(book.title()),
                book.author(),
                0,
                pages,
                true
        );

        stack.set(DataComponents.WRITTEN_BOOK_CONTENT, content);
        return stack;
    }
}
