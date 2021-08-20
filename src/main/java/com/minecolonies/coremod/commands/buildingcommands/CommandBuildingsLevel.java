package com.minecolonies.coremod.commands.buildingcommands;

import com.ldtteam.structures.blueprints.v1.Blueprint;
import com.ldtteam.structures.client.BlueprintHandler;
import com.ldtteam.structures.client.StructureClientHandler;
import com.ldtteam.structurize.management.Structures;
import com.ldtteam.structurize.placement.StructurePlacer;
import com.ldtteam.structurize.util.LanguageHandler;
import com.ldtteam.structurize.util.StructureUtils;
import com.minecolonies.api.colony.IColony;
import com.minecolonies.api.colony.IColonyManager;
import com.minecolonies.api.colony.buildings.IBuilding;
import com.minecolonies.api.colony.buildings.ISchematicProvider;
import com.minecolonies.api.tileentities.AbstractTileEntityColonyBuilding;
import com.minecolonies.api.tileentities.MinecoloniesTileEntities;
import com.minecolonies.api.tileentities.TileEntityColonyBuilding;
import com.minecolonies.api.util.Log;
import com.minecolonies.api.util.constant.SchematicTagConstants;
import com.minecolonies.coremod.MineColonies;
import com.minecolonies.coremod.colony.buildings.workerbuildings.BuildingCowboy;
import com.minecolonies.coremod.colony.managers.BuildingManager;
import com.minecolonies.coremod.commands.commandTypes.IMCColonyOfficerCommand;
import com.minecolonies.coremod.commands.commandTypes.IMCCommand;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.world.gen.feature.structure.StructureManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.minecolonies.coremod.commands.CommandArgumentNames.*;
import static com.minecolonies.coremod.commands.colonycommands.CommandListColonies.START_PAGE_ARG;

public class CommandBuildingsLevel implements IMCColonyOfficerCommand {

    @Override
    public int onExecute(CommandContext<CommandSource> context) {
        final Logger log = Log.getLogger();
        log.info("Execute BuildingLevelCmd");
        final int colonyID = IntegerArgumentType.getInteger(context, COLONYID_ARG);
        final IColony colony = IColonyManager.getInstance().getColonyByDimension(colonyID, context.getSource().getLevel().dimension());
        if (colony == null)
        {
            context.getSource().sendSuccess(LanguageHandler.buildChatComponent("com.minecolonies.command.colonyidnotfound", colonyID), true);
            return 0;
        }

        final int buildingID = IntegerArgumentType.getInteger(context, BUIDLINGID_ARG);
        final int newLevel = IntegerArgumentType.getInteger(context, BUILDINGLVL_ARG);
        final List<IBuilding> buildings = new ArrayList<>(colony.getBuildingManager().getBuildings().values());
        final IBuilding building = buildings.get(buildingID);

        if(building == null) {
            log.info("Building not found!");
            return 0;
        }

        return 1;
    }

    @Override
    public String getName() {
        return "level";
    }


    @Override
    public LiteralArgumentBuilder<CommandSource> build()
    {
        return IMCCommand.newLiteral(getName())
                .then(IMCCommand.newArgument(COLONYID_ARG, IntegerArgumentType.integer(1))
                    .then(IMCCommand.newArgument(BUIDLINGID_ARG, IntegerArgumentType.integer(0))
                        .then(IMCCommand.newArgument(BUILDINGLVL_ARG, IntegerArgumentType.integer(0))
                            .executes(this::checkPreConditionAndExecute))));
    }
}
