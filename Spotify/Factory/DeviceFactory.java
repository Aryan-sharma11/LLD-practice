package Spotify.Factory;

import Spotify.Device.BluetoothAdapter;
import Spotify.Device.HeadphonesAdapter;
import Spotify.Device.IAudioDevice;
import Spotify.Device.WiredAdapter;
import Spotify.Enums.DeviceType;
import Spotify.External.BluetoothApi;
import Spotify.External.HeadphonesApi;
import Spotify.External.WiredApi;

public class DeviceFactory {
    public IAudioDevice ConnectDevice(DeviceType dt){
        switch(dt){
            case WIRED:
                WiredApi wApi = new WiredApi();
                return new WiredAdapter(wApi);
            case HEADPHONES:
                HeadphonesApi hApi = new HeadphonesApi();
                return new HeadphonesAdapter(hApi);
                
            case BLUETOOTH:
                BluetoothApi bApi = new BluetoothApi();
                return new BluetoothAdapter(bApi);
            default:
                return null;
        }
    }
}
