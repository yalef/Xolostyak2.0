package com.example.user.xolostyak20;

/**
 * Created by User on 25.03.2018.
 */

public class Recept {
    String name,discription;
    String ingridients;
    String image;

    public Recept(String name, String discription, String ingridients,String image){
        this.name = name;
        this.discription = discription;
        this.ingridients = ingridients;
        this.image =image;
    }
    public Recept(String name,String ingridients,String image){
        this.name = name;
        this.ingridients = ingridients;
        this.image = image;
    }

    public String getIngridients() { return ingridients; }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIngridients(String ingridients) {
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
