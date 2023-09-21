package step.learning.basics;

import step.learning.hws.MyDictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BasicsDemo {
    public void run() {
        // hW 20_09_23
        MyDictionary dict = new MyDictionary();
        do {
            System.out.print("Enter a word: ");
            Scanner kbScanner = new Scanner(System.in);
            String word = kbScanner.next();
            String translatedWord = dict.GetTranslate(word);
            System.out.println(translatedWord == null ?  "There is no such word(\n" : translatedWord);
        }while(true);

//        System.out.println("Java basics");
//        byte b = 10;
//        short s = 1000;
//        int i = 1000000;
//        long l = 100000000L;
//
//        int[][] arr = {
//                {1, 2, 3},
//                {4, 5, 6},
//                {7, 8, 9}
//        };
//        for (int[] row :
//                arr) {
//            for (int n :
//                    row) {
//                System.out.printf("%d ", n);
//            }
//            System.out.println();
//        }
//
//        Map<String, String> headers = new HashMap<>();
    }
}
