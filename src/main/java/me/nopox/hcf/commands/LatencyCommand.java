package me.nopox.hcf.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import me.nopox.hcf.HCF;
import me.nopox.hcf.utils.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

public class LatencyCommand extends BaseCommand {

    @CommandAlias("latency")
    public void onLatency(CommandSender sender) {

        sender.sendMessage(CC.translate("&6&lHCF Database Latency"));
        sender.sendMessage(CC.translate("&7&m-----------------------------"));
        sender.sendMessage(CC.translate("&7Profiles << &6" + HCF.getInstance().getProfileHandler().getLastLatency() + "ms"));
        sender.sendMessage(CC.translate("&7Teams << &6" + HCF.getInstance().getTeamHandler().getLastLatency() + "ms"));
        sender.sendMessage(CC.translate("&c"));
        sender.sendMessage(CC.translate("&7" + HCF.getInstance().getProfileHandler().getCachedProfiles().size() + " Profiles Cached in Memory"));
        sender.sendMessage(CC.translate("&7&m-----------------------------"));

    }
}
