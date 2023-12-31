package uz.code.utils;

import java.util.Scanner;

public class ScannerUtils {
    static Scanner scanner=new Scanner(System.in);

    public String nextLine(String s) {
        System.out.print(s);
        String str = scanner.nextLine();
        return str;
    }

    public static int nextInt(String s) {
        int number;
        do {
            try {
                System.out.print(s);
                number = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println();
                System.out.println("Enter only numbers");
                scanner.nextLine();
            }
        } while (true);
        return number;
    }
}
