package Spotify.Managers;

import java.util.HashMap;
import Spotify.Models.Playlist;

public class PlayListManager {
    private HashMap<String, Playlist> playlists;
    private static PlayListManager instance = null;
    private PlayListManager() {
        playlists = new HashMap<>();
    }
    public static PlayListManager getInstance() {
        if (instance == null) {
            instance = new PlayListManager();
        }
        return instance;
    }
    public void createPlaylist(String name) {
        if (playlists.containsKey(name)) {
            System.out.println("Playlist " + name + " already exists." );
            return;
        }
        Playlist playlist = new Playlist(name);
        playlists.put(name, playlist);
        System.out.println("Playlist " + name + " created." );
    }
    public Playlist getPlaylist(String name) {
        return playlists.get(name);
    }
    public void addSongToPlaylist(String playlistName, Spotify.Models.Songs song) {
        Playlist playlist = playlists.get(playlistName);
        if (playlist != null) {
            playlist.addSong(song);
            System.out.println("Song " + song.getTitle() + " added to playlist " + playlistName );
        } else {
            System.out.println("Playlist " + playlistName + " does not exist." );
        }
    }
    public void removeSongFromPlaylist(String playlistName, int index) {
        Playlist playlist = playlists.get(playlistName);
        if (playlist != null) {
            playlist.removeSong(index);
            System.out.println("Song at index " + index + " removed from playlist " + playlistName );
        } else {
            System.out.println("Playlist " + playlistName + " does not exist." );
        }
    }
}
