/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.mixin;

import com.ahenkeshi.unikko.Unikko;
import com.ahenkeshi.unikko.cmd.CommandHandler;
import com.ahenkeshi.unikko.utils.emoji.EmojiHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
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

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    private void refresh(CallbackInfo ci, String str, StringReader reader) {
        String cmdPref = Unikko.SOFTCONFIG.cmdPrefix.value();
        String emojiPref = ":";
        if (reader.canRead(cmdPref.length()) && reader.getString().startsWith(cmdPref, reader.getCursor())) {
            reader.setCursor(reader.getCursor() + cmdPref.length());
            CommandDispatcher<CommandSource> commandDispatcher = CommandHandler.getDISPATCHER();
            if (this.parse == null) {
                this.parse = commandDispatcher.parse(reader, CommandHandler.getCOMMAND_SOURCE());
            }
            int cursor = this.textField.getCursor();
            if (cursor >= 1 && (this.window == null || this.completingSuggestions)) {
                this.pendingSuggestions = commandDispatcher.getCompletionSuggestions(this.parse, cursor);
                this.pendingSuggestions.thenRun(() -> {
                    if (this.pendingSuggestions.isDone()) {
                        this.show();
                    }
                });
            }
            ci.cancel();
        } else if (reader.canRead(emojiPref.length()) && reader.getString().contains(emojiPref))  {
                Collection<String> collection = EmojiHandler.getInstance().getEmojiSuggestList();
                reader.setCursor(reader.getCursor() + emojiPref.length());
                int cursor = this.textField.getCursor();
                String string = str.substring(0, cursor);
                int j = getEmojiNameStart(string);
                this.pendingSuggestions = CommandSource.suggestMatching(collection, new SuggestionsBuilder(string, j));
                if (cursor >= 1 && (this.window == null || this.completingSuggestions)) {
                    this.pendingSuggestions.thenRun(() -> {
                        if (this.pendingSuggestions.isDone()) {
                            this.show();
                        }
                    });
                }
            ci.cancel();
            }
        }

    private int getEmojiNameStart(String input) {
        Pattern emojiMatcher = Pattern.compile("(:)(?<!\\w:)");

        int i = 0;
        for(Matcher matcher = emojiMatcher.matcher(input); matcher.find(); i = matcher.end()) {}
        return i;
    }
}

