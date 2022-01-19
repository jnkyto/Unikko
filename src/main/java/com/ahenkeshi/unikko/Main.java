/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/


package com.ahenkeshi.unikko;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Joona Kytöniemi kjoona@outlook.com
 * @since 1.1.0
 *
 * Shows an option pane when double-clicking Unikko's jar file.
 * Mostly skidded from Meteor Client
 */
public class Main {
    public static void main(String[] args)  {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                UnsupportedLookAndFeelException e)  {
            e.printStackTrace();
        }
        int option = JOptionPane.showOptionDialog(
                null,
                "To install Unikko, put it in your mods folder with Fabric API, and run\n" +
                        "Minecraft with the latest version of Fabric Loader installed",
                "Unikko",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[] { "Fabric Loader", "Fabric API" },
                null
        );

        if(option == 0) {
            openUrl("https://fabricmc.net/use/");
        } else if(option == 1)  {
            openUrl("https://www.curseforge.com/minecraft/mc-mods/fabric-api/files");
        }
    }

    /**
     * @author MeteorDevelopment
     * @param url Url to be opened
     */
    public static void openUrl(String url) {
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(url));
                }
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")) {
                Runtime.getRuntime().exec("xdg-open " + url);
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
