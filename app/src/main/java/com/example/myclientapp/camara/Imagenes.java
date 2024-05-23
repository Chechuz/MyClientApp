package com.example.myclientapp.camara;

public class Imagenes {
    String imageUri;
    int id, foreignKey;

    public Imagenes(){

    }
    public Imagenes (String imageUri){
        this.imageUri= imageUri;
    }
    public Imagenes(int id,int foreignKey, String imageUri ){
        this.id = id;
        this.foreignKey = foreignKey;
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(int foreignKey) {
        this.foreignKey = foreignKey;
    }
}
