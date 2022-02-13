package com.ahenkeshi.unikko.mixin;

import com.ahenkeshi.unikko.utils.emoji.EmojiHandler;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiteralText.class)
public class MixinLiteralText {
    @Mutable @Final @Shadow private String string;

    @Inject(method = "getRawString", at=@At("HEAD"))
    public void getRawString(CallbackInfoReturnable<String> cir) {
        this.string = EmojiHandler.getInstance().transformEmojis(this.string);
    }

    @Inject(method = "asString", at=@At("HEAD"))
    public void asString(CallbackInfoReturnable<String> cir) {
        this.string = EmojiHandler.getInstance().transformEmojis(this.string);
    }
}
