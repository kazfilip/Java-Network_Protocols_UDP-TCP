import java.io.*;
import java.net.*;
import java.util.LinkedHashMap;
import java.util.regex.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;



    private static LinkedHashMap<String, Integer> stats;
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        if (stats == null){
            initializeStats();
        }
        int currentClients;
        try{
            currentClients = stats.get("connectedClients");
        }catch (NullPointerException e){
            currentClients = 0;
        }

        stats.put("connectedClients", 1 + currentClients);
        System.out.println(stats.get("connectedClients"));
    }

    public static LinkedHashMap<String,Integer> getStats(){
        return stats;
    }
    public static void initializeStats(){
        stats = new LinkedHashMap<>();
        stats.put("solvedOperations", 0);
        stats.put("addOperations", 0);
        stats.put("minusOperations", 0);
        stats.put("multiplyOperations", 0);
        stats.put("divideOperations", 0);
        stats.put("faultyOperations", 0);
        stats.put("sum", 0);
    }
    @Override
    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        ) {
            String msg;
            while ((msg = reader.readLine()) != null) {
                int response = processMessage(msg);
                if (response == 0){
                    writer.write("ERROR");
                }else writer.write(response);
                System.out.println("Result of operation " + msg + " is: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int processMessage(String msg) {
        String regex = "^(ADD|SUB|MUL|DIV) (\\d+) (\\d+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);

        if (matcher.matches()) {
            String operation = matcher.group(1);
            int num1 = Integer.parseInt(matcher.group(2));
            int num2 = Integer.parseInt(matcher.group(3));

            try {
                switch (operation) {
                    case "ADD":
                        stats.put("addOperations", stats.get("addOperations")+1);
                        stats.put("sum", stats.get("sum")+num1+num2);
                        stats.put("solvedOperations", stats.get("solvedOperations")+1);
                        return (num1 + num2);
                    case "SUB":
                        stats.put("minusOperations", stats.get("minusOperations")+1);
                        stats.put("sum", stats.get("sum")+(num1-num2));
                        stats.put("solvedOperations", stats.get("solvedOperations")+1);
                        return (num1 - num2);
                    case "MUL":
                        stats.put("multiplyOperations", stats.get("multiplyOperations")+1);
                        stats.put("sum", stats.get("sum")+(num1*num2));
                        stats.put("solvedOperations", stats.get("solvedOperations")+1);
                        return (num1 * num2);
                    case "DIV":
                        stats.put("divideOperations", stats.get("divideOperations")+1);
                        stats.put("sum", stats.get("sum")+(num1/num2));
                        stats.put("solvedOperations", stats.get("solvedOperations")+1);
                        return (num1 / num2);
                }
                return 0;
            } catch (ArithmeticException e) {
                stats.put("faultyOperations", stats.get("faultyOperations")+1);
                return 0;
            }
        }else {
            return 0;
        }
    }
}
