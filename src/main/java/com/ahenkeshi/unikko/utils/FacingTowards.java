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
