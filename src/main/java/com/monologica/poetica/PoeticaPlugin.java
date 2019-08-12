package com.monologica.poetica;

import com.monologica.poetica.modules.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Main class for the plugin
 */
public class PoeticaPlugin extends JavaPlugin {

    private PoeticaController controller;

    /**
     * Initialize the plugin. Called when the plugin is loaded
     */
    @Override
    public void onEnable() {
        extractConfigFiles();
        initializePoetica();
    }

    /**
     * Reloads the plugin config, re-initializing the helper modules as it does so
     */
    @Override
    public void reloadConfig() {
        super.reloadConfig();
        extractConfigFiles();
        initializePoetica();
    }

    /**
     * Extracts necessary resource files from the jar
     */
    public void extractConfigFiles() {
        extractFileFromJar("rarities.yml");
        extractFileFromJar("customItems.yml");
        extractFileFromJar("archetypes.yml");
        extractFileFromJar("prefixes.txt");
        extractFileFromJar("suffixes.txt");
    }

    /**
     * (Re-)Initializes the backing controller of the plugin
     */
    public void initializePoetica() {
        try {
            controller = new PoeticaController();
        } catch (Exception e) {
            getLogger().severe("Poetica was not able to initialize. Disabling.");
            e.printStackTrace();
            this.setEnabled(false);
        }
    }

    /**
     * Extracts a file from the jar, but does not replace it if it already exists
     *
     * @param path path inside the jar to extract from
     * @return The extracted file
     */
    public File extractFileFromJar(String path) {
        File file = new File(getDataFolder(), path);

        // Copy file from inside jar
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            saveResource(path, false);
        }
        return file;
    }

    public ArchetypeManager getArchetypeManager() {
        return controller.archetypeManager;
    }

    public ItemParser getItemParser() {
        return controller.itemParser;
    }

    public ItemStatManager getItemStatManager() {
        return controller.itemStatManager;
    }

    public ModManager getModManager() {
        return controller.modManager;
    }

    public PlayerLevelManager getPlayerLevelManager() {
        return controller.playerLevelManager;
    }

    public PlayerStatManager getPlayerStatManager() {
        return controller.playerStatManager;
    }

    public RarityManager getEquipmentRarities() {
        return controller.equipmentRarities;
    }

    public ItemGenerator getItemGenerator() {
        return controller.itemGenerator;
    }

    public CustomItemManager getCustomItemManager() {
        return controller.customItemManager;
    }
}
