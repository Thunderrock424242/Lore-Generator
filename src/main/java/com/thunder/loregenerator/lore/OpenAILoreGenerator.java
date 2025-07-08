package com.thunder.loregenerator.lore;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thunder.loregenerator.config.LoreConfig;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OpenAILoreGenerator {
    public static GeneratedBook generateBook(Set<String> tags, String description, String apiKey) {
        try {
            URL url = new URL("https://api.openai.com/v1/chat/completions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");

            String prompt = "Generate a Minecraft lore book with 2–4 short pages (200 characters each) based on this world: " + description +
                    ". Themes: " + String.join(", ", tags) + ". Return JSON with fields 'title', 'author', 'pages'.";

            JsonObject message = new JsonObject();
            message.addProperty("role", "user");
            message.addProperty("content", prompt);

            JsonArray messages = new JsonArray();
            messages.add(message);

            JsonObject payload = new JsonObject();
            payload.addProperty("model", "gpt-4");
            payload.add("messages", messages);
            payload.addProperty("temperature", 0.8);
            payload.addProperty("max_tokens", 500);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.toString().getBytes());
            }

            try (InputStreamReader reader = new InputStreamReader(conn.getInputStream())) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                String content = json.getAsJsonArray("choices")
                        .get(0).getAsJsonObject()
                        .get("message").getAsJsonObject()
                        .get("content").getAsString();

                JsonObject bookObj = JsonParser.parseString(content).getAsJsonObject();
                String title = bookObj.get("title").getAsString();
                String author = bookObj.get("author").getAsString();
                List<String> pages = bookObj.get("pages").getAsJsonArray().asList().stream()
                        .map(JsonElement::getAsString)
                        .collect(Collectors.toList());

                if (LoreConfig.LORE_GENERATION_MODE.get().equalsIgnoreCase("generate_and_export")) {
                    PreGeneratedLoreSaver.save(title, author, pages, tags);
                }

                return new GeneratedBook(title, author, pages);
            }
        } catch (Exception e) {
            System.err.println("OpenAI generation failed: " + e.getMessage());
            return null;
        }
    }
}