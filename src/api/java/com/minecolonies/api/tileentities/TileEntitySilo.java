package com.minecolonies.api.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.world.NoteBlockEvent;

public class TileEntitySilo extends AbstractTileEntitySilo {
    public TileEntitySilo(final TileEntityType<? extends TileEntitySilo> type) {super(type);}
    public TileEntitySilo() { super(MinecoloniesTileEntities.SILO);}

    private int storage = 0;

    public int current() { return storage; }

    public boolean add(int amount) {
        if(storage + amount <= CAPACITY) {
            storage += amount;
            return true;
        }
        return  false;
    }

    public boolean take(int amount) {
        if(storage - amount >= 0) {
            storage -= amount;
            return true;
        }
        return false;
    }

    public boolean addFromPlayerHand(PlayerEntity player) {
        if(player.inventory.getSelected().getItem() == Items.MILK_BUCKET) {
            if(add(1)) {
                player.inventory.getSelected().shrink(1);
                player.inventory.add(new ItemStack(Items.BUCKET));
                return true;
            }
        }
        return false;
    }

    public boolean takeFromPlayerHand(PlayerEntity player) {
        Item currentItem = player.inventory.getSelected().getItem();
        if(currentItem == Items.BUCKET && ((BucketItem)currentItem).getFluid() == Fluids.EMPTY) {
            if(take(1)) {
                player.inventory.getSelected().shrink(1);
                player.inventory.add(new ItemStack(Items.MILK_BUCKET));
                return true;
            }
        }

        return false;
    }

    @Override
    public CompoundNBT save(CompoundNBT c) {
        super.save(c);
        c.putInt("storage", storage);
        return c;
    }

    @Override
    public void load(BlockState blockState, CompoundNBT c) {
        super.load(blockState, c);
        storage = c.getInt("storage");
    }
}
