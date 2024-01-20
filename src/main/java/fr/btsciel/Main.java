package fr.btsciel;

import jssc.SerialPortException;

import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        Application app = new Application();
        String choice;
        app.listerLesPorts().forEach(com->System.out.println(com));
        System.out.println("GO or IEEE?");
        System.out.print("Your choice: ");
        choice = In.readString().toUpperCase();
        try {
            while (true) {
                app.initialisation("COM10");
                app.ecrire(choice.getBytes(StandardCharsets.US_ASCII));
                app.ecrire("\r".getBytes(StandardCharsets.US_ASCII));
                if (app.detecteSiReception() > 0) {
                    System.out.println(new String(app.lireTrame(app.detecteSiReception())));
                }
                Thread.sleep(5000);
                app.deconnexionCapteur();
            }
        } catch (SerialPortException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}