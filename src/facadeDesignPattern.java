
class Power {
    public void PowerOn(){
        System.out.println("Power On");
    }
    public void PowerOff(){
        System.out.println("Power Off");
    }
}

class Cooling {
    public void CoolingOn(){
        System.out.println("Cooling On");
    }
    public void CoolingOff(){
        System.out.println("Cooling Off");
    }
}
class CPU {
    public void CPUOn(){
        System.out.println("CPU On");
    }
    public void CPUOff(){
        System.out.println("CPU Off");
    }
}   
class Memory {
    public void MemoryOn(){
        System.out.println("Memory On");
    }
    public void MemoryOff(){
        System.out.println("Memory Off");
    }
}
class HardDisk {
    public void HardDiskOn(){
        System.out.println("HardDisk On");
    }
    public void HardDiskOff(){
        System.out.println("HardDisk Off");
    }
}
class Bios {
    CPU cpu = new CPU();
    Memory memory = new Memory();
    public void BiosOn(){
        cpu.CPUOn();
        memory.MemoryOn();
    }
    public void BiosOff(){
        cpu.CPUOff();
        memory.MemoryOff();
    }
}
class OperatingSystem{
    public void OSOn(){
        System.out.println("Operating System On");
    }
    public void OSOff(){
        System.out.println("Operating System Off");
    }
}

class ComputerFacade {
    private Power power;
    private Cooling cooling;
    private Bios bios;
    private HardDisk hardDisk;
    private OperatingSystem os;

    public ComputerFacade() {
        this.power = new Power();
        this.cooling = new Cooling();
        this.bios = new Bios();
        this.hardDisk = new HardDisk();
        this.os = new OperatingSystem();
    }

    public void startComputer() {
        power.PowerOn();
        cooling.CoolingOn();
        bios.BiosOn();
        hardDisk.HardDiskOn();
        os.OSOn();
    }

    public void shutdownComputer() {
        os.OSOff();
        bios.BiosOff();
        hardDisk.HardDiskOff();
        cooling.CoolingOff();
        power.PowerOff();
    }
}
class FacadeDesignPattern {
    public static void main(String[] args) {
        ComputerFacade computer = new ComputerFacade();
        computer.startComputer();
        System.out.println("-----");
        computer.shutdownComputer();
    }
}