package tw.ch1ck3n.mention;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Mention extends JavaPlugin implements Listener, CommandExecutor {

    public static Mention INSTANCE;

    public final String prefix = "&7[&aMention&7]&r".replaceAll("&", "§");

    public Configuration config;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.saveDefaultConfig();
        this.reloadConfig();
        this.config = new Configuration();

        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("mention").setExecutor(this);
    }

    @Override
    public void onDisable() {
        AsyncPlayerChatEvent.getHandlerList().unregister((Plugin) this);
        this.getCommand("mention").setExecutor(null);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) if (!sender.hasPermission("quickshulker.admin")) return true;

        Bukkit.getScheduler().runTask(Mention.INSTANCE, () -> {
            if (args.length == 1) {
                if (args[0].equals("reload")) {
                    if (sender instanceof Player) sender.sendMessage(prefix + " " + config.reloading);

                    Mention.INSTANCE.onDisable();
                    Mention.INSTANCE.onEnable();

                    if (sender instanceof Player) sender.sendMessage(prefix + " " + config.reloaded);
                } else for (String h: config.help) sender.sendMessage(h);
            } else for (String h: config.help) sender.sendMessage(h);
        });

        return true;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        for (Player p: Bukkit.getOnlinePlayers()) if (message.contains(p.getName())) {
            Bukkit.getScheduler().runTaskLater(Mention.INSTANCE, () -> {
                if (config.mode == 0) {
                    p.sendTitle(config.title.replaceFirst("%s", e.getPlayer().getName()), "", config.titleFadeIn, config.titleStay, config.titleFadeOut);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 1f, 1f);
                } else if (config.mode == 1) {
                    p.sendTitle("", config.subtitle.replaceFirst("%s", e.getPlayer().getName()), config.titleFadeIn, config.titleStay, config.titleFadeOut);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 1f, 1f);
                } else if (config.mode == 2) {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(config.actionbar.replaceFirst("%s", e.getPlayer().getName())));
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 1f, 1f);
                } else {
                    p.sendMessage((prefix + " " + "&c&lInvalid mode: " + config.mode).replaceAll("&", "§"));
                }
            }, config.runAfter);
        }
    }
}
