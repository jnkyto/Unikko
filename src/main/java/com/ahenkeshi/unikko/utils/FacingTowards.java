/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.utils;

public class FacingTowards {
    public static String get(String yaw) {
        return switch (yaw) {
            case "north" -> "[-Z]";
            case "east" -> "[+X]";
            case "south" -> "[+Z]";
            case "west" -> "[-X]";
            default -> "switch/case error";
        };
    }
}
