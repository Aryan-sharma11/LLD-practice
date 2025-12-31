package Spotify.Strategy;

import Spotify.Models.Playlist;
import Spotify.Models.Songs;

public class Sequential implements PlayStrategy {
   
  private Playlist currentPlaylist;
    private int currentIndex;

    public Sequential() {
        currentPlaylist = null;
        currentIndex = -1;
    }

    @Override
    public void setPlaylist(Playlist playlist , int index) {
        currentPlaylist = playlist;
        currentIndex = -1;
    }

    @Override
    public boolean hasNext() {
        return ((currentIndex + 1) < currentPlaylist.getSize());
    }

    // Next in Loop
    @Override
    public Songs next() {
        if (currentPlaylist == null || currentPlaylist.getSize() == 0) {
            throw new RuntimeException("No playlist loaded or playlist is empty.");
        }
        currentIndex = currentIndex + 1;
        return currentPlaylist.getSongs().get(currentIndex);
    }

    @Override
    public boolean hasPrevious() {
        return (currentIndex - 1 > 0);
    }

    // previous in Loop
    @Override
    public Songs previous() {
        if (currentPlaylist == null || currentPlaylist.getSize() == 0) {
            throw new RuntimeException("No playlist loaded or playlist is empty.");
        }
        currentIndex = currentIndex - 1;
        return currentPlaylist.getSongs().get(currentIndex);
    }
    @Override
    public void addToNext(Songs song) {
        // Not applicable for Sequential Strategy
    }

}
