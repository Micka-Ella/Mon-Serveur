 HEAD
# Mon-Serveur

Bienvenue dans le projet **HTTP Server**, un serveur HTTP léger et personnalisable écrit en Java. Ce serveur est conçu pour être simple à installer, à configurer et à utiliser. Il permet de servir des fichiers HTML, CSS, JS, ainsi que des fichiers PHP si activés via l'interface.

## Installation

1. Téléchargez le fichier `.deb` du serveur.

2. Installez-le en utilisant la commande suivante dans votre terminal :

   sudo dpkg -i monserveurhttp.deb

3. Si des dépendances sont manquantes, vous pouvez les installer avec cette commande :

   sudo apt-get install -f

Une fois l'installation terminée, le serveur est prêt à être utilisé.

## Utilisation

### Démarrer le serveur

Pour démarrer le serveur, ouvrez un terminal et tapez la commande suivante :

http-server


Cela ouvrira une fenêtre avec plusieurs options de configuration et d'administration :

- **Start** : Lance le serveur HTTP.
- **Stop** : Arrête le serveur HTTP.
- **Config** : Accédez aux paramètres de configuration pour ajuster le comportement du serveur (activation/désactivation de PHP, modification des répertoires, etc.).
- **PHP Activation** : Un champ à cocher permet d'activer ou de désactiver le traitement des fichiers PHP directement depuis l'interface.

### Fonctionnalités principales

- **Interface conviviale** : Un tableau de bord simple pour contrôler facilement le serveur.
- **Activation de PHP** : Activez ou désactivez le traitement des fichiers PHP via une case à cocher dans l'interface.
- **Personnalisation facile** : Adaptez le comportement du serveur selon vos besoins via les options de configuration.
  
### Désinstallation

Si vous souhaitez désinstaller le serveur, vous pouvez utiliser la commande suivante :

sudo dpkg -r monserveurhttp

## Support

Si vous avez des questions ou rencontrez des problèmes, n'hésitez pas à contacter les mainteneurs :

- **Mainteneur** : Mickaella [macsunlinemickaella@gmail.com](mailto:macsunlinemickaella@gmail.com)
- **Mainteneur** : Josia [raobelinajosia@gmail.com](mailto:raobelinajosia@gmail.com)

Merci d'utiliser **HTTP Server** 
13c7f00 (Premier commit)
