package com.minecolonies.coremod.commands.buildingcommands;

import com.ldtteam.structurize.util.LanguageHandler;
import com.minecolonies.api.colony.IColony;
import com.minecolonies.api.colony.IColonyManager;
import com.minecolonies.api.colony.buildings.IBuilding;
import com.minecolonies.api.util.Log;
import com.minecolonies.coremod.MineColonies;
import com.minecolonies.coremod.commands.commandTypes.IMCColonyOfficerCommand;
import com.minecolonies.coremod.commands.commandTypes.IMCCommand;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.event.ClickEvent;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.minecolonies.coremod.commands.CommandArgumentNames.COLONYID_ARG;
import static com.minecolonies.coremod.commands.colonycommands.CommandListColonies.START_PAGE_ARG;

public class CommandBuildingsList implements IMCColonyOfficerCommand {
    private static final String LIST_COMMAND_SUGGESTED = "/minecolonies buildings list %d %d";
    private static final String COMMAND_BUILDING_INFO   = "/minecolonies buildings info %d %d";

    @Override
    public int onExecute(CommandContext<CommandSource> context) { return displayListFor(context, 1); }

    private int executeWithPage(final CommandContext<CommandSource> context)
    {
        if (!checkPreCondition(context))
        {
            return 0;
        }

        return displayListFor(context, IntegerArgumentType.getInteger(context, START_PAGE_ARG));
    }

    private int displayListFor(final CommandContext<CommandSource> context, int page) {
        // Colony
        final int colonyID = IntegerArgumentType.getInteger(context, COLONYID_ARG);
        final IColony colony = IColonyManager.getInstance().getColonyByDimension(colonyID, context.getSource().getLevel().dimension());
        if (colony == null)
        {
            context.getSource().sendSuccess(LanguageHandler.buildChatComponent("com.minecolonies.command.colonyidnotfound", colonyID), true);
            return 0;
        }

        final Map<BlockPos, IBuilding> buildings = colony.getBuildingManager().getBuildings();

        final List<IBuilding> buildingList = new ArrayList<>(buildings.values());
        for(IBuilding building : buildingList) {
            context.getSource().sendSuccess(new StringTextComponent(buildingList.indexOf(building) + ": " + building.getCustomBuildingName() + " (" + building.getBuildingLevel() + ")"), true);
        }

        return 1;
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public LiteralArgumentBuilder<CommandSource> build()
    {
        return IMCCommand.newLiteral(getName())
                .then(IMCCommand.newArgument(COLONYID_ARG, IntegerArgumentType.integer(1))
                        .executes(this::checkPreConditionAndExecute)
                        .then(IMCCommand.newArgument(START_PAGE_ARG, IntegerArgumentType.integer(1)).executes(this::executeWithPage)));
    }
}
