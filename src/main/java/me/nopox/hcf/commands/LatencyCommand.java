package me.nopox.hcf.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import me.nopox.hcf.HCF;
import me.nopox.hcf.utils.CC;
import me.nopox.hcf.utils.acf.AutoRegister;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

@AutoRegister
public class LatencyCommand extends BaseCommand {

    @CommandAlias("latency")
    public void onLatency(CommandSender player) {
        player.sendMessage(CC.translate("&7&m-----------------------------------------"));
        player.sendMessage(CC.translate("&6&lHCF &7┃ &fLatency")); //TODO: Change this line when we're on 1.17
        player.sendMessage(CC.translate(""));
        player.sendMessage(CC.translate("&eProfiles&7: &r" + HCF.getInstance().getProfileHandler().getLastLatency() + "ms"));
        player.sendMessage(CC.translate("&eTeams&7: &r" + HCF.getInstance().getTeamHandler().getLastLatency() + "ms"));
        player.sendMessage(CC.translate("&7&m-----------------------------------------"));
    }
}
