package com.minecolonies.coremod.items;

import com.minecolonies.api.blocks.ModBlocks;
import com.minecolonies.api.util.constant.Constants;
import com.minecolonies.coremod.blocks.BlockSilo;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

public class ItemSilo extends BlockItem {
    public static final String NAME = "silo_item";

    private int storage = 0;

    public ItemSilo() {
        super(ModBlocks.blockSilo, new Properties().stacksTo(1));
        setRegistryName(Constants.MOD_ID.toLowerCase() + ":" + NAME);
    }
    public ItemSilo(int storage) {
        this();
        this.storage = storage;
    }

    public int getStorage() { return storage; }
    public void setStorage(int storage) { this.storage = storage; }
}
