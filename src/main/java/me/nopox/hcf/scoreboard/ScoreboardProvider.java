package me.nopox.hcf.scoreboard;

import io.github.thatkawaiisam.assemble.AssembleAdapter;
import me.nopox.hcf.HCF;
import me.nopox.hcf.utils.CC;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardProvider implements AssembleAdapter {
    @Override
    public String getTitle(Player player) {
        return CC.translate(HCF.getInstance().getConfig().getString("scoreboard.title"));
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();

        lines.add("&7&m--------------------------------");
        lines.add("");
        lines.add("&e&7&m--------------------------------");



        return null;
    }
}
