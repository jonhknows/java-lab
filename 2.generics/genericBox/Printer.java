import java.util.List;

// Wildcards (? extends e ? super)
public class Printer {
    // Leitura segura com ? extends
    public static void printNumbers(List<? extends Number> list) {
        for (Number n : list) {
            System.out.println("Número: " + n);
        }
    }

    // Escrita segura com ? super
    public static void addIntegers(List<? super Integer> list) {
        list.add(100);
        list.add(200);
    }
}