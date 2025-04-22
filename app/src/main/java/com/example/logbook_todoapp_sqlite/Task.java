package com.example.logbook_todoapp_sqlite;

public class Task {
    private int id; // Add an ID for database management
    private String name;
    private boolean completed;

    public Task(String name) {
        this.name = name;
        this.completed = false; // Default to not completed
    }

    public Task(int id, String name, boolean completed) {
        this.id = id;
        this.name = name;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}