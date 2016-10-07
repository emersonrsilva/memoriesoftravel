package com.cedro.memoriesoftravel.model;

/**
 * Created by emerson on 06/10/16.
 */

public class CountryModel {
    private int id;
    private String nome;
    private String image;

    public CountryModel() {
    }

    public CountryModel(int id) {
        this.id=id;
    }

    public CountryModel(int id,String nome,String image) {
        this.id = id;
        this.nome = nome;
        this.image = image;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
