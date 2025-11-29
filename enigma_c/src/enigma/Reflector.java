package enigma;
import java.util.*;

public class Reflector {
    private static final Map<String, String> WIRINGS = new HashMap<>();
    
    static {
        WIRINGS.put("B", "YRUHQSLDPXNGOKMIEBFZCWVJAT");
        WIRINGS.put("C", "FVPJIAOYEDRZXWGCTKUQSBNMHL");
    }
    
    private String wiring;
    
    public Reflector(String tipo) {
        this.wiring = WIRINGS.get(tipo);
    }
    
    public char reflect(char c) {
        int idx = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(c);
        return wiring.charAt(idx);
    }
}