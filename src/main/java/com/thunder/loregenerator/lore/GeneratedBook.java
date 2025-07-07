package com.thunder.loregenerator.lore;

import java.util.List;

public class GeneratedBook {
    public final String title;
    public final String author;
    public final List<String> pages;

    public GeneratedBook(String title, String author, List<String> pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }
}