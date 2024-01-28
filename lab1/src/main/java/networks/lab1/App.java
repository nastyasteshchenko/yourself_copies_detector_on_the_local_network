package networks.lab1;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class App {

    private final String mode;
    private final Scanner input;

    App(String mode, Scanner input){
        this.mode = mode;
        this.input = input;
    }

    void startApp(String inputGroupIP) {
        try {
            CheckingGroupIpUtils.checkValidIp(inputGroupIP);

            InetAddress groupAddress = InetAddress.getByName(inputGroupIP);

            Sender sender = Sender.create(groupAddress);
            Receiver receiver = Receiver.create(groupAddress);

            switch (mode){
                case "s" -> sender.start();
                case "r" -> receiver.start();
                case "sr" -> {
                    sender.start();
                    receiver.start();
                }
            }

            detectRequestToEndApp(sender, receiver);

        } catch (IOException | InterruptedException | IPException e) {
            input.close();
            System.err.println(e.getMessage());
        }
    }

    void detectRequestToEndApp(Sender sender, Receiver receiver) throws InterruptedException {
        while (true) {
            String str = input.next();
            if (str.equals("end")) {

                switch (mode){
                    case "s" -> {
                        sender.stopRunning();
                        sender.join();
                        sender.close();
                    }
                    case "r" -> {
                        receiver.stopRunning();
                        receiver.join();
                        receiver.close();
                    }
                    case "sr" -> {
                        sender.stopRunning();
                        receiver.stopRunning();

                        sender.join();
                        receiver.join();

                        sender.close();
                        receiver.close();
                    }
                }
                break;
            }
        }
        input.close();
    }

}
