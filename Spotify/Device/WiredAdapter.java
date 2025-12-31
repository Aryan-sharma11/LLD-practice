
package Spotify.Device;

import Spotify.External.WiredApi;
import Spotify.Models.Songs;

public class WiredAdapter implements IAudioDevice{

    private WiredApi speakerApi;

    public WiredAdapter(WiredApi speakerApi) {
        this.speakerApi = speakerApi;
    }

    public void play(Songs song){

        String data = "Playing song:" + song.getTitle() + " by " + song.getArtist() + "on Wired Device";
        speakerApi.play(data);
    }

   
}