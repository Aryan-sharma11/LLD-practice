package Spotify.Models;

public class Songs {
    private String title;
    private String artist;
    private String path;


    public Songs(String title, String artist, String path) {
        this.title = title;
        this.artist = artist;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }
}
