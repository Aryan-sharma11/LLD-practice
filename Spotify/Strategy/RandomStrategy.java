package Spotify.Strategy;

import java.util.*;
import java.util.List;
import java.util.Stack;

import Spotify.Models.Playlist;
import Spotify.Models.Songs;   


public class RandomStrategy implements PlayStrategy {
    private Playlist currentPlaylist;
    private List<Songs> remainingSongs;
    private Stack<Songs> history;
    // private RandomStrategy randomStrategy;

    public RandomStrategy() {
        currentPlaylist = null;
        // randomStrategy = new RandomStrategy();
    }

    @Override
    public void setPlaylist(Playlist playlist , int index) {
        currentPlaylist = playlist;
        if (currentPlaylist == null || currentPlaylist.getSize() == 0) return;

        remainingSongs = new ArrayList<>(currentPlaylist.getSongs());
        history = new Stack<>();
    }

    @Override
    public boolean hasNext() {
        return currentPlaylist != null && !remainingSongs.isEmpty();
    }

    // Next in Loop
    @Override
    public Songs next() {
        if (currentPlaylist == null || currentPlaylist.getSize() == 0) {
            throw new RuntimeException("No playlist loaded or playlist is empty.");
        }
        if (remainingSongs.isEmpty()) {
            throw new RuntimeException("No songs left to play");
        }

        int idx = (int)Math.random()*remainingSongs.size();
        Songs selectedSong = remainingSongs.get(idx);

        // Remove the selectedSong from the list. (Swap and pop to remove in O(1))
        int lastIndex = remainingSongs.size() - 1;
        remainingSongs.set(idx, remainingSongs.get(lastIndex));
        remainingSongs.remove(lastIndex);

        history.push(selectedSong);
        return selectedSong;
    }

    @Override
    public boolean hasPrevious() {
        return history.size() > 0;
    }

    @Override
    public Songs previous() {
        if (history.isEmpty()) {
            throw new RuntimeException("No previous song available.");
        }

        Songs song = history.pop();
        return song;
    }
    @Override
    public void addToNext(Songs song) {
        return; 
    }

}
