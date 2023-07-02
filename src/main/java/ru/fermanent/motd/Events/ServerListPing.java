package ru.fermanent.motd.Events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import ru.fermanent.motd.MOTD;
import ru.fermanent.motd.Utils.GradientConverter;

import java.util.Objects;

public class ServerListPing implements Listener {

    @EventHandler
    public void onUpdate(ServerListPingEvent event) {

        String motdFirstLine = GradientConverter.convertHexToGradient(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MOTD.getInstance().getConfig().getString("motd.first-line"))));
        String motdSecondLine = GradientConverter.convertHexToGradient(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MOTD.getInstance().getConfig().getString("motd.second-line"))));

        event.setMotd(motdFirstLine + "\n" + motdSecondLine);
    }

}
