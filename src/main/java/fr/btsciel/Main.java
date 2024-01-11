package fr.btsciel;

import jssc.SerialPortException;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Application app = new Application();
        app.listerLesPorts().forEach(com->System.out.println(com));
        try {
            while (true) {
                app.initialisation("COM10");
                System.out.println("Entrez votre message: ");
                app.ecrire(In.readString().getBytes(StandardCharsets.US_ASCII));
                app.ecrire(" ".getBytes(StandardCharsets.US_ASCII));
                Thread.sleep(2000);
                if (app.detecteSiReception() > 0) {
                    System.out.println(new String(app.lireTrame(app.detecteSiReception())));
                }
                app.deconnexionCapteur();
            }
        } catch (SerialPortException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}