package Spotify;

import Spotify.Models.Playlist;

import Spotify.Core.*;
import Spotify.Device.IAudioDevice;
import Spotify.Enums.StrategyType;
import Spotify.Managers.*;
import Spotify.Models.Songs;
import Spotify.Strategy.PlayStrategy;
import Spotify.Enums.DeviceType;

public class MusicPlayerFacade {
    private AudioEngine ae;
    private Playlist playlist;
    private StrategyManager playlistStrategyManager;
    private PlayStrategy strategy;
    private DeviceManager deviceManager;
    private static MusicPlayerFacade instance;
    private MusicPlayerFacade() {
        ae = new AudioEngine();
    }
    public static synchronized MusicPlayerFacade getInstance() {
        if (instance == null) {
            instance = new MusicPlayerFacade();
        }
        return instance;
    }

    public void connectDevice(DeviceType dType){
        DeviceManager.getInstance().connectDevice(dType);;
    }
    public void setStrategy(StrategyType type){
        playlistStrategyManager = StrategyManager.getInstance();
        strategy = playlistStrategyManager.getStrategy(type);
    }

    public void loadPlaylist(String playlistName){
        playlist = PlayListManager.getInstance().getPlaylist(playlistName);
        strategy.setPlaylist(playlist, 0);
        System.out.println("Playlist " + playlistName + " loaded.");
    }
    public void loadStrategy(StrategyType stype){
        strategy = StrategyManager.getInstance().getStrategy(stype);
    }
    public void play(Songs song) {
        if (!DeviceManager.getInstance().hasOutputDevice()) {
            throw new RuntimeException("No audio device connected.");
        }
        IAudioDevice device = DeviceManager.getInstance().getAudioDevice();
        ae.playSong(device, song);
        
    }
    public void pause(Songs song) {
        if (!ae.getCurrentSongTitle().equals(song.getTitle())) {
            throw new RuntimeException("Cannot pause \"" + song.getTitle() + "\"; not currently playing.");
        }
        ae.pauseSong();
    }
     public void playAllTracks() {
        if (playlist == null) {
            throw new RuntimeException("No playlist loaded.");
        }
        while (strategy.hasNext()) {
            Songs nextSong = strategy.next();
            IAudioDevice device = DeviceManager.getInstance().getAudioDevice();
            ae.playSong(device, nextSong);
        }
        System.out.println("Completed playlist: " + playlist.getPlaylistName());
    }
    public void playNextTrack() {
        if (playlist == null) {
            throw new RuntimeException("No playlist loaded.");
        }
        if (strategy.hasNext()) {
            Songs nextSong = strategy.next();
            IAudioDevice device = DeviceManager.getInstance().getAudioDevice();
            ae.playSong(device, nextSong);
        } else {
            System.out.println("Completed playlist: " + playlist.getPlaylistName());
        }
    }

    public void playPreviousTrack() {
        if (playlist == null) {
            throw new RuntimeException("No playlist loaded.");
        }
        if (strategy.hasPrevious()) {
            Songs prevSong = strategy.previous();
            IAudioDevice device = DeviceManager.getInstance().getAudioDevice();
            ae.playSong(device, prevSong);
        } else {
            System.out.println("Completed playlist: " + playlist.getPlaylistName());
        }
    }

    public void enqueueNext(Songs song) {
        strategy.addToNext(song);
    }

}
