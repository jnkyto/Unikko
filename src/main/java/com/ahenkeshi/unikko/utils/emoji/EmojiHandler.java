package com.ahenkeshi.unikko.utils.emoji;

import com.ahenkeshi.unikko.Unikko;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.crash.CrashReport;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Joona Kytöniemi kjoona@outlook.com
 * @since 1.2.0
 *
 * Originally from SparxySys/minecraft-discordemoji:
 * https://github.com/SparxySys/minecraft-discordemoji
 *
 * (with small edits)
 */
public class EmojiHandler {
    private final Pattern emojiPattern;
    private final Map<String, Character> emojiMap;
    private final Collection<String> emojiSuggestList;

    public static EmojiHandler getInstance() {
            return InstanceHolder.INSTANCE;
    }

    private EmojiHandler() {
        this.emojiPattern = Pattern.compile(":([^\\s:]+):");
        this.emojiMap = loadEmojis();
        this.emojiSuggestList = new ArrayList<>();
        for(String entry : emojiMap.keySet())   {
            emojiSuggestList.add(entry+":");
        }
    }

    public String transformEmojis(String text) {
        StringBuilder newText = new StringBuilder();
        Matcher matcher = this.emojiPattern.matcher(text);
        while (matcher.find()) {
            String emoji = matcher.group(1);
            if (emoji != null && emoji.length() > 0) {
                matcher.appendReplacement(newText, getEmojiFor(emoji));
            }
        }
        matcher.appendTail(newText);
        return newText.toString();
    }

    private String getEmojiFor(String emoji) {
        Character data = this.emojiMap.get(emoji);
        if (data != null) {
            return data.toString();
        }
        return ":" + emoji + ":";
    }

    private Map<String, Character> loadEmojis() {
        Map<String, Character> emojis = new HashMap<>();
        char emojiChar = '\uac00';
        try {
            String emojiTableLoc = "/assets/" + Unikko.MODID.toLowerCase(Locale.ROOT) + "/emojitable.txt";
            InputStream listInput = getClass().getResourceAsStream(emojiTableLoc);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(listInput)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                emojis.put(line, emojiChar++);
            }
            bufferedReader.close();
            listInput.close();
        } catch (Exception e) {
            CrashReport report = new CrashReport("Unikko was trying to load Emojis.", e);
            e.printStackTrace();
            MinecraftClient.printCrashReport(report);
        }
        Unikko.LOGGER.info("Emojis were loaded successfully!");
        return ImmutableMap.copyOf(emojis);
    }

    public Collection<String> getEmojiSuggestList()   {
        return emojiSuggestList;
    }

    private static class InstanceHolder {
        private static final EmojiHandler INSTANCE = new EmojiHandler();
    }
}