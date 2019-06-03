package br.edu.utfpr.carolineadao.myseries.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "series",
        foreignKeys = @ForeignKey(entity = Category.class,
                parentColumns = "id",
                childColumns  = "categoryId"))
public class Serie {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    private int episodes;

    private int seasons;

    @ColumnInfo(index = true)
    private int categoryId;

    private String status;

    public Serie(String name){
        this.name = name;
    }

    public int getId() {
        return id;
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

    public int getEpisodes() {
        return this.episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public int getSeasons() {
        return this.seasons;
    }

    public void setSeasons(int seasons) {
        this.seasons = seasons;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString(){
        return this.name ;
    }

}
