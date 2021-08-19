package com.minecolonies.coremod.blocks;

import com.minecolonies.api.blocks.AbstractBlockSilo;
import com.minecolonies.api.colony.IColony;
import com.minecolonies.api.colony.IColonyManager;
import com.minecolonies.api.colony.buildings.IBuilding;
import com.minecolonies.api.colony.buildings.ModBuildings;
import com.minecolonies.api.colony.managers.interfaces.IBuildingManager;
import com.minecolonies.api.items.ModItems;
import com.minecolonies.api.tileentities.TileEntitySilo;
import com.minecolonies.api.util.constant.Constants;
import com.minecolonies.coremod.colony.buildings.workerbuildings.BuildingCowboy;
import com.minecolonies.coremod.items.ItemSilo;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BlockSilo extends AbstractBlockSilo<BlockSilo> {
    private static final float BLOCK_HARDNESS = 3.0f;

    private static final String BLOCK_NAME = "blocksilo";

    private TileEntitySilo entity;
    public BlockSilo()
    {
        super(AbstractBlock.Properties.of(Material.METAL).strength(BLOCK_HARDNESS));
        setRegistryName(Constants.MOD_ID.toLowerCase() + ":" + BLOCK_NAME);
    }

    @Override
    public boolean hasTileEntity(BlockState state) { return true; }

    public TileEntitySilo getEntity() {
        if(entity == null)
            entity = new TileEntitySilo();
        return entity;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        if(entity == null)
            entity = new TileEntitySilo();
        return entity;
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity playerEntity, ItemStack itemStack) {
        if(itemStack.getItem() instanceof  ItemSilo) {
            if(blockState.getBlock() instanceof BlockSilo) {
                BlockSilo blockSilo = (BlockSilo) blockState.getBlock();
                ItemSilo itemSilo = (ItemSilo)itemStack.getItem();
                blockSilo.getEntity().add(itemSilo.getStorage());
            }
        }

        IColony colony = IColonyManager.getInstance().getColonyByPosFromWorld(worldIn, blockPos);
        if(colony != null) {
            IBuilding building = colony.getBuildingManager().getBuilding(blockPos);
            if(building instanceof BuildingCowboy) {
                BuildingCowboy buildingCowboy = (BuildingCowboy) building;
                buildingCowboy.addContainerPosition(blockPos);
                LOGGER.info("added silo to cowboy");
            }
        }
    }

    @Override
    public void destroy(IWorld worldIn, BlockPos blockPos, BlockState blockState) {
        IColony colony = IColonyManager.getInstance().getColonyByPosFromWorld((World) worldIn, blockPos);
        if(colony != null) {
            IBuilding building = colony.getBuildingManager().getBuilding(blockPos);
            if(building instanceof BuildingCowboy) {
                BuildingCowboy buildingCowboy = (BuildingCowboy) building;
                buildingCowboy.removeContainerPosition(blockPos);
                LOGGER.info("removed silo to cowboy");
            }
        }
        super.destroy(worldIn, blockPos, blockState);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder) {
        ItemStack stack = new ItemStack(ModItems.silo, 1);
        ((ItemSilo)stack.getItem()).setStorage(getEntity().current());
        List<ItemStack> stacks = new ArrayList<ItemStack>() {{
            add(stack);
        }};

        return stacks;
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
        if(!worldIn.isClientSide()) {
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
        }

        return ActionResultType.PASS;
    }
}
