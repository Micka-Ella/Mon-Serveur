package serverconfig;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationLoader {
    private static final String CONFIG_FILE = "config.txt";
    private static int port;
    private static String docRoot;
    private static boolean phpEnabled;

    static {
        loadConfig();  // Charger les configurations au démarrage
    }

    // Méthode pour charger la configuration à partir du fichier config.txt
    public static void loadConfig() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);

            port = Integer.parseInt(properties.getProperty("PORT", "8080"));
            docRoot = properties.getProperty("ROOT", "htdocs");
            phpEnabled = Boolean.parseBoolean(properties.getProperty("PHP_ENABLED", "false"));

        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la configuration : " + e.getMessage());
            // Définir des valeurs par défaut si le fichier n'existe pas ou est mal formaté
            port = 1234;
            docRoot = "htdocs";
            phpEnabled = false;
        }
    }

    // Méthode pour obtenir le port
    public static int getPort() {
        return port;
    }

    // Méthode pour obtenir le répertoire racine des fichiers
    public static String getDocRoot() {
        return docRoot;
    }

    // Méthode pour savoir si PHP est activé
    public static boolean isPhpEnabled() {
        return phpEnabled;
    }

    // Méthode pour mettre à jour l'activation de PHP et le sauvegarder dans le fichier config
    public static void setPhpEnabled(boolean enabled) throws IOException {
        phpEnabled = enabled;
        // Sauvegarde la configuration mise à jour dans le fichier
        Properties properties = new Properties();
        properties.setProperty("PORT", String.valueOf(port));
        properties.setProperty("ROOT", docRoot);
        properties.setProperty("PHP_ENABLED", String.valueOf(phpEnabled));

        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, "Mise à jour de la configuration");
        }
    }
}
