package com.ahenkeshi.unikko.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;


public class UnikkoGui extends LightweightGuiDescription {
    public UnikkoGui() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(256,240);

        WLabel label = new WLabel(new TranslatableText("epstoriclient"));
        root.add(label, 0, 0, 4, 1);

        WSprite icon = new WSprite(new Identifier("minecraft:textures/item/redstone.png"));
        root.add(icon, 0, 2, 1, 1);

        root.validate(this);
    }
}
