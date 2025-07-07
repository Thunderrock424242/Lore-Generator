package com.thunder.loregenerator.config;

import com.electronwill.nightconfig.core.ConfigSpec;
import net.neoforged.fml.config.ConfigTracker;

public class LoreConfig {
    public static final ConfigSpec CONFIG;
    public static final ConfigSpec.ConfigValue<String> WORLD_DESCRIPTION;
    public static final ConfigSpec.BooleanValue AI_GENERATED;

    static {
        var builder = new ConfigSpec.Builder();
        builder.push("server_lore");

        WORLD_DESCRIPTION = builder
                .comment("Brief description of your world to seed AI-generated lore")
                .define("world_description", "The world is shattered and strange anomalies emerge from the caves.");

        AI_GENERATED = builder
                .comment("Use AI to generate lore based on world_description")
                .define("ai_generated", true);

        builder.pop();
        CONFIG = builder.build();
    }

    public static void register() {
        ConfigTracker.INSTANCE.load(CONFIG, Type.SERVER, "serverloregenerator-server.toml");
    }
}
