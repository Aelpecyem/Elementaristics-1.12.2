package de.aelpecyem.elementaristics.misc.elements;

import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.init.Items;

import java.util.ArrayList;

public class Aspects {

    public static Aspect air;
    public static Aspect earth;
    public static Aspect water;
    public static Aspect fire;
    public static Aspect aether;

    public static Aspect mana;
    public static Aspect magan;


    public static Aspect electricity;
    public static Aspect vacuum;
    //Earth descendants
    public static Aspect crystal;
    public static Aspect body;
    //Water decendants
    public static Aspect life;
    public static Aspect ice;
    //Fire descendants
    public static Aspect light;
    public static Aspect mind;
    //Aether descendants
    public static Aspect order;
    public static Aspect chaos;
    //advanced
    public static Aspect soul;

    private static ArrayList<Aspect> elements = new ArrayList<>();

    public static ArrayList<Aspect> getElements() {
        return elements;
    }

    public static void init() {
        air = new Aspect("air", 1026524); //0
        earth = new Aspect("earth",23296); //1
        water = new Aspect("water",13275);//2
        fire = new Aspect("fire", 15159040);//3
        aether = new Aspect("aether", 9961727);//4

        magan = new Aspect("magan", 14117632);//5

        electricity = new Aspect("electricity", 39935);//6
        vacuum = new Aspect("vacuum", 5263440);//7
        crystal = new Aspect("crystal", 39388);//8
        body = new Aspect("body", 11680055);//9

        life = new Aspect("life", 15546679);//10
        ice = new Aspect("ice", 10610175);//11

        light = new Aspect("light", 16777157);//12
        mind = new Aspect("mind",52677);//13

        order = new Aspect("order", 16777215);//14
        chaos = new Aspect("chaos", 2431017);//15

        soul = new Aspect("soul", 7831949); //16
        mana = new Aspect("mana", 13972108); //17

    }


    public static Aspect getElementByName(String name) {
        for (Aspect a : elements) {
            if (a.getName() == name) {
                return a;
            }
        }
        return null;
    }

    public static Aspect getElementById(int id) {
        for (Aspect a : elements) {
            if (a.getId() == id) {
                return a;
            }
        }
        return magan;
    }
}
