package com.minecolonies.api.tileentities;

import com.minecolonies.api.blocks.AbstractBlockSilo;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.items.ItemStackHandler;

public abstract class AbstractTileEntitySilo extends TileEntity {
    public static final int CAPACITY = 64;

    public AbstractTileEntitySilo(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

}
