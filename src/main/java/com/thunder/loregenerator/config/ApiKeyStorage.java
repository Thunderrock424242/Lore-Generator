package com.thunder.loregenerator.config;

import com.thunder.loregenerator.Core.ModConstants;
import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

/**
 * Handles loading and persisting the OpenAI API key to an obfuscated file.
 */
public class ApiKeyStorage {
    private static final String FILE_NAME = "loregenerator.key";

    private static String obfuscate(String key) {
        return new StringBuilder(Base64.getEncoder().encodeToString(key.getBytes())).reverse().toString();
    }

    private static String deobfuscate(String encoded) {
        try {
            return new String(Base64.getDecoder().decode(new StringBuilder(encoded).reverse().toString()));
        } catch (IllegalArgumentException e) {
            ModConstants.LOGGER.warn("Failed to decode API key file", e);
            return null;
        }
    }

    /**
     * Returns the OpenAI key from the environment, obfuscated file, or config.
     * Writes the key to an obfuscated file the first time it is seen in the config.
     */
    public static String loadKey() {
        String env = System.getenv("OPENAI_API_KEY");
        if (env != null && !env.isBlank()) {
            return env;
        }

        Path path = FMLPaths.GAMEDIR.get().resolve(FILE_NAME);
        if (Files.exists(path)) {
            try {
                String encoded = Files.readString(path).trim();
                String decoded = deobfuscate(encoded);
                if (decoded != null && !decoded.isBlank()) {
                    return decoded;
                }
            } catch (IOException e) {
                ModConstants.LOGGER.warn("Failed to read API key file", e);
            }
        }

        String key = LoreConfig.OPENAI_API_KEY.get();
        if (key != null && !key.isBlank()) {
            try {
                Files.writeString(path, obfuscate(key), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                ModConstants.LOGGER.warn("Failed to persist API key", e);
            }
            return key;
        }

        return null;
    }
}

