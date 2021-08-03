package com.myapplicationdev.android.taskmanager;

import java.io.Serializable;

public class Task implements Serializable {
    String name;
    String description;
    int id;

    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Task(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return  id + " " + name + "\n" + description;
    }
}
