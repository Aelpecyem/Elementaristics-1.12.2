package de.aelpecyem.elementaristics.misc.elements;

import java.util.ArrayList;

public class ElementInit {

    public static Aspect air;// = new Aspect("air");
    public static Aspect earth;// = new Aspect("earth");
    public static Aspect water;// = new Aspect("water");
    public static Aspect fire;// = new Aspect("fire");
    public static Aspect aether;// = new Aspect("aether");

    public static Aspect magan;
    //1 Entropizer, 2 Tunneler
    //Air descendants
    public static Aspect electricity; //r //definitely rework those (equally balance use of manipulation methods, define Aspects to help with research (water influenced by earth is ice, because earth also represents the solid matter
    public static Aspect vacuum; //r- entropizing
    //Earth descendants
    public static Aspect crystal; //r- purifying
    public static Aspect body; //r- influencing
    //Water decendants
    public static Aspect life; //r- reacting
    public static Aspect ice; //r- tunneling
    //Fire descendants
    public static Aspect light; //r- influencing
    public static Aspect mind; //r- tunneling
    //Aether descendants
    public static Aspect order; //r- purifying
    public static Aspect chaos; //r- entropizing
    //advanced
    public static Aspect soul; //r- tunneling
    private static ArrayList<Aspect> elements = new ArrayList<>();

    public static ArrayList<Aspect> getElements() {
        return elements;
    }

    public static void init() {
        air = new Aspect("air", 1026524); //0
        earth = new Aspect("earth",23296); //1
        water = new Aspect("water",13275);//2
        fire = new Aspect("fire",15728640);//3
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
        return null;
    }
}
