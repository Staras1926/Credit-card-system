package asf2;

import java.io.Serializable;

public class Wallet implements Serializable {

    private String cardNumber;
    private String eidos;
    private String CVV;
    private String date;
    private String katoxos;

    public Wallet(String eidos, String cardNumber, String date, String CVV, String katoxos) {
        this.cardNumber = cardNumber;
        this.eidos = eidos;
        this.CVV = CVV;
        this.date = date;
        this.katoxos = katoxos;
    }

    protected String getCardNumber() {
        return cardNumber;
    }

    protected String getEidos() {
        return eidos;
    }

    protected String getCVV() {
        return CVV;
    }

    protected String getDate() {
        return date;
    }

    protected String getKatoxos() {
        return katoxos;
    }

    @Override
    public String toString() {
        return "Wallet{" + "cardNumber=" + cardNumber + ", eidos=" + eidos + ", CVV=" + CVV + ", date=" + date + ", katoxos=" + katoxos + '}';
    }

}
