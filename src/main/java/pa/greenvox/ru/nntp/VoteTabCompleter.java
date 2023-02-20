package pa.greenvox.ru.nntp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VoteTabCompleter implements @Nullable TabCompleter {

    private List<String> getAllCandidates(String enteredName) {
        List<String> users = NNTP.Instance.getConfig().getStringList("settings.candidates.all");
        List<String> result = new ArrayList<>();

        for (String user : users) {
            if (user.startsWith(enteredName)) result.add(user);
        }

        return result;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return null;
        String name = args[0];

        return getAllCandidates(name);
    }
}
