package br.edu.utfpr.carolineadao.myseries;

public class Serie {
    private String name;
    private int episodes;
    private int seasons;
    private Category category;

    public Serie(String name, int episodes, int seasons, Category category) {
        this.name = name;
        this.episodes = episodes;
        this.seasons = seasons;
        this.category = category;
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
}
