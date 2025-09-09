package com.thunder.loregenerator.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class LoreConfig {
    public static final ModConfigSpec CONFIG;
    public static final int CURRENT_VERSION = 1;
    public static final ModConfigSpec.IntValue CONFIG_VERSION;
    public static final ModConfigSpec.ConfigValue<String> WORLD_DESCRIPTION;
    public static final ModConfigSpec.BooleanValue AI_GENERATED;
    public static final ModConfigSpec.ConfigValue<String> OPENAI_API_KEY;
    public static final ModConfigSpec.ConfigValue<String> LORE_GENERATION_MODE;
    public static final ModConfigSpec.ConfigValue<String> OPENAI_MODEL;

    static {
        var builder = new ModConfigSpec.Builder();
        builder.push("server_lore");

        CONFIG_VERSION = builder
                .comment("Internal config version. Do not modify.")
                .defineInRange("config_version", CURRENT_VERSION, 1, Integer.MAX_VALUE);

        WORLD_DESCRIPTION = builder
                .comment("Brief description of your world to seed AI-generated lore")
                .define("world_description", "The world is shattered and strange anomalies emerge from the caves.");

        AI_GENERATED = builder
                .comment("Use OpenAI ChatGPT-4o to generate lore based on world_description. "
                        + "Set to false to use the built-in generator instead of AI.")
                .define("ai_generated", true);

        OPENAI_API_KEY = builder
                .comment("Optional OpenAI API key. Leave blank to use the OPENAI_API_KEY environment variable.")
                .define("openai_api_key", "");

        OPENAI_MODEL = builder
                .comment("Model to use for OpenAI lore generation. Recommended: gpt-4o.")
                .define("openai_model", "gpt-4o");

        LORE_GENERATION_MODE = builder
                .comment("Mode: live, fallback, or generate_and_export")
                .define("lore_generation_mode", "fallback");

        builder.pop();
        CONFIG = builder.build();
    }
}
