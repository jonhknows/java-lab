package genericBox;

// Método genérico com restrição de tipo
public class NumberUtils {
    
    // Método genérico que aceita qualquer tipo que estenda Number
    public static <T extends Number> double sum(T a, T b) {
        return a.doubleValue() + b.doubleValue();
    }
}