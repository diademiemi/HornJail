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
            "&7/hornjail area list &8- &d Lists all areas and their region" + "\n" +
            "&7/hornjail area delete <name> &8- &dDeletes an area by name" + "\n" +
            "&7/hornjail togglejail &8- &dToggles whether to teleport players to the jail." + "\n" +
            "&7/hornjail setjail &8- &dSets jail teleport point" + "\n" +
            "&7/hornjail togglewhitelist &8- &dToggles whether areas should function as a whitelist instead of blacklist" + "\n" +
            "&7/hornjail setdenymessage &8- &dSet message to show when player tries to use horn in a blacklisted area" + "\n" +
            "&7/hornjail setjailmessage &8- &dSet message to show when player is sent to Horn Jail";

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
                                case "list":
                                    if (sender instanceof Player) {
                                        if (sender.hasPermission("hornjail.area.list")) {
                                            sender.sendMessage(Format.format("&l&5Horn&dJail &r&l&fArea List"));
                                            for (Area area : AreaList.getAreas().values()) {
                                                sender.sendMessage(Format.format("&7- &d" + area.getName()));
                                                sender.sendMessage(Format.format("  " + "&7" +
                                                        area.getRegion().getMinimumPoint().getX() + "&8, &7" +
                                                        area.getRegion().getMinimumPoint().getY() + "&8, &7" +
                                                        area.getRegion().getMinimumPoint().getZ() + "&8 to &7" +
                                                        area.getRegion().getMaximumPoint().getX() + "&8, &7" +
                                                        area.getRegion().getMaximumPoint().getY() + "&8, &7" +
                                                        area.getRegion().getMaximumPoint().getZ() + "&8 in &7" +
                                                        area.getRegion().getWorld().getName()));
                                            }
                                            if (AreaList.getAreas().size() == 0) {
                                                sender.sendMessage(Format.format("&7No areas have been created yet!"));
                                            }
                                            return true;
                                        }
                                    }
                                    sender.sendMessage(Format.format(noperm));
                                    break;
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
                                                sender.sendMessage(Format.format("&aArea created!"));
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
                                                sender.sendMessage(Format.format("&aArea deleted!"));
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
                                sender.sendMessage(Format.format("&aJail location set!"));
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

                    case "setdenymessage":
                        if (sender.hasPermission("hornjail.setdenymessage")) {
                            if (args.length > 1) {
                                String message = "";
                                for (int i = 1; i < args.length; i++) {
                                    message += args[i] + " ";
                                }
                                Jail.setDenyMessage(message);
                                sender.sendMessage(Format.format("&aDeny message set!"));
                                return true;
                            }
                            sender.sendMessage(Format.format("&cYou must specify a message!"));
                            return true;
                        }
                        sender.sendMessage(Format.format(noperm));
                        break;
                    case "setjailmessage":
                        if (sender.hasPermission("hornjail.setjailmessage")) {
                            if (args.length > 1) {
                                String message = "";
                                for (int i = 1; i < args.length; i++) {
                                    message += args[i] + " ";
                                }
                                Jail.setJailMessage(message);
                                sender.sendMessage(Format.format("&aJail message set!"));
                                return true;
                            }
                            sender.sendMessage(Format.format("&cYou must specify a message!"));
                            return true;
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