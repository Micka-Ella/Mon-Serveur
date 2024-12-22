package serverpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Properties;
import serverconfig.*;

public class HttpServer {
    private SimpleHttpServer server;
    private JButton startButton;
    private JButton stopButton;
    private JButton configButton;
    private JCheckBox phpEnableCheckBox;
    private JCheckBox phpDisableCheckBox;

    public HttpServer() {
        JFrame frame = new JFrame("Serveur HTTP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);  // Augmenté pour ajouter les cases à cocher
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement

        // Création des boutons
        startButton = new JButton("Démarrer le serveur");
        stopButton = new JButton("Arrêter le serveur");
        configButton = new JButton("Configuration");

        // Création des cases à cocher pour activer/désactiver PHP
        phpEnableCheckBox = new JCheckBox("Activer PHP", SimpleHttpServer.isPhpEnabled());
        phpEnableCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (phpEnableCheckBox.isSelected()) {
                    // Désactiver la case "Désactiver PHP" lorsque "Activer PHP" est coché
                    phpDisableCheckBox.setSelected(false);
                    SimpleHttpServer.setPhpEnabled(true);  // Mettre à jour PHP_ENABLED
                }
                updateConfigFile();  // Mettre à jour le fichier config.txt
            }
        });

        phpDisableCheckBox = new JCheckBox("Désactiver PHP", !SimpleHttpServer.isPhpEnabled());
        phpDisableCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (phpDisableCheckBox.isSelected()) {
                    // Désactiver la case "Activer PHP" lorsque "Désactiver PHP" est coché
                    phpEnableCheckBox.setSelected(false);
                    SimpleHttpServer.setPhpEnabled(false);  // Mettre à jour PHP_ENABLED
                }
                updateConfigFile();  // Mettre à jour le fichier config.txt
            }
        });

        // Styles des boutons et des cases à cocher
        startButton.setBackground(new Color(39, 130, 38)); // Vert foncé
        stopButton.setBackground(new Color(178, 34, 34));  // Rouge foncé
        configButton.setBackground(new Color(70, 130, 180)); // Bleu acier
        phpEnableCheckBox.setBackground(Color.WHITE);
        phpDisableCheckBox.setBackground(Color.WHITE);

        // Action pour démarrer le serveur
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        // Action pour arrêter le serveur
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmAndStopServer(frame);
            }
        });

        // Action pour ouvrir le fichier de configuration
        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openConfigFile();
            }
        });

        // Ajout des éléments dans la fenêtre
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(startButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(stopButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(configButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(phpEnableCheckBox, gbc);  // Ajouter la case à cocher "Activer PHP"

        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(phpDisableCheckBox, gbc);  // Ajouter la case à cocher "Désactiver PHP"

        frame.setVisible(true);
    }

    private void startServer() {
        if (server == null) {
            server = new SimpleHttpServer();
            new Thread(() -> {
                try {
                    server.start();
                    updateButtonState(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erreur lors du démarrage du serveur: " + e.getMessage());
                }
            }).start();
        }
    }

    private void confirmAndStopServer(JFrame frame) {
        int confirmation = JOptionPane.showConfirmDialog(frame, "Êtes-vous sûr de vouloir arrêter le serveur et fermer toutes les fenêtres ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            stopServer();
            frame.dispose(); // Ferme la fenêtre principale
            System.exit(0);  // Termine le programme et les processus associés
        }
    }

    private void stopServer() {
        if (server != null) {
            server.stop(); // Assurez-vous que cette méthode fonctionne correctement
            server = null;
            updateButtonState(false);
            JOptionPane.showMessageDialog(null, "Serveur arrêté.");
        }
    }

    private void updateButtonState(boolean isRunning) {
        startButton.setEnabled(!isRunning);
        stopButton.setEnabled(isRunning);
    }

    private void openConfigFile() {
        File configFile = new File("config.txt");
        if (configFile.exists()) {
            try {
                Desktop.getDesktop().open(configFile); // Ouvre le fichier dans l'éditeur par défaut
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erreur lors de l'ouverture du fichier de configuration: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Le fichier de configuration n'existe pas.");
        }
    }

    private void updateConfigFile() {
        // Mise à jour de la configuration dans le fichier config.txt
        try (FileOutputStream fos = new FileOutputStream("config.txt")) {
            Properties properties = new Properties();
            properties.setProperty("PORT", String.valueOf(SimpleHttpServer.getPort()));
            properties.setProperty("ROOT", SimpleHttpServer.getDocRoot());
            properties.setProperty("PHP_ENABLED", String.valueOf(SimpleHttpServer.isPhpEnabled())); // Sauvegarder la valeur de PHP_ENABLED
            properties.store(fos, null);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour du fichier de configuration: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HttpServer::new);
    }
}
