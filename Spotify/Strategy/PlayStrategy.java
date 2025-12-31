package Spotify.Strategy;

import Spotify.Models.Playlist;
import Spotify.Models.Songs;

public interface PlayStrategy {
    public void setPlaylist(Playlist playlist , int index);
    public boolean hasNext();
    public Songs next();
    public boolean hasPrevious();
    public Songs previous();
    public void addToNext(Songs song);
}
