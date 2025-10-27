import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflexaoExemplo {
    // public static void main(String[] args) throws Exception {
        
    //     // Obtendo a classe
    //     Class<?> clazz = Class.forName("java.util.ArrayList");

    //     // Criando uma instância
    //     Object arrayList = clazz.getDeclaredConstructor().newInstance();

    //     // Obtendo o método add
    //     Method addMethod = clazz.getMethod("add", Object.class);

    //     // Invocando o método add
    //     addMethod.invoke(arrayList, "Elemento");

    //     System.out.println(arrayList); // [Elemento]
    // }

    public static void main(String[] args) throws Exception {

        // Cria instância da classe Pessoa
        Class<?> clazz = Class.forName("Pessoa");
        Object pessoa = clazz.getDeclaredConstructor().newInstance();

        // Lista todos os métodos
        System.out.println("Métodos da classe:");
        for (Method metodo : clazz.getDeclaredMethods()) {
            System.out.println("- " + metodo.getName());
        }

        // Invoca método público
        Method dizerOi = clazz.getMethod("dizerOi");
        dizerOi.invoke(pessoa);

        // Invoca método privado
        Method segredo = clazz.getDeclaredMethod("segredo");
        segredo.setAccessible(true); // desbloqueia acesso
        segredo.invoke(pessoa);

        // Acessa campo privado
        Field campoNome = clazz.getDeclaredField("nome");
        campoNome.setAccessible(true);
        campoNome.set(pessoa, "Maria");

        // Invoca novamente para ver mudança
        dizerOi.invoke(pessoa);

            
    }
}



class Pessoa {
    private String nome = "João";
    private int idade = 25;

    public void dizerOi() {
        System.out.println("Oi, meu nome é " + nome);
    }

    private void segredo() {
        System.out.println("Este é um segredo!");
    }
}