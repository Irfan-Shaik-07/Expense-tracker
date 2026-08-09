package com.expensetracker.model;

import java.io.Serializable;

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String iconClass;
    private String colorHex;
    private String type;

    public Category() {}

    public Category(int id, String name, String iconClass, String colorHex, String type) {
        this.id = id;
        this.name = name;
        this.iconClass = iconClass;
        this.colorHex = colorHex;
        this.type = type;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIconClass() { return iconClass; }
    public void setIconClass(String iconClass) { this.iconClass = iconClass; }

    public String getColorHex() { return colorHex; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
