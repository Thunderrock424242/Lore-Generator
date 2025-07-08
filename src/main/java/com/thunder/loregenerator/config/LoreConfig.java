package com.thunder.loregenerator.config;

import net.neoforged.fml.config.ConfigTracker;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class LoreConfig {
    public static final ModConfigSpec CONFIG;
    public static final ModConfigSpec.ConfigValue<String> WORLD_DESCRIPTION;
    public static final ModConfigSpec.BooleanValue AI_GENERATED;
    public static final ModConfigSpec.ConfigValue<String> OPENAI_API_KEY;
    public static final ModConfigSpec.ConfigValue<String> LORE_GENERATION_MODE;

    static {
        var builder = new ModConfigSpec.Builder();
        builder.push("server_lore");

        WORLD_DESCRIPTION = builder
                .comment("Brief description of your world to seed AI-generated lore")
                .define("world_description", "The world is shattered and strange anomalies emerge from the caves.");

        AI_GENERATED = builder
                .comment("Use AI to generate lore based on world_description")
                .define("ai_generated", true);

        OPENAI_API_KEY = builder
                .comment("Optional OpenAI API key. Leave blank to disable live AI lore.")
                .define("openai_api_key", "");

        LORE_GENERATION_MODE = builder
                .comment("Mode: live, fallback, or generate_and_export")
                .define("lore_generation_mode", "fallback");

        builder.pop();
        CONFIG = builder.build();
    }
}
