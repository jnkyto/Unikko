/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

// THIS MIXIN IS CURRENTLY BROKEN AND NOT NEEDED

package com.ahenkeshi.unikko.mixin;

import com.ahenkeshi.unikko.cmd.CommandHandler;
import com.ahenkeshi.unikko.utils.SoftConfigUtils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.client.gui.screen.CommandSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.CompletableFuture;


@Mixin(CommandSuggestor.class)
public abstract class MixinCommandSuggestor {
    @Shadow private ParseResults<CommandSource> parse;
    @Shadow @Final TextFieldWidget textField;
    @Shadow @Nullable CommandSuggestor.SuggestionWindow window;
    @Shadow boolean completingSuggestions;
    @Shadow protected abstract void show();
    @Shadow private CompletableFuture<Suggestions> pendingSuggestions;

    @Inject(method = "refresh",
            at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/StringReader;canRead()Z",
                    remap = false),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void onRefresh(CallbackInfo ci, String str, StringReader reader) {
        String cmdPref = SoftConfigUtils.getString("cmdPrefix");
        int prefLength = cmdPref.length();
        if (reader.canRead(prefLength) && reader.getString().startsWith(cmdPref, reader.getCursor())) {
            reader.setCursor(reader.getCursor() + prefLength);
            CommandDispatcher<CommandSource> commandDispatcher = CommandHandler.getDISPATCHER();
            if (this.parse == null) {
                this.parse = commandDispatcher.parse(reader, CommandHandler.getCOMMAND_SOURCE());
            }
            int cursor = textField.getCursor();
            if (cursor >= 1 && (this.window == null || this.completingSuggestions)) {
                this.pendingSuggestions = commandDispatcher.getCompletionSuggestions(this.parse, cursor);
                this.pendingSuggestions.thenRun(() -> {
                    if (this.pendingSuggestions.isDone()) {
                        this.show();
                    }
                });
            }
            ci.cancel();
        }

    }
}

