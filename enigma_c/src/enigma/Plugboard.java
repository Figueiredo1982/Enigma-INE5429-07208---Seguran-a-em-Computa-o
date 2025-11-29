package enigma;
import java.util.*;

public class Plugboard {
    private Map<Character, Character> conexoes;
    
    public Plugboard(String pares) {
        conexoes = new HashMap<>();
        for (int i = 0; i < pares.length() - 1; i += 2) {
            char a = pares.charAt(i);
            char b = pares.charAt(i + 1);
            conexoes.put(a, b);
            conexoes.put(b, a);
        }
    }
    
    public char forward(char c) {
        return conexoes.getOrDefault(c, c);
    }
}