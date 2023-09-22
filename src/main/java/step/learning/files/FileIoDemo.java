package step.learning.files;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

/**
 * Working with file for save data demonstration
 */
public class FileIoDemo {
    public void run() {
        // HW 21_09_23
        String filename = "lines.txt";
        try(InputStream stream = new FileInputStream(filename);
            Scanner scanner = new Scanner(stream) ) {
            int maxLineLength = 0;
            String maxLineLengthContent = "";
            int lineNumber = 0;
            int maxLineNumber = 0;
            while (scanner.hasNext()) {
                lineNumber++;
                String line = scanner.nextLine();
                int length = line.length();
                if (length > maxLineLength) {
                    maxLineLength = length;
                    maxLineLengthContent = line;
                    maxLineNumber = lineNumber;
                }
            }
            System.out.printf("Line Number: %s%nLine Length: %s%nLine Content: %s", maxLineNumber, maxLineLength, maxLineLengthContent);
        }
        catch(IOException ex){
            System.err.println(ex.getMessage());
        }
//        Random rnd = new Random();
//        int rowsCount = rnd.nextInt(81) + 20;
//        String filename = "lines.txt";
//        StringBuilder sb = new StringBuilder();
//        try(FileWriter writer = new FileWriter(filename, true)){
//            for (int i = 0; i < rowsCount; i++) {
//                int charsCount = rnd.nextInt(91) + 10;
//                for (int j = 0; j < charsCount; j++) {
//                    sb.append((char)(rnd.nextInt(108) + 20));
//                }
//                sb.append("\r\n");
//            }
//            writer.append(sb);
//        }
//        catch(IOException ex){
//            System.err.println(ex.getMessage());
//        }
//        String filename = "test.txt";
//        try (OutputStream writer = new FileOutputStream(filename, false)) {
//            writer.write("Hello, world!".getBytes());
//        } catch (IOException ex) {
//            System.err.println(ex.getMessage());
//        }
//
//        try (FileWriter writer = new FileWriter(filename, true)) {
//            writer.write("\r\nNew Line");
//        } catch (IOException ex) {
//            System.err.println(ex.getMessage());
//        }
//
//        StringBuilder sb = new StringBuilder();
//        try (InputStream reader = new FileInputStream(filename)) {
//            int c;
//            while ((c = reader.read()) != -1) {
//                sb.append((char) c);
//            }
//            System.out.println(sb.toString());
//        } catch (IOException ex) {
//            System.err.println(ex.getMessage());
//        }
//        System.out.println("--------------------------------");
//        ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream();
//        byte[] buf = new byte[1024];
//        try (BufferedInputStream reader = new BufferedInputStream(new FileInputStream(filename))) {
//            int cnt;
//            while ((cnt = reader.read(buf)) > 0) {
//                byteBuilder.write(buf, 0, cnt);
//            }
//            String content = new String(
//                    byteBuilder.toByteArray(),
//                    StandardCharsets.UTF_8
//            );
//            System.out.println(content);
//        } catch (IOException ex) {
//            System.err.println(ex.getMessage());
//        }
//
//        System.out.println("--------------------------------");
//        try(InputStream stream = new FileInputStream(filename);
//            Scanner scanner = new Scanner(stream) ) {
//            while (scanner.hasNext()) {
//                System.out.println(scanner.nextLine());
//            }
//        }
//        catch(IOException ex){
//            System.err.println(ex.getMessage());
//        }
    }
}
