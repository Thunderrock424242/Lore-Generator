package com.thunder.loregenerator.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class LoreConfig {
    public static final ModConfigSpec CONFIG;
    public static final int CURRENT_VERSION = 2;
    public static final ModConfigSpec.IntValue CONFIG_VERSION;
    public static final ModConfigSpec.ConfigValue<String> WORLD_DESCRIPTION;

    static {
        var builder = new ModConfigSpec.Builder();
        builder.push("server_lore");

        CONFIG_VERSION = builder
                .comment("Internal config version. Do not modify.")
                .defineInRange("config_version", CURRENT_VERSION, 1, Integer.MAX_VALUE);

        WORLD_DESCRIPTION = builder
                .comment("Brief description of your world to seed tag detection for lore placement.")
                .define("world_description", "The world is shattered and strange anomalies emerge from the caves.");

        builder.pop();
        CONFIG = builder.build();
    }
}
