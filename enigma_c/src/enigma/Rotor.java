package enigma;
import java.util.*;

public class Rotor {
    private static final String ALFABETO = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Map<String, String> WIRINGS = new HashMap<>();
    private static final Map<String, Character> NOTCHES = new HashMap<>();
    
    static {
        WIRINGS.put("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ");
        WIRINGS.put("II", "AJDKSIRUXBLHWTMCQGZNPYFVOE");
        WIRINGS.put("III", "BDFHJLCPRTXVZNYEIWGAKMUSQO");
        WIRINGS.put("IV", "ESOVPZJAYQUIRHXLNFTGKDCMWB");
        WIRINGS.put("V", "VZBRGITYUPSDNHLXAWMJQOFECK");
        
        NOTCHES.put("I", 'Q');
        NOTCHES.put("II", 'E');
        NOTCHES.put("III", 'V');
        NOTCHES.put("IV", 'J');
        NOTCHES.put("V", 'Z');
    }
    
    private String wiring;
    private int posicao;
    private int ringSetting;
    private char notch;
    
    public Rotor(String tipo, char posicaoInicial, char ring) {
        this.wiring = WIRINGS.get(tipo);
        this.posicao = ALFABETO.indexOf(posicaoInicial);
        this.ringSetting = ALFABETO.indexOf(ring);
        this.notch = NOTCHES.get(tipo);
    }
    
    public char forward(char c) {
        int shift = posicao - ringSetting;
        int entrada = (ALFABETO.indexOf(c) + shift + 26) % 26;
        char saida = wiring.charAt(entrada);
        int saidaIdx = (ALFABETO.indexOf(saida) - shift + 26) % 26;
        return ALFABETO.charAt(saidaIdx);
    }
    
    public char backward(char c) {
        int shift = posicao - ringSetting;
        int entrada = (ALFABETO.indexOf(c) + shift + 26) % 26;
        char saidaChar = ALFABETO.charAt(entrada);
        int saida = wiring.indexOf(saidaChar);
        int saidaIdx = (saida - shift + 26) % 26;
        return ALFABETO.charAt(saidaIdx);
    }
    
    public void rotar() {
        posicao = (posicao + 1) % 26;
    }
    
    public boolean estaNoEntalhe() {
        return ALFABETO.charAt(posicao) == notch;
    }
}