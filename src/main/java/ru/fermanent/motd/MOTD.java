package ru.fermanent.motd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import ru.fermanent.motd.Commands.MOTDCommand;
import ru.fermanent.motd.Events.ServerListPing;
import ru.fermanent.motd.Utils.GradientConverter;
import ru.fermanent.motd.Utils.Messages;

import java.util.Objects;

public final class MOTD extends JavaPlugin {

    private static MOTD instance;

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        saveDefaultConfig();

        Messages.load(getConfig());

        new MOTDCommand();

        Bukkit.getPluginManager().registerEvents(new ServerListPing(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String getPrefix() {
        return GradientConverter.convertHexToGradient(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getInstance().getConfig().getString("prefix"))));
    }

    public static MOTD getInstance() {
        return instance;
    }
}
