package Spotify.Models;

import java.util.ArrayList;

public class Playlist {
    String name;
    
    ArrayList<Songs> songs; 

    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();     
    }
    public void addSong(Songs song){
        songs.add(song);
    }
    public ArrayList<Songs> getSongs() {
        return songs;
    }
    public void removeSong( int index ){
        songs.remove(index);
    }
    public String getPlaylistName(){
        return name;
    }
    public int getSize(){
        return songs.size();
    }

}   
