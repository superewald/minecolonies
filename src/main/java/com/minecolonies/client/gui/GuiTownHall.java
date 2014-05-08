package com.minecolonies.client.gui;

import com.minecolonies.tileentities.TileEntityTownHall;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class GuiTownHall extends GuiBase
{
    private TileEntityTownHall tileEntityTownHall;
    private int numberOfButtons        = 8;
    private int idBuildTownhall        = 0;
    private int idRepairTownhall       = 1;
    private int idRecallCitizens       = 2;
    private int idToggleSpecialization = 3;
    private int idRenameColony         = 4;
    private int idInformation          = 5;
    private int idActions              = 6;
    private int idSettings             = 7;
    private int middleX                = 0;
    private int middleY                = 0;
    private int buttonWidth            = 116;
    private int buttonHeight           = 20;
    private int buttonSpan             = 4;
    private int span                   = 30;

    public GuiTownHall(TileEntityTownHall tileEntityTownHall)
    {
        super();
        this.tileEntityTownHall = tileEntityTownHall;
    }

    @Override
    protected void addButtons()
    {
        middleX = (width / 2);
        middleY = (height - ySize) / 2;

        buttonList.clear();

        int y = span;
        buttonList.add(new GuiButton(idBuildTownhall, middleX - buttonWidth / 2, middleY + y, buttonWidth, buttonHeight, I18n.format("com.minecolonies.gui.townhall.build")));
        y += buttonHeight + buttonSpan;
        buttonList.add(new GuiButton(idRepairTownhall, middleX - buttonWidth / 2, middleY + y, buttonWidth, buttonHeight, I18n.format("com.minecolonies.gui.townhall.repair")));
        y += buttonHeight + buttonSpan;
        buttonList.add(new GuiButton(idRecallCitizens, middleX - buttonWidth / 2, middleY + y, buttonWidth, buttonHeight, I18n.format("com.minecolonies.gui.townhall.recall")));
        y += buttonHeight + buttonSpan;
        buttonList.add(new GuiButton(idToggleSpecialization, middleX - buttonWidth / 2, middleY + y, buttonWidth, buttonHeight, I18n.format("com.minecolonies.gui.townhall.togglespec")));
        y += buttonHeight + buttonSpan;
        //Current Spec
        y += buttonHeight + buttonSpan;
        buttonList.add(new GuiButton(idRenameColony, middleX - buttonWidth / 2, middleY + y, buttonWidth, buttonHeight, I18n.format("com.minecolonies.gui.townhall.rename")));

        //Bottom navigation
        buttonList.add(new GuiButton(idInformation, middleX - 76, middleY + ySize - 34, 64, buttonHeight, I18n.format("com.minecolonies.gui.townhall.information")));
        buttonList.add(new GuiButton(idActions, middleX - 10, middleY + ySize - 34, 44, buttonHeight, I18n.format("com.minecolonies.gui.townhall.actions")));
        buttonList.add(new GuiButton(idSettings, middleX + xSize / 2 - 50, middleY + ySize - 34, 46, buttonHeight, I18n.format("com.minecolonies.gui.townhall.settings")));
    }

    protected void drawGuiForeground()
    {
        String currentSpec = I18n.format("com.minecolonies.gui.townhall.currentSpecialization");
        String spec = "<Industrial>"; //TODO replace with actual specialisation
        String currentTownhallName = I18n.format("com.minecolonies.gui.townhall.currTownhallName");
        String townhallName = "FreeZ City"; //TODO replace with actual name

        fontRendererObj.drawString(currentSpec, middleX - fontRendererObj.getStringWidth(currentSpec) / 2 + 3, middleY + span + 4 * (buttonHeight + buttonSpan), 0x000000);
        fontRendererObj.drawString(spec, middleX - fontRendererObj.getStringWidth(spec) / 2 + 3, middleY + span + 4 * (buttonHeight + buttonSpan) + 11, 0x000000);
        fontRendererObj.drawString(currentTownhallName, middleX - fontRendererObj.getStringWidth(currentTownhallName) / 2 + 3, middleY + 4, 0x000000);
        fontRendererObj.drawString(townhallName, middleX - fontRendererObj.getStringWidth(townhallName) / 2 + 3, middleY + 13, 0x000000);
    }
}
