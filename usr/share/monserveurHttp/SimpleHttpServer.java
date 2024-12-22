package serverconfig;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;

public class SimpleHttpServer {
    private static int PORT;
    private static String DOC_ROOT;
    private static boolean PHP_ENABLED = false;
    private ServerSocket serverSocket;
    private boolean running;

    static {
        try {
            // Charger la configuration au démarrage
            ConfigurationLoader.loadConfig(); // Charger les configurations
            PORT = ConfigurationLoader.getPort();
            DOC_ROOT = ConfigurationLoader.getDocRoot();
            PHP_ENABLED = ConfigurationLoader.isPhpEnabled(); // Initialiser PHP_ENABLED
        } catch (Exception e) {
            System.out.println("Erreur de configuration : " + e.getMessage());
        }
    }

    public static String getDocRoot() {
        return DOC_ROOT;
    }

    public static int getPort() {
        return PORT;
    }

    public static boolean isPhpEnabled() {
        return PHP_ENABLED;
    }

    public static void setPhpEnabled(boolean enabled) {
        PHP_ENABLED = enabled;
        try {
            ConfigurationLoader.setPhpEnabled(enabled); // Mettre à jour la configuration si nécessaire
        } catch (IOException e) {
            System.out.println("Erreur lors de la mise à jour de la configuration : " + e.getMessage());
        }
    }

    public SimpleHttpServer() {
        File docRoot = new File(getDocRoot());
        if (!docRoot.exists()) {
            try {
                if (!docRoot.mkdirs()) {
                    throw new FileAlreadyExistsException("Le répertoire 'htdocs' n'a pas pu être créé.");
                }
                System.out.println("Répertoire htdocs créé.");
            } catch (IOException e) {
                System.out.println("Erreur lors de la création du répertoire 'htdocs' : " + e.getMessage());
            }
        }
    }

    public void start() {
        running = true;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur démarré sur le port " + PORT);
            while (running) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du démarrage du serveur : " + e.getMessage());
        } finally {
            stop();
        }
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'arrêt du serveur : " + e.getMessage());
        }
        System.out.println("Serveur arrêté.");
    }
}
