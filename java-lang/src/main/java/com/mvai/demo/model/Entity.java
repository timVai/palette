package com.mvai.demo.model;

/**
 * @author fanfengshi
 */
public class Entity {

    public Entity(){}

    public Entity(String name){
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String id;
    private String name;

}
