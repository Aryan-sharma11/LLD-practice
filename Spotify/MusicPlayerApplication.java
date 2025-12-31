package Spotify;

import java.util.ArrayList;
import Spotify.Models.Songs;
import Spotify.Managers.PlayListManager;
import Spotify.Enums.*;



public class MusicPlayerApplication {
    private static MusicPlayerApplication instance = null;

    ArrayList<Songs> songLibrary;
    private MusicPlayerApplication() {
        songLibrary = new ArrayList<>();
    }

    public static synchronized MusicPlayerApplication getInstance() {
        if (instance == null) {
            instance = new MusicPlayerApplication();
        }
        return instance;
    }

    public void createSongInLibrary(String title, String artist, String path) {
        Songs newSong = new Songs(title, artist, path);
        songLibrary.add(newSong);
    }
    public Songs findSongByTitle(String title) {
        for (Songs s : songLibrary) {
            if (s.getTitle().equals(title)) {
                return s;
            }
        }
        return null;
    }

    public void createPlaylist(String name){
        PlayListManager.getInstance().createPlaylist(name);

    }
    public void addSongToPlaylist(String playlistName, String songTitle) {
        Songs song = findSongByTitle(songTitle);
        if (song == null) {
            throw new RuntimeException("Song \"" + songTitle + "\" not found in library.");
        }
        PlayListManager.getInstance().addSongToPlaylist(playlistName, song);
    }
    public void connectDevice(DeviceType dType){
        MusicPlayerFacade.getInstance().connectDevice(dType);
    }
    public void selectPlayStrategy(StrategyType stype){
        MusicPlayerFacade.getInstance().setStrategy(stype);
    }
    public void loadPlaylist(String playlistName){
        MusicPlayerFacade.getInstance().loadPlaylist(playlistName);
    }
    public void playSingleSong(String songTitle) {
        Songs song = findSongByTitle(songTitle);
        if (song == null) {
            throw new RuntimeException("Song \"" + songTitle + "\" not found.");
        }
        MusicPlayerFacade.getInstance().play(song);
        
    }
    public void pauseCurrentSong(String songTitle) {
        Songs song = findSongByTitle(songTitle);
        if (song == null) {
            throw new RuntimeException("Song \"" + songTitle + "\" not found.");
        }
        MusicPlayerFacade.getInstance().pause(song);
    }   
    public void playAllTracksInPlaylist() {
        MusicPlayerFacade.getInstance().playAllTracks();
    }

    public void playPreviousTrackInPlaylist() {
        MusicPlayerFacade.getInstance().playPreviousTrack();
    }

    public void queueSongNext(String songTitle) {
        Songs song = findSongByTitle(songTitle);
        if (song == null) {
            throw new RuntimeException("Song \"" + songTitle + "\" not found.");
        }
        MusicPlayerFacade.getInstance().enqueueNext(song);
    }

}
