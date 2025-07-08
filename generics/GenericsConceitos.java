import java.util.ArrayList;
import java.util.List;

public class GenericsConceitos {

    public static void main(String[] args) {

        // Covariância
        printNumbers(List.of(1L, 2, 3, 4.0, 5)); // Printa todos os números da lista mesmo que sejam de tipos diferentes

        // Contravariância
        // addIntegers(List.of(1, 2, 3)); // Lança UnsupportedOperationException, pois List.of é uma lista imutável
        addIntegers(new ArrayList<>(List.of(1, 2, 3)));  // Adiciona um Integer à lista, que pode ser de tipo super Integer

        // Wildcard não limitado
        printList(List.of("Hello", "World", 42, 3.14)); // Printa todos os elementos da lista, independente do tipo

        // Type erasure
    }

    /*
     * Usado quando você só vai LER dados da estrutura genérica.
     * Não permite adicionar elementos, exceto null.
     * Permite adicionar Integer ou subclasses, mas leitura é limitada a Object.
     */
    public static void printNumbers(List<? extends Number> list) {
        for (Number n : list) {
            System.out.println(n);
        }
    }
    /*
     * Usado quando você vai ESCREVER dados na estrutura genérica.
     * Aceita List<Integer>, List<Number>, List<Object>.
     */
    public static void addIntegers(List<? super Integer> list) {
        list.add(42); // OK
        list.forEach(n -> {
            System.err.println(n);
        });
    }

    /*
     * Aceita qualquer tipo, mas você não pode adicionar nada (exceto null).
     */
    public static void printList(List<?> list) {
        for (Object obj : list) {
            System.out.println(obj);
        }
    }


    
}

/*
 * O type erasure é o processo pelo qual o compilador Java remove as informações de tipo genérico em tempo de compilação. 
 * Isso significa que os tipos genéricos não existem em tempo de execução.
 */
class Caixa<T> {
    private T valor;
    public void set(T valor) { this.valor = valor; }
    public T get() { return valor; }
}

/*
 * Após o type erasure, o código se torna:
 * class Caixa {
        private Object valor;
        public void set(Object valor) { this.valor = valor; }
        public Object get() { return valor; }
    }
 * Isso significa que a classe Caixa não tem mais conhecimento do tipo T em tempo de execução.
 * O tipo T é substituído por Object (ou pelo upper bound, se houver).
 * Isso explica por que não é possível usar instanceof com tipos genéricos:
 * if (obj instanceof List<String>) // Erro de compilação
 */

/*
 * Não há sobrecarga de métodos com tipos genéricos diferentes:
 * 
 * void metodo(List<String> lista) {}
 * void metodo(List<Integer> lista) {} // Erro: mesmo método após erasure
 * 
 */



