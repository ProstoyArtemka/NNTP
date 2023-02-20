package pa.greenvox.ru.nntp;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class TimeChecker extends BukkitRunnable implements Runnable {

    @Override
    public void run() {
        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int month = cal.get(Calendar.MONTH);
        FileConfiguration config = NNTP.Instance.getConfig();

        if (config.getInt("month") != month) {
            config.set("players_played", null);
            config.set("month", month);
        }

        ConfigurationSection section = config.getConfigurationSection("players_played");
        if (!config.contains("players_played")) section = config.createSection("players_played");
        if (section == null) return;

        for (Player i : Bukkit.getOnlinePlayers()) {
            if (section.contains(i.getName())) section.set(i.getName(), section.getInt(i.getName()) + 5);
            else section.set(i.getName(), 0);
        }

        File f = new File(NNTP.Instance.getDataFolder() + "/config.yml");
        try {
            config.save(f);
        } catch (IOException ignored) {}
    }
}
