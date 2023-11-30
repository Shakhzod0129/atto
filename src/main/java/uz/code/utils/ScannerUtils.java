package uz.code.utils;

import java.util.Scanner;

public class ScannerUtils {
    Scanner scanner=new Scanner(System.in);

    public String nextLine(String s) {
        System.out.print(s);
        String str = scanner.nextLine();
        return str;
    }
}
