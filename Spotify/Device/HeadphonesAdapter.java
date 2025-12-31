package Spotify.Device;
import Spotify.Models.Songs;
import Spotify.External.HeadphonesApi;

public class HeadphonesAdapter implements IAudioDevice{
    private HeadphonesApi headphoneApi;

    public HeadphonesAdapter(HeadphonesApi headphoneApi) {
        this.headphoneApi = headphoneApi;
    }

    public void play(Songs song){
        String data = "Playing song:" + song.getTitle() + " by " + song.getArtist() + "on Headphones";
        headphoneApi.play(data);
    }
}`