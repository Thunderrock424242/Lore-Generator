package com.thunder.loregenerator.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.awt.*;

public class ItemFramePlacer {
    public static void place(LorePlacementContext ctx) {
        ServerLevel level = ctx.level();
        BlockPos pos = ctx.pos();
        Direction dir = Direction.NORTH;

        // ✅ Use the correct constructor for 1.21.1
        ItemFrame frame = new ItemFrame(level, pos, dir);
        frame.setItem(new ItemStack(Items.PAPER));
        level.addFreshEntity(frame);
    }
}