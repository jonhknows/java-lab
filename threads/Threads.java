import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ThreadsLab {
    public static void main(String[] args) throws Exception {
//        System.out.println("Hello, World!");

        // Executa a thread com Runnable
        ThreadComRunnable threadComRunnable = new ThreadComRunnable();
        Thread thread = new Thread(threadComRunnable);
        thread.start();
        System.out.println("Thread principal continua executando...");
        // Aguarda a thread terminar
        thread.join();
        System.out.println("Thread com Runnable finalizada.");

        // Executa a soma sequencial
        SomaSequencial somaSequencial = new SomaSequencial();
        somaSequencial.soma();
        
        // Executa a soma com threads
        SomaComThreads somaComThreads = new SomaComThreads();
        somaComThreads.soma();

        // Executa a soma com ExecutorService
        SomaExecutorService somaExecutorService = new SomaExecutorService();
        somaExecutorService.soma();

        // Executa a soma com ForkJoinPool
        SomaForkJoin somaForkJoin = new SomaForkJoin();
        somaForkJoin.soma(args);

    }
}


class SomaSequencial {
    public void soma() {
        long inicio = System.currentTimeMillis();

        long soma = 0;
        for (long i = 1; i <= 2_000_000_000L; i++) {
            soma += i;
        }

        long fim = System.currentTimeMillis();
        System.out.println("Soma: " + soma);
        System.out.println("Tempo (sequencial): " + (fim - inicio) + " ms");
    }
}

/*
 * Quando sua classe já estende outra classe (Java não permite herança múltipla)
 */

class ThreadComRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Executando thread: " + i);
            try {
                Thread.sleep(1000); // pausa de 1 segundo
            } catch (InterruptedException e) {
                System.out.println("Thread interrompida");
            }
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new ThreadComRunnable());
        thread.start();

        System.out.println("Thread principal continua executando...");
    }
}

/*
 * Quando você quer customizar diretamente o comportamento da thread
 */

class SomaParcial extends Thread {
    private long inicio, fim, resultado;

    public SomaParcial(long inicio, long fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public long getResultado() {
        return resultado;
    }

    @Override
    public void run() {
        for (long i = inicio; i <= fim; i++) {
            resultado += i;
        }
    }
}

class SomaComThreads {
    public void soma() throws InterruptedException {
        long inicioTotal = System.currentTimeMillis();

        // Divide em 4 threads
        SomaParcial t1 = new SomaParcial(1, 500_000_000L);
        SomaParcial t2 = new SomaParcial(500_000_001L, 1_000_000_000L);
        SomaParcial t3 = new SomaParcial(1_000_000_001L, 1_500_000_000L);
        SomaParcial t4 = new SomaParcial(1_500_000_001L, 2_000_000_000L);

        t1.start(); 
        t2.start(); 
        t3.start(); 
        t4.start();

        t1.join(); 
        t2.join(); 
        t3.join(); 
        t4.join();

        long somaTotal = t1.getResultado() + t2.getResultado() + t3.getResultado() + t4.getResultado();

        long fimTotal = System.currentTimeMillis();
        System.out.println("Soma: " + somaTotal);
        System.out.println("Tempo (com threads): " + (fimTotal - inicioTotal) + " ms");
    }
}

/* 
 *  Essa abordagem permite gerenciar um pool de threads e obter resultados com Future.
 *  O uso de Callable e Future facilita a obtenção de resultados de tarefas assíncronas.
 *  Controle explícito sobre tarefas e threads. Ideal para tarefas independentes.
 */

class SomaExecutorService {
    public void soma() throws InterruptedException, ExecutionException {
        long inicio = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(4);

        Callable<Long> tarefa1 = () -> somaIntervalo(1, 500_000_000L);
        Callable<Long> tarefa2 = () -> somaIntervalo(500_000_001L, 1_000_000_000L);
        Callable<Long> tarefa3 = () -> somaIntervalo(1_000_000_001L, 1_500_000_000L);
        Callable<Long> tarefa4 = () -> somaIntervalo(1_500_000_001L, 2_000_000_000L);

        Future<Long> f1 = executor.submit(tarefa1);
        Future<Long> f2 = executor.submit(tarefa2);
        Future<Long> f3 = executor.submit(tarefa3);
        Future<Long> f4 = executor.submit(tarefa4);

        long somaTotal = f1.get() + f2.get() + f3.get() + f4.get();

        executor.shutdown();

        long fim = System.currentTimeMillis();
        System.out.println("Soma: " + somaTotal);
        System.out.println("Tempo (ExecutorService): " + (fim - inicio) + " ms");
    }

    private static long somaIntervalo(long inicio, long fim) {
        long soma = 0;
        for (long i = inicio; i <= fim; i++) {
            soma += i;
        }
        return soma;
    }
}

/*
 * O ForkJoinPool divide a tarefa em subtarefas menores automaticamente.
 * É ideal para tarefas recursivas e divide o trabalho de forma eficiente.
 * Melhor para tarefas recursivas e divisíveis. Usa "work stealing" para eficiência
 */
class SomaTask extends RecursiveTask<Long> {
    private static final long LIMITE = 100_000_000L;
    private long inicio, fim;

    public SomaTask(long inicio, long fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    protected Long compute() {
        if (fim - inicio <= LIMITE) {
            long soma = 0;
            for (long i = inicio; i <= fim; i++) {
                soma += i;
            }
            return soma;
        } else {
            long meio = (inicio + fim) / 2;
            SomaTask parte1 = new SomaTask(inicio, meio);
            SomaTask parte2 = new SomaTask(meio + 1, fim);
            parte1.fork();
            long soma2 = parte2.compute();
            long soma1 = parte1.join();
            return soma1 + soma2;
        }
    }
}

class SomaForkJoin {
    public void soma(String[] args) {
        long inicio = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool();
        SomaTask tarefa = new SomaTask(1, 2_000_000_000L);
        long resultado = pool.invoke(tarefa);

        long fim = System.currentTimeMillis();
        System.out.println("Soma: " + resultado);
        System.out.println("Tempo (ForkJoinPool): " + (fim - inicio) + " ms");
    }
}
