package br.edu.utfpr.carolineadao.myseries.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "categories",
        indices = @Index(value = {"name"}, unique = true))
public class Category {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    public Category(String name){
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
