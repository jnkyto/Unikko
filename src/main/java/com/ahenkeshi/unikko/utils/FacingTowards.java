/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.utils;

public class FacingTowards {
    public static String get(String yaw) {
        String yawStr;
        switch(yaw)    {
            case "north":
                yawStr = "[-Z]";
                break;
            case "east":
                yawStr = "[+X]";
                break;
            case "south":
                yawStr = "[+Z]";
                break;
            case "west":
                yawStr = "[-X]";
                break;
            default:
                yawStr = "switch/case error";
                break;
        }
        return yawStr;
    }
}
