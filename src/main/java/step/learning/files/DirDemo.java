package step.learning.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Working with files and directories as file system objects demonstration
 */
public class DirDemo {
    public void run() {
        // HW 21_09_23
        String dirPath = "./";
        File dir = new File(dirPath);
        String headerFormat = "%-12s %-20s %-9s %-6s%n";
        String fileInfoFormat = "%-12s%-19s%9d    %s%n";
        if(dir.exists()){
            if(dir.isDirectory()){
                try{
                    System.out.printf(headerFormat, "Mode", "LastWriteTime", "Length", "Name");
                    System.out.printf(headerFormat, "----", "-------------", "------", "----");
                    for (File file : dir.listFiles()){
                        System.out.printf(fileInfoFormat,
                                file.isDirectory() ? "d-----" : "-a----",
                                new SimpleDateFormat("dd.MM.yyyy  HH:mm").format(new Date(file.lastModified())),
                                file.length(),
                                file.getName());
                    }
                }
                catch(NullPointerException ignored){
                    System.err.println("Iteration Exception");
                }
            }
            else{
                System.out.printf("%s is not a directory", dirPath);
            }
        }
        else{
            System.out.printf("Object %s doesn't exist", dirPath);
        }


//        System.out.println("Directories demo");
//        String path = "./";
//        File dir = new File(path);
//        if (dir.exists()) {
//            System.out.printf("Object %s does exist @ real path '%s' and it is %s %n",
//                    path,
//                    dir.getAbsolutePath(),
//                    dir.isFile() ? "file" : "directory");
//            if(dir.isDirectory()) {
//                try {
//                    for (String name : dir.list()) {
//                        System.out.printf("%s%n", name);
//                    }
//                    System.out.println();
//                    for(File f : dir.listFiles()){
//                        System.out.printf("%s%n", f.getName());
//                    }
//                }
//                catch (NullPointerException ignored) {
//                    System.err.println("Iteration Exception");
//                }
//            }
//        } else {
//            System.out.printf("Object %s doesn't exist", path);
//        }
//        String subPath = "./upload";
//        File subDir = new File(subPath);
//        if (subDir.isDirectory()) {
//            System.out.printf("Dir '%s' already exists%n", subPath);
//        } else {
//            if (subDir.exists()) {
//                System.out.printf("Object '%s' does exist, BUT NOT A DIR %n", subPath);
//                System.out.print("Delete object? (y/n): ");
//                Scanner kbScanner = new Scanner(System.in);
//                String answer = kbScanner.next();
//                if (answer.toLowerCase().trim().equals("y")) {
//                    if (subDir.delete()) {
//                        System.out.println("Deleted successful");
//                        if (subDir.mkdir()) {
//                            System.out.printf("Dir %s created successful", subPath);
//                        } else {
//                            System.out.println("Created error");
//                        }
//                    } else {
//                        System.out.println("Deleted Error");
//                    }
//
//                }
//            } else {
//                System.out.printf("Dir '%s' doesn't exist, creating...%n", subPath);
//                if (subDir.mkdir()) {
//                    System.out.println("Done");
//                } else {
//                    System.out.println("Creation Error");
//                }
//            }
//        }
//
//        String logPath = subPath + File.separator + "actions.log";
//        File logFile = new File(logPath);
//        if (logFile.isFile()) {
//            System.out.printf("File %s already exists%n", logPath);
//        } else {
//            if (logFile.exists()) {
//                System.out.printf("Object '%s' does exist, BUT NOT A FILE %n", logPath);
//                System.out.print("Delete object? (y/n): ");
//                Scanner kbScanner = new Scanner(System.in);
//                String answer = kbScanner.next();
//                if (answer.toLowerCase().trim().equals("y")) {
//                    if (logFile.delete()) {
//                        System.out.println("Deleted successful");
//                        try {
//                            if (logFile.createNewFile()) {
//                                System.out.printf("File %s created successful", logPath);
//                            } else {
//                                System.out.println("Created error");
//                            }
//                        }
//                        catch(IOException ex){
//                            System.out.println(ex.getMessage());
//                        }
//                    } else {
//                        System.out.println("Deleted Error");
//                    }
//
//                }
//            } else {
//                try {
//                    if (logFile.createNewFile()) {
//                        System.out.println("File created");
//                    } else {
//                        System.out.println("File creation error");
//                    }
//                } catch (IOException ex) {
//                    System.out.println(ex.getMessage());
//                }
//            }
//        }

    }
}
