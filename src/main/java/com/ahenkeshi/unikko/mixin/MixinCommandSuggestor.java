/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

// THIS MIXIN IS CURRENTLY BROKEN AND NOT NEEDED

package com.ahenkeshi.unikko.mixin;

import com.ahenkeshi.unikko.cmd.CommandManager;
import com.ahenkeshi.unikko.interfaces.ITextFieldWidget;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import net.minecraft.client.gui.screen.CommandSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*
@Mixin(CommandSuggestor.class)
public class MixinCommandSuggestor {
    @Shadow private ParseResults<CommandSource> parse;
    @Shadow @Final TextFieldWidget textField;

    @Unique private int oldMaxLength;
    @Unique private boolean wasCommand = false;

    @Inject(method = "refresh", at = @At("RETURN"))
    private void onRefresh(CallbackInfo ci) {
        boolean isCommand;
        if (parse == null) {
            isCommand = false;
        }
        else {
            StringReader reader = new StringReader(parse.getReader().getString());
            reader.skip();
            String command = reader.canRead() ? reader.readUnquotedString() : "";
            isCommand = CommandManager.isCommand(command);
        }

        if (isCommand && !wasCommand)   {
            wasCommand = true;
            oldMaxLength = ((ITextFieldWidget) textField).commands_getMaxLength();
            textField.setMaxLength(Math.max(oldMaxLength, 32500));
        } else if (!isCommand && wasCommand)    {
            wasCommand = false;
            textField.setMaxLength(oldMaxLength);
        }
}*/
