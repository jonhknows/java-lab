package genericBox;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Box genérica
        Box<String> stringBox = new Box<>();
        stringBox.set("Olá, João!");
        System.out.println(stringBox);

        Box<Integer> intBox = new Box<>();
        intBox.set(42);
        System.out.println(intBox);

        // Método genérico
        double resultado = NumberUtils.sum(10L, 20);
        System.out.println("Soma genérica: " + resultado);

        // Wildcards
        List<Integer> numeros = new ArrayList<>(List.of(1, 2, 3));
        Printer.printNumbers(numeros); // ? extends Number

        Printer.addIntegers(numeros);  // ? super Integer
        System.out.println("Após adicionar: " + numeros);
    }
}