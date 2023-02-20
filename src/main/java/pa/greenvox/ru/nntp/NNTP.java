package pa.greenvox.ru.nntp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class NNTP extends JavaPlugin {
    public static NNTP Instance;

    public static String getColoredString(String path) {
        return ChatColor.translateAlternateColorCodes('&', Instance.getConfig().getString(path));
    }

    @Override
    public void onEnable() {
        Instance = this;

        saveDefaultConfig();

        Bukkit.getPluginCommand("vote_reload").setExecutor(new VoteReloadCommand());
        Bukkit.getPluginCommand("vote").setExecutor(new VoteCommand());
        Bukkit.getPluginCommand("vote").setTabCompleter(new VoteTabCompleter());

        new TimeChecker().runTaskTimer(this, 0, 60 * 20 * 5);
    }

    @Override
    public void onDisable() {

    }
}
