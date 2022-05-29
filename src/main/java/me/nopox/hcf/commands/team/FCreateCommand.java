package me.nopox.hcf.commands.team;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Flags;
import co.aikar.commands.annotation.Name;
import co.aikar.commands.annotation.Single;
import me.nopox.hcf.HCF;
import me.nopox.hcf.utils.CC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class FCreateCommand extends BaseCommand {
    public final Pattern ALPHA_NUMERIC = Pattern.compile("[^a-zA-Z0-9]");

    @CommandAlias("fcreate")
    public void onFCreate(Player player, @Name("name") @Single String name) {
        HCF.getInstance().getProfileHandler().getProfile(player.getUniqueId().toString()).thenAccept(profile -> {
            if (profile.getTeamId() != null) {
                player.sendMessage(ChatColor.RED + "You are already in a faction.");
            }
        });

        HCF.getInstance().getTeamHandler().getTeam(name).thenAccept(team -> {
            if (team != null) {
                player.sendMessage(ChatColor.RED + "A team with that name already exists.");
                return;
            }
        });

        if (name.length() > 16) {
            player.sendMessage(CC.translate("§cThe name must be 16 characters or less."));
            return;
        }

        if (ALPHA_NUMERIC.matcher(name).find()) {
            player.sendMessage(CC.translate("§cThe name must only contain letters and numbers."));
            return;
        }

        HCF.getInstance().getTeamHandler().create(name, player.getUniqueId());
        player.sendMessage(CC.translate("§aYou have created a new team."));


    }
}
