package com.minecolonies.coremod.blocks;

import com.minecolonies.api.blocks.AbstractBlockSilo;
import com.minecolonies.api.tileentities.TileEntitySilo;
import com.minecolonies.api.util.constant.Constants;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class BlockSilo extends AbstractBlockSilo<BlockSilo> {
    private static final float BLOCK_HARDNESS = 10.0f;

    private static final String BLOCK_NAME = "blocksilo";

    public BlockSilo()
    {
        super(AbstractBlock.Properties.of(Material.METAL));
        setRegistryName(Constants.MOD_ID.toLowerCase() + ":" + BLOCK_NAME);
    }

    @Override
    public boolean hasTileEntity(BlockState state) { return true; }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntitySilo();
    }

    @Override
    public ActionResultType use(
            final BlockState state,
            final World worldIn,
            final BlockPos pos,
            final PlayerEntity player,
            final Hand hand,
            final BlockRayTraceResult ray) {
        ItemStack selectedStack = player.inventory.getSelected();
        Item selectedItem = selectedStack.getItem();

        final TileEntity tileEntity = worldIn.getBlockEntity(pos);
        if(tileEntity instanceof TileEntitySilo) {
            TileEntitySilo entitySilo = (TileEntitySilo)tileEntity;
            if (selectedItem == Items.MILK_BUCKET) {
                if(entitySilo.addFromPlayerHand(player)) return ActionResultType.SUCCESS;
                else return ActionResultType.FAIL;
            } else if (selectedItem == Items.BUCKET) {
                if(entitySilo.takeFromPlayerHand(player)) return ActionResultType.SUCCESS;
                else return ActionResultType.FAIL;
            }
        }

        return ActionResultType.PASS;
    }
}
