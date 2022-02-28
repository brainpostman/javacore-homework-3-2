package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        File fileSave1 = new File("D://Games/Game/savegames", "save1.dat");
        File fileSave2 = new File("D://Games/Game/savegames", "save2.dat");
        File fileSave3 = new File("D://Games/Game/savegames", "save3.dat");
        List<File> saveFiles = new ArrayList<>();
        saveFiles.add(fileSave1);
        saveFiles.add(fileSave2);
        saveFiles.add(fileSave3);
        GameProgress save1 = new GameProgress(100, 2, 5, 20.4);
        GameProgress save2 = new GameProgress(50, 4, 7, 70.2);
        GameProgress save3 = new GameProgress(74, 6, 9, 96.56);
        saveGame(saveFiles.get(0), save1);
        saveGame(saveFiles.get(1), save2);
        saveGame(saveFiles.get(2), save3);
        zipFiles("D://Games/Game/savegames/saves.zip", saveFiles);
        fileSave1.delete();
        fileSave2.delete();
        fileSave3.delete();
    }


    public static void saveGame(File file, GameProgress gp) {
        try (FileOutputStream fos = new FileOutputStream(file.getPath());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipPath, List<File> files) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (int i = 0; i < files.size(); i++) {
                try (FileInputStream fis = new FileInputStream(files.get(i).getPath())) {
                    ZipEntry entry = new ZipEntry(files.get(i).getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
