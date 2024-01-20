package fr.btsciel;

import jssc.SerialPortEvent;
import jssc.SerialPortException;

import java.text.DecimalFormat;

public class Application extends LiaisonSerie {
    public Application () {
    }
    public void serialEvent(SerialPortEvent spe) {
        DecimalFormat df = new DecimalFormat("0.##");
        super.serialEvent(spe);
        byte [] laTrame;
        int longeur = 0;
        longeur = spe.getEventValue();
        laTrame = lireTrame(longeur);


        System.out.printf("""
                --- Reception ---
                %s °С
                %s K
                %n""", df.format(decodageTrameCapteur(laTrame)), df.format(decodageTrameCapteur(laTrame) + 273.15f));
    }
    public void initialisation(String portDeTravail) throws SerialPortException {
        super.initCom(portDeTravail);
        super.configurerParametres(9600, 8, 0, 1);
    }
    public void deconnexionCapteur() {
        super.fermerPort();
    }

    public float decodageTrameCapteur(byte [] byteArray) {
        StringBuilder sb = new StringBuilder();
        boolean first_byte = false;
        for (byte b: byteArray) {
            if (!first_byte) {
                sb.append((b & 0xff)).append(".");
            } else {
                sb.append((b & 0xff));
            }
            first_byte = true;
        }
        return Float.parseFloat(String.valueOf(sb));
    }
    public void ecrire(byte [] b) {
        super.ecrire(b);
    }
}