// Filesystem
import java.util.*;
interface FilesystemItems {
    void ls();
    void openAll();
    int getSize();
    String getName();
}

class file implements FilesystemItems {
    String name;
    int size;

    public file(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void ls() {
        System.out.println("File: " + name);
    }

    @Override
    public void openAll() {
        System.out.println("Opening file: " + name);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getName() {
        return name;
    }
}
class folder implements FilesystemItems{
    String name ;
    List<FilesystemItems> items;
    public folder(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }
    public void addItem(FilesystemItems item) {
        items.add(item);
    }
    @Override
    public void ls() {
        System.out.println("Folder: " + name);
        for (FilesystemItems item : items) {
            System.out.println("item:"+ item.getName());
        }
    }
    @Override
    public void openAll() {
        System.out.println("Opening folder: " + name);
        for (FilesystemItems item : items) {
            item.openAll(); 
        }
    }
    @Override
    public int getSize() {
        int totalSize = 0;
        for (FilesystemItems item : items) {
            // Assuming getSize prints size, we would need to modify to return size for accurate calculation
            // Here we just simulate size calculation
            totalSize += item.getSize(); // Placeholder for actual size
        }
        return totalSize;
    }
    @Override
    public String getName() {
        return name;
    }

}

public class CompositeDesignPattern {
    public static void main(String[] args) {
        folder root = new folder("root");
        file file1 = new file("file1.txt", 100);
        file file2 = new file("file2.txt", 200);
        folder subFolder1 = new folder("subFolder1");
        file file3 = new file("file3.txt", 300);
        subFolder1.addItem(file3);
        root.addItem(file1);
        root.addItem(file2);
        root.addItem(subFolder1);

        root.ls();
        root.openAll();
        System.out.println("Total size of root folder: " + root.getSize() + " bytes");
    }
}
