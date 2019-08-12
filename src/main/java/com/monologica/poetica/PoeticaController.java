package com.monologica.poetica;

import com.monologica.poetica.config.ConfigPaths;
import com.monologica.poetica.modules.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the overall operation of the Poetica plugin
 */
public class PoeticaController {
    protected ArchetypeManager archetypeManager;
    protected ItemParser itemParser;
    protected ItemStatManager itemStatManager;
    protected ModManager modManager;
    protected PlayerLevelManager playerLevelManager;
    protected PlayerStatManager playerStatManager;
    protected RarityManager equipmentRarities;
    protected ItemGenerator itemGenerator;
    protected CustomItemManager customItemManager;

    public PoeticaController() throws IOException, InvalidConfigurationException {
        initializeModules();
    }

    /**
     * Initializes the helper modules
     */
    public void initializeModules() throws IOException, InvalidConfigurationException {
        modManager = new ModManager();
        itemStatManager = new ItemStatManager();
        equipmentRarities = new RarityManager(loadConfig(ConfigPaths.RARITIES));
        archetypeManager = new ArchetypeManager(loadConfig(ConfigPaths.ARCHETYPES), itemStatManager);
        customItemManager = new CustomItemManager(loadConfig(ConfigPaths.CUSTOM_ITEMS), itemStatManager, archetypeManager, modManager);
        itemGenerator = new ItemGenerator(equipmentRarities, archetypeManager, customItemManager, modManager,
                itemStatManager, readTextFile(ConfigPaths.PREFIXES), readTextFile(ConfigPaths.SUFFIXES));

        itemParser = new ItemParser(this);
        playerStatManager = new PlayerStatManager(itemParser);
        playerLevelManager = new PlayerLevelManager();
    }

    /**
     * Loads a yml configuration file
     *
     * @param path path to a yml configuration file
     * @return the configuration file
     */
    private YamlConfiguration loadConfig(String path) throws IOException, InvalidConfigurationException {
        File configFile = new File(path);
        System.out.println("Loading config file: " + configFile.getAbsolutePath());

        // Load YAML
        YamlConfiguration configYaml = new YamlConfiguration();
        configYaml.load(configFile);

        return configYaml;
    }

    /**
     * Reads a text file
     *
     * @param path path to a text file
     * @return a list of all lines in the text file
     */
    public List<String> readTextFile(String path) throws IOException {
        List<String> lines = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }


    public ArchetypeManager getArchetypeManager() {
        return archetypeManager;
    }

    public ItemParser getItemParser() {
        return itemParser;
    }

    public ItemStatManager getItemStatManager() {
        return itemStatManager;
    }

    public ModManager getModManager() {
        return modManager;
    }

    public PlayerLevelManager getPlayerLevelManager() {
        return playerLevelManager;
    }

    public PlayerStatManager getPlayerStatManager() {
        return playerStatManager;
    }

    public RarityManager getEquipmentRarities() {
        return equipmentRarities;
    }

    public ItemGenerator getItemGenerator() {
        return itemGenerator;
    }

    public CustomItemManager getCustomItemManager() {
        return customItemManager;
    }

}
