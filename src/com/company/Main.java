package com.company;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        List<File> saveFiles = Arrays.asList(
                new File("D://Games/Game/savegames", "save1.dat"),
                new File("D://Games/Game/savegames", "save2.dat"),
                new File("D://Games/Game/savegames", "save3.dat"));
        List<GameProgress> gameStates = Arrays.asList(
                new GameProgress(100, 2, 5, 20.4),
                new GameProgress(50, 4, 7, 70.2),
                new GameProgress(74, 6, 9, 96.56));
        for (int i = 0; i < saveFiles.size(); i++) {
            saveGame(saveFiles.get(i), gameStates.get(i));
        }
        zipFiles("D://Games/Game/savegames/saves.zip", saveFiles);
        for (File file : saveFiles) {
            file.delete();
        }
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
