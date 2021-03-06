package com.hard._01_implements_comparator._03_map._02_by_value;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
//        Map<Entity, Integer> entities = new HashMap<>();			// not sortable
//        Map<Entity, Integer> entities = new LinkedHashMap<>();	// not sortable
//        Map<Entity, Integer> entities = new Hashtable<>();		// not sortable
        Map<Entity, String> entities2 = new HashMap<>();
        Map<Entity, String> entities = new TreeMap<>(new ValueComparator(entities2));  // sortable

        entities2.put(new Entity(3, "ccc"), "bbbb");
        entities2.put(new Entity(2, "aaa"), "dddd");
        entities2.put(new Entity(4, "ddd"), "aaaa");
        entities2.put(new Entity(1, "bbb"), "cccc");

        System.out.println(entities2);
    }
}

/**
 * Model
 */

class Entity {
    private long id;
    private String name;

    public Entity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

/**
 * comparators
 */

class EntityIdComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity o1, Entity o2) {
        if (o1.getId() > o2.getId())        // ascending
            return 1;
        else if (o1.getId() == o2.getId())  // not sort
            return 0;
        else if (o1.getId() < o2.getId())   // descending
            return -1;

        return 0;
    }

//    @Override
//    public int compare(Entity o1, Entity o2) {
//        Long val1 = new Long(o1.getId());
//        Long val2 = new Long(o2.getId());
//
//        return val1.compareTo(val2);  // + ascending, - descending
//    }
}

class EntityNameComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity o1, Entity o2) {
        String val1 = o1.getName();
        String val2 = o2.getName();

        return val1.compareTo(val2);       // + ascending, - descending
    }
}

class ValueComparator implements Comparator<Entity> {
    private Map<Entity, String> entities;

    public ValueComparator(Map<Entity, String> entities) {
        this.entities = entities;
    }

    @Override
    public int compare(Entity o1, Entity o2) {
        String val1 = entities.get(o1);
        String val2 = entities.get(o2);

        return val1.compareTo(val2);
    }
}
