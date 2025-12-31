package Spotify.Core;

import Spotify.Device.IAudioDevice;
import Spotify.Models.Songs;
public class AudioEngine {
    private Songs currSong;
    private boolean isPlaying;
    public AudioEngine() {
        currSong = null;
        isPlaying = false;
    }
    public void playSong(IAudioDevice ad , Songs song){
        if (song == null) {
            throw new RuntimeException("Cannot play a null song.");
        }
        if (isPlaying && song == currSong) {
            isPlaying = true;
            System.out.println("Resuming song: " + song.getTitle());
            ad.play(song);
            return;
        }

        currSong = song;
        isPlaying = true;
        System.out.println("Playing song: " + song.getTitle());
        ad.play(song);
        
    }
    public void pauseSong(){
        if (currSong == null) {
            throw new RuntimeException("No song is currently playing to pause.");
        }
        if (!isPlaying) {
            throw new RuntimeException("Song is already paused.");
        }
        System.out.println("Pausing song: " + currSong.getTitle());
        isPlaying = false;

    }
    public String getCurrentSongTitle() {
        if (currSong != null) {
            return currSong.getTitle();
        }
        return "";
    }
    public boolean isPaused() {
        return !isPlaying;
    }

}
