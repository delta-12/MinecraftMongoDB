package net.sytes.deltacloud.minecraftmongodb;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class MinecraftMongoDB extends JavaPlugin {

    private MongoDBHandler mongoDBHandler;

    private void checkConfigFile() {
        File ConfigFile = new File(getDataFolder(), "config.yml");
        if (!ConfigFile.exists()) {
            saveDefaultConfig();
        }
    }

    private void setDifficulty(String difficulty) {
        if (Objects.equals(difficulty, "peaceful")) {
            Objects.requireNonNull(getServer().getWorld("world")).setDifficulty(Difficulty.PEACEFUL);
        } else if (Objects.equals(difficulty, "easy")) {
            Objects.requireNonNull(getServer().getWorld("world")).setDifficulty(Difficulty.EASY);
        } else if (Objects.equals(difficulty, "hard")) {
            Objects.requireNonNull(getServer().getWorld("world")).setDifficulty(Difficulty.HARD);
        } else {
            Objects.requireNonNull(getServer().getWorld("world")).setDifficulty(Difficulty.NORMAL);
            mongoDBHandler.setDifficulty("normal");
        }
    }

    private void setGameMode(String gameMode) {
        if (Objects.equals(gameMode, "creative")) {
            getServer().setDefaultGameMode(GameMode.CREATIVE);
        } else if (Objects.equals(gameMode, "adventure")) {
            getServer().setDefaultGameMode(GameMode.ADVENTURE);
        } else if (Objects.equals(gameMode, "spectator")) {
            getServer().setDefaultGameMode(GameMode.SPECTATOR);
        } else {
            getServer().setDefaultGameMode(GameMode.SURVIVAL);
            mongoDBHandler.setGameMode("survival");
        }
    }

    @Override
    public void onEnable() {
        checkConfigFile();
        mongoDBHandler = new MongoDBHandler(this);
        mongoDBHandler.resetPlayerCount();
        mongoDBHandler.setMaxPlayers(Integer.toString(getServer().getMaxPlayers()));
        mongoDBHandler.setWorldSeed(Long.toString(Objects.requireNonNull(getServer().getWorld("world")).getSeed()));
        setDifficulty(mongoDBHandler.getDifficulty());
        setGameMode(mongoDBHandler.getGameMode());
        String version = getServer().getVersion();
        version = version.substring(version.lastIndexOf(' ') + 1);
        mongoDBHandler.setVersion(version.substring(0, version.length() - 1));
        mongoDBHandler.setStatusOnline();
        getServer().getPluginManager().registerEvents(new PlayerLoginListener(mongoDBHandler), this);
    }

    @Override
    public void onDisable() {
        mongoDBHandler.setStatusOffline();
    }
}
