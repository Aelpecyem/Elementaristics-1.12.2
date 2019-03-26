package de.aelpecyem.elementaristics.misc.elements;

import net.minecraft.client.resources.I18n;

public class Aspect {
    private int id;
    private String name;
    private int color;

    //maybe other stuff
    public Aspect(String name, int color) {
        this.name = name;
        this.id = ElementInit.getElements().size();
        this.color = color;
        ElementInit.getElements().add(this);
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getLocalizedName() {
        return I18n.format("aspect." + name + ".name");
    }

    public int getId() {
        return id;
    }
}
