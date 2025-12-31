package Spotify.Managers;

import Spotify.Device.IAudioDevice;
import Spotify.Factory.DeviceFactory;
import Spotify.Enums.DeviceType;
public class DeviceManager {
    private IAudioDevice audioDevice;
    private DeviceFactory deviceFactory;
    private static DeviceManager currDeviceManager;

    private DeviceManager() {
        audioDevice= null;
        deviceFactory = new DeviceFactory();
    }
    public static synchronized DeviceManager getInstance() {
        if (currDeviceManager== null) {
            currDeviceManager = new DeviceManager();
        }
        return currDeviceManager;
    }
    public void connectDevice( DeviceType deviceType){
        this.audioDevice = deviceFactory.ConnectDevice(deviceType);
        switch (deviceType) {
            case BLUETOOTH:
                System.out.println("Bluetooth device connected ");
                break;
            case WIRED:
                System.out.println("Wired device connected ");
                break;
            case HEADPHONES:
                System.out.println("Headphones connected ");
                break;
        }
    }
    public IAudioDevice getAudioDevice(){
        if (audioDevice == null) {
            throw new RuntimeException("No output device is connected.");
        }
        return audioDevice;
    }
    public boolean hasOutputDevice() {
        return audioDevice != null;
    }
}