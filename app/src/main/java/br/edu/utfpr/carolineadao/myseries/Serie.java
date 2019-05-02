package br.edu.utfpr.carolineadao.myseries;

import android.os.Parcel;
import android.os.Parcelable;

public class Serie implements Parcelable {
    private int id;
    private String name;
    private int episodes;
    private int seasons;
    private Category category;
    private Status status;

    public Serie(String name, int episodes, int seasons, Category category) {
        this.name = name;
        this.episodes = episodes;
        this.seasons = seasons;
        this.category = category;
    }

    public Serie(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public Serie(String name, int seasons, Category category) {
        this.name = name;
        this.seasons = seasons;
        this.category = category;
    }

    public Serie(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.episodes = in.readInt();
        this.seasons = in.readInt();
       // this.category = in.readValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public int getSeasons() {
        return seasons;
    }

    public void setSeasons(int seasons) {
        this.seasons = seasons;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.episodes);
        dest.writeInt(this.seasons);
        dest.writeValue(this.category);
    }
}
