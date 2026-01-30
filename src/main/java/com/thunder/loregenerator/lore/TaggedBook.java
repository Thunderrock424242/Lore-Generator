package com.thunder.loregenerator.lore;

import java.util.Set;

public record TaggedBook(GeneratedBook book, Set<String> tags) {
}
