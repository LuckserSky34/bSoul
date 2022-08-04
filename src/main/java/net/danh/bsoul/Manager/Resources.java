package net.danh.bsoul.Manager;

import net.danh.bsoul.CustomEvents.SoulChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static net.danh.bsoul.bSoul.getInstance;

public class Resources {

    private static File configFile, languageFile, mobFile;
    private static FileConfiguration config, language, mob;

    public static void createfiles() {
        configFile = new File(getInstance().getDataFolder(), "config.yml");
        languageFile = new File(getInstance().getDataFolder(), "language.yml");
        mobFile = new File(getInstance().getDataFolder(), "mob.yml");

        if (!configFile.exists()) getInstance().saveResource("config.yml", false);
        if (!languageFile.exists()) getInstance().saveResource("language.yml", false);
        if (!mobFile.exists()) getInstance().saveResource("mob.yml", false);
        language = new YamlConfiguration();
        config = new YamlConfiguration();
        mob = new YamlConfiguration();

        try {
            language.load(languageFile);
            config.load(configFile);
            mob.load(mobFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getconfigfile() {
        return config;
    }

    public static FileConfiguration getlanguagefile() {
        return language;
    }

    public static FileConfiguration getmobfile() {
        return mob;
    }

    public static void reloadfiles() {
        language = YamlConfiguration.loadConfiguration(languageFile);
        config = YamlConfiguration.loadConfiguration(configFile);
        mob = YamlConfiguration.loadConfiguration(mobFile);
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            SoulChangeEvent soulChangeEvent = new SoulChangeEvent(p, Data.getSoul(p));
            Bukkit.getServer().getPluginManager().callEvent(soulChangeEvent);
        }
    }

    public static void saveconfig() {
        try {
            config.save(configFile);
        } catch (IOException ignored) {
        }
    }

    public static void savelanguage() {
        try {
            language.save(languageFile);
        } catch (IOException ignored) {
        }
    }

    public static void savemob() {
        try {
            mob.save(mobFile);
        } catch (IOException ignored) {
        }
    }
}
