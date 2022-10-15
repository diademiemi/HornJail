package me.diademiemi.hornjail.command;

import net.md_5.bungee.api.ChatColor;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format {

    public static String format(String msg, String... replacements) {
        return ChatColor.translateAlternateColorCodes('&', translateHexColorCodes(replace(msg, replacements)));
    }

    public static String translateHexColorCodes(String message) {
        final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        while (matcher.find()) {
            String colour = message.substring(matcher.start(), matcher.end());
            message = message.replace(colour, ChatColor.of(colour.replace("&", "")) + "");
            matcher = hexPattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String replace(String msg, String... replacements) {
        // Create dict for replacements
        // Loop through replacements
        // alternate between key and value

        HashMap<String, String> dict = new HashMap<>();
        for (int i = 0; i < replacements.length; i++) {
            if (i % 2 == 0) {
                dict.put(replacements[i], replacements[i + 1]);
            }
        }

        // Replace in string
        for (String key : dict.keySet()) {
            msg = msg.replace("{{ " + key + " }}", dict.get(key));
        }

        return msg;
    }


}