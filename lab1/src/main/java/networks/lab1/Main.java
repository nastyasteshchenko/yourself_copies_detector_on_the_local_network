package networks.lab1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter group ip: ");
        String inputGroupIP = in.next();

        System.out.println("Enter 's' if you want to only send packets");
        System.out.println("Enter 'r' if you want to only receive packets");
        System.out.println("Enter 'sr' if you want to both send and receive packets");
        String mode = in.next();
        while (!mode.equals("r") && !mode.equals("s") && !mode.equals("sr")){
            System.out.println("Mode is not exist, try again: ");
            mode = in.next();
        }

        System.out.println("Write \"end\" to stop.");

        App app = new App(mode, in);
        app.startApp(inputGroupIP);
    }

}