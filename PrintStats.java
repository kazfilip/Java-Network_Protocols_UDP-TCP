import java.util.LinkedHashMap;

public class PrintStats implements Runnable {

    private LinkedHashMap<String,Integer> stats;

    public PrintStats(LinkedHashMap<String, Integer> stats) {
        this.stats = stats;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(10000);
                System.out.println(stats);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
