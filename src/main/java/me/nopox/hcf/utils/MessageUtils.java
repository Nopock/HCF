package me.nopox.hcf.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.val;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class MessageUtils {
    private TextComponent component;

    public static MessageUtils of(String text) {
        return new MessageUtils(text);
    }

    private MessageUtils(String text) {
        this.component = new TextComponent(CC.translate(text));
    }

    public MessageUtils text(String text) {
        this.component = new TextComponent(CC.translate(text));
        return this;
    }

    public MessageUtils color(ChatColor color) {
        this.component.setColor(color.asBungee());
        return this;
    }

    public MessageUtils clickable(String cmd) {
        this.component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + cmd));
        return this;
    }

    public MessageUtils hover(String text) {
        this.component.setText(CC.translate(text));
        return this;
    }

    public MessageUtils send(Player player){
        player.spigot().sendMessage(this.component);
        return this;
    }
}
