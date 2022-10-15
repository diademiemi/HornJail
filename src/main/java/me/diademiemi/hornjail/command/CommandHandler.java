package me.diademiemi.hornjail.command;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.CuboidRegion;
import me.diademiemi.hornjail.area.Area;
import me.diademiemi.hornjail.area.AreaList;
import me.diademiemi.hornjail.jail.Jail;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

    public static String help = "&l&5Horn&dJail &r&l&fHelp Page" + "\n" +
            "&7/hornjail help &8- &dDisplays this help page" + "\n" +
            "&7/hornjail area create <name> &8- &dCreates an area from your current worldedit selection" + "\n" +
            "&7/hornjail area delete <name> &8- &dDeletes an area by name" + "\n" +
            "&7/hornjail togglejail &8- &dToggles jail. Goat horns will stay disabled in areas" + "\n" +
            "&7/hornjail setjail &8- &dSets jail teleport point" + "\n" +
            "&7/hornjail togglewhitelist &8- &dToggles whether areas should function as a whitelist instead of blacklist";

    public static String noperm = "&cYou do not have permission to use this command!";

    public static String unknowncmd = "&cUnknown command. Type /hornjail help for help.";

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("hornjail")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    if (sender.hasPermission("hornjail.help")) {
                        sender.sendMessage(Format.format(help));
                        return true;
                    }
                }
                sender.sendMessage(Format.format(noperm));
            } else if (args.length > 0) {
                switch (args[0].toLowerCase()) {
                    case "help":
                        if (sender instanceof Player) {
                            if (sender.hasPermission("hornjail.help")) {
                                sender.sendMessage(Format.format(help));
                                return true;
                            }
                        }
                        sender.sendMessage(Format.format(noperm));
                        break;
                    case "area":
                        if (args.length > 1) {
                            switch (args[1].toLowerCase()) {
                                case "create":
                                    if (sender instanceof Player) {
                                        if (sender.hasPermission("hornjail.area.create")) {
                                            // Test for WorldEdit selection
                                            CuboidRegion selection = null;
                                            try {
                                                WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
                                                if (worldEdit != null) {
                                                    selection = (CuboidRegion) worldEdit.getSession((Player) sender).getSelection(worldEdit.getSession((Player) sender).getSelectionWorld());
                                                }
                                            } catch (Exception e) {
                                                selection = null;
                                            }

                                            if (args.length > 2) {
                                                new Area(args[2], selection);
                                                return true;
                                            }
                                            sender.sendMessage(Format.format("&cYou must specify an area name!"));
                                            return true;
                                        }
                                    }
                                    sender.sendMessage(Format.format(noperm));
                                    break;
                                case "delete":
                                    if (sender instanceof Player) {
                                        if (sender.hasPermission("hornjail.area.delete")) {
                                            if (args.length > 2) {
                                                AreaList.removeArea(args[2]);
                                                return true;
                                            }
                                            sender.sendMessage(Format.format("&cYou must specify an area name!"));
                                            return true;
                                        }
                                    }
                                    sender.sendMessage(Format.format(noperm));
                                    break;
                                default:
                                    sender.sendMessage(Format.format(unknowncmd));
                                    break;
                            }
                        }
                        break;
                    case "togglejail":
                        if (sender instanceof Player) {
                            if (sender.hasPermission("hornjail.togglejail")) {
                                if (Jail.toggleJail()) {
                                    sender.sendMessage(Format.format("&aJail has been enabled!"));
                                    return true;
                                } else {
                                    sender.sendMessage(Format.format("&aJail has been disabled!"));
                                    return true;
                                }
                            }
                        }
                        sender.sendMessage(Format.format(noperm));
                        break;
                    case "setjail":
                        if (sender instanceof Player) {
                            if (sender.hasPermission("hornjail.setjail")) {
                                Jail.setLocation((Player) sender);
                                return true;
                            }
                        }
                        sender.sendMessage(Format.format(noperm));
                        break;
                    case "togglewhitelist":
                        if (sender instanceof Player) {
                            if (sender.hasPermission("hornjail.togglewhitelist")) {
                                if (Jail.toggleWhitelist()) {
                                    sender.sendMessage(Format.format("&aWhitelist has been enabled!"));
                                    return true;
                                } else {
                                    sender.sendMessage(Format.format("&aWhitelist has been disabled!"));
                                    return true;
                                }
                            }
                        }
                        sender.sendMessage(Format.format(noperm));
                        break;
                    default:
                        sender.sendMessage(Format.format(unknowncmd));
                        break;
                }
            }
        }
        return true;
    }
}