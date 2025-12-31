package Spotify.Device;

import Spotify.Models.Songs;
import Spotify.External.BluetoothApi;

public class BluetoothAdapter implements IAudioDevice{
    private BluetoothApi bluetoothApi;

    public BluetoothAdapter(BluetoothApi bluetoothApi) {
        this.bluetoothApi = bluetoothApi;
    }
    public void play(Songs song){
        String data = "Playing song:" + song.getTitle() + " by " + song.getArtist() + " on Bluetooth Device";
        bluetoothApi.play(data);
    } 
}