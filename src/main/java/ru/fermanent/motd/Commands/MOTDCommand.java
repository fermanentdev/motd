package ru.fermanent.motd.Commands;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import ru.fermanent.motd.MOTD;
import ru.fermanent.motd.Utils.Messages;

import java.util.List;

public class MOTDCommand extends AbstractCommand {
    public MOTDCommand() {
        super("mtd");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        if (!sender.hasPermission("motd.usage")) {
            Messages.noPermission.replace("{prefix}", MOTD.getPrefix()).send(sender);
            return;
        }

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            Messages.help.replace("{prefix}", MOTD.getPrefix()).replace("{alias}", label).send(sender);
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            MOTD.getInstance().reloadConfig();
            Messages.load(MOTD.getInstance().getConfig());
            Messages.reload.replace("{prefix}", MOTD.getPrefix()).send(sender);
            return;
        }

    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if(args.length == 1) return Lists.newArrayList("reload", "help");
        return Lists.newArrayList();
    }
}
