package pa.greenvox.ru.nntp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class VoteCommand implements CommandExecutor {

    List<String> CandidatesTypes = Arrays.asList(
            "architecture",
            "president",
            "interior",
            "culture"
    );

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            sender.sendMessage(NNTP.getColoredString("messages.not_enough_args"));
            return true;
        }

        String type = "nothing";
        String name = args[0];

        for (String s : CandidatesTypes) {
            if (NNTP.Instance.getConfig().contains("settings.candidates." + s + "." + name)) type = s;
        }

        if (!NNTP.Instance.getConfig().contains("settings.candidates." + type + "." + name)) {
            sender.sendMessage(NNTP.getColoredString("messages.this_user_isnt_exists"));
            return true;
        }

        if (NNTP.Instance.getConfig().getStringList("settings.candidates." + type + ".voiced_people").contains(sender.getName())) {
            sender.sendMessage(NNTP.getColoredString("messages.you_are_voted_already"));
            return true;
        }

        FileConfiguration config = NNTP.Instance.getConfig();

        double activity = config.getInt("players_played." + sender.getName());

        if (activity < config.getInt("settings.time_for_vote")) {
            sender.sendMessage(NNTP.getColoredString("messages.you_not_played_three_hours"));
            return true;
        }

        config.set("settings.candidates." + type + "." + name, config.getInt("settings.candidates." + type + "." + name) + 1);
        List<String> players = config.getStringList("settings.candidates." + type + ".voiced_people");
        players.add(sender.getName());

        config.set("settings.candidates." + type + ".voiced_people", players);

        File f = new File(NNTP.Instance.getDataFolder() + "/config.yml");
        try {
            config.save(f);
        } catch (IOException ignored) {}

        sender.sendMessage(NNTP.getColoredString("messages.your_voice_accepted"));

        return true;
    }
}
