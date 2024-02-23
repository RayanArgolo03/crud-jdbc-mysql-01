package services;

import enums.DefaultMessage;

import java.util.Scanner;

public final class ReaderService {

    private ReaderService() {
    }

    private static final Scanner sc = new Scanner(System.in);

    public static <T extends Enum<T>> T readEnum(T[] enums) {

        int total = enums.length;

        System.out.printf("%s %s \n", DefaultMessage.ENTER_WITH.getValue(), "your choice:");
        PrinterService.printEnums(enums);

        int choice = readInt();

        while (!validChoice(choice, total)) {
            System.out.println(DefaultMessage.INVALID.getValue());
            PrinterService.printEnums(enums);

            System.out.printf("%s %s \n", DefaultMessage.ENTER_WITH.getValue(), "your choice:");
            choice = readInt();
        }


        return enums[choice - 1];
    }

    public static int readInt() {
        return sc.nextInt();
    }

    public static String readString() {
        return sc.next();
    }

    public static Long readLong() {
        return sc.nextLong();
    }

    private static boolean validChoice(int choice, int total) {
        return choice > 0 && choice <= total;
    }

}
