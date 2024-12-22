package serverconfig;

import java.io.*;
import java.util.Vector;

public class PhpReader {
    public static String contentPhp(File f, String method, String parameters) throws IOException {
        
        System.out.println("Fichier : " + f.getName());
        System.out.println("Méthode : " + method);
        System.out.println("Paramètres : " + parameters);
        
        Vector<String> commands = new Vector<>();
        if (method.equalsIgnoreCase("POST") && !parameters.isEmpty()) {
            commands.add("php");
            commands.add("-r");
            commands.add("parse_str(file_get_contents('php://stdin'), $_POST); include('" + f.getName() + "');");
        } else if (method.equalsIgnoreCase("GET")) {
            if (parameters.isEmpty()) {
                commands.add("php");
                commands.add(f.getName());
            } else {
                commands.add("php");
                commands.add("-r");
                commands.add("parse_str('" + parameters + "', $_GET); include('" + f.getName() + "');");
            }
        }

        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.directory(f.getParentFile());
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        if (method.equalsIgnoreCase("POST") && !parameters.isEmpty()) {
            try (OutputStream os = process.getOutputStream()) {
                os.write(parameters.getBytes());
                os.flush();
            }
        }

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        return output.toString();
    }
}
