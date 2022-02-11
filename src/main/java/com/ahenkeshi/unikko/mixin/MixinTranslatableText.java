package com.ahenkeshi.unikko.mixin;

import com.ahenkeshi.unikko.utils.emoji.EmojiHandler;
import com.google.common.collect.ImmutableList;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.ArrayList;
import java.util.List;

@Mixin(TranslatableText.class)
public abstract class MixinTranslatableText {
    @Shadow private List<StringVisitable> translations;

    @Inject(method = "visitSelf(Lnet/minecraft/text/StringVisitable$StyledVisitor;Lnet/minecraft/text/Style;)Ljava/util/Optional;", at = @At("HEAD"))
    public void visitSelf(CallbackInfoReturnable<?> callbackInfo) {
            addEmojisToPlainTexts();
    }
    private void addEmojisToPlainTexts() {
        ArrayList<StringVisitable> newTexts = new ArrayList<>();
        EmojiHandler instance = EmojiHandler.getInstance();
        for (StringVisitable translation : this.translations) {
            if (!(translation instanceof Text)) {
                newTexts.add(StringVisitable.plain(instance.transformEmojis(translation.getString())));
            } else {
                newTexts.add(translation);
            }
        }
        this.translations = ImmutableList.copyOf(newTexts);
    }
}
