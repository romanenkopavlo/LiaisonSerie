package fr.btsciel;

import jssc.SerialPortEvent;
import jssc.SerialPortException;

import java.nio.charset.StandardCharsets;

public class Application extends LiaisonSerie {
    public Application () {
    }
    public void serialEvent(SerialPortEvent spe) {
        super.serialEvent(spe);
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        byte [] laTrame;
        int longeur = 0;
        longeur = spe.getEventValue();
        laTrame = lireTrame(longeur);

        for (byte b: laTrame) {
            sb1.append((b & 0xff) + " ");
        }
        for (byte b: laTrame) {
            sb2.append(String.format("%02X", b));
        }
        System.out.printf("""
                --- Reception ---
                byte -> %s
                byte hexa -> %s
                ASCII -> %s
                %n""", sb1, sb2, new String(laTrame, StandardCharsets.US_ASCII));
    }
    public void initialisation(String portDeTravail) throws SerialPortException {
        super.initCom(portDeTravail);
        super.configurerParametres(9600, 8, 0, 2);
    }
    public void deconnexionCapteur() {
        super.fermerPort();
    }
    public void ecrire(byte [] b) {
        super.ecrire(b);
    }
}