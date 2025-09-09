package com.thunder.loregenerator.lore;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thunder.loregenerator.config.LoreConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class OpenAILoreGenerator {
    public static CompletableFuture<GeneratedBook> generateBookAsync(Set<String> tags, String description, String apiKey) {
        try {
            String prompt = "Generate a Minecraft lore book with 2–4 short pages (200 characters each) based on this world: " + description +
                    ". Themes: " + String.join(", ", tags) + ". Return JSON with fields 'title', 'author', 'pages'.";

            JsonObject message = new JsonObject();
            message.addProperty("role", "user");
            message.addProperty("content", prompt);

            JsonArray messages = new JsonArray();
            messages.add(message);

            JsonObject payload = new JsonObject();
            String model = LoreConfig.OPENAI_MODEL.get();
            payload.addProperty("model", model == null || model.isBlank() ? "gpt-4o" : model);
            payload.add("messages", messages);
            payload.addProperty("temperature", 0.8);
            payload.addProperty("max_tokens", 500);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                    .build();

            return HttpClient.newHttpClient()
                    .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        if (response.statusCode() >= 400) {
                            System.err.println("OpenAI API error: " + response.body());
                            return null;
                        }
                        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
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
                    })
                    .exceptionally(e -> {
                        System.err.println("OpenAI generation failed: " + e.getMessage());
                        return null;
                    });
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    public static GeneratedBook generateBook(Set<String> tags, String description, String apiKey) {
        return generateBookAsync(tags, description, apiKey).join();
    }
}