package com.example.user.xolostyak20;

/**
 * Created by User on 25.03.2018.
 */

public class Recept {
    String name,discription;
    String[] ingridients;
    int id;
    public Recept(String name, String discription, String[] ingridients, int id){
        this.name = name;
        this.discription = discription;
        this.ingridients = ingridients;
        this.id = id;
    }
    public Recept(String name,String discription,String[] ingridients){
        this.name = name;
        this.discription = discription;
        this.ingridients = ingridients;
    }
    public Recept(String name,String discription){
        this.name = name;
        this.discription = discription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getIngridients() {

        return ingridients;
    }

    public void setIngridients(String[] ingridients) {
        this.ingridients = ingridients;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
