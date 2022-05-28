package me.nopox.hcf.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Name;
import co.aikar.commands.annotation.Optional;
import me.nopox.hcf.utils.CC;
import me.nopox.hcf.utils.acf.AutoRegister;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@AutoRegister
public class PlaytimeCommand extends BaseCommand {

    @CommandAlias("playtime|pt")
    public void onPlaytime(Player sender, @Optional OfflinePlayer target) {
        if (target == null) {
            sender.sendMessage(CC.translate(""));
        }
    }
}
