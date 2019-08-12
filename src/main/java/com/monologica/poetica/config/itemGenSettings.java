package com.monologica.poetica.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

public class itemGenSettings {
    private float customChance;

    public itemGenSettings(YamlConfiguration yml) {
    }

    public float getCustomChance() {
        return customChance;
    }
}
