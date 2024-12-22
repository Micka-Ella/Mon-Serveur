#!/bin/bash

# Installer Java si nécessaire
echo "Vérification de Java..."
if ! command -v java &> /dev/null
then
    echo "Java n'est pas installé. Installation de OpenJDK..."
    sudo apt update
    sudo apt install -y openjdk-11-jre
else
    echo "Java est déjà installé."
fi

# Créer le répertoire d'installation dans /opt
echo "Création du répertoire d'installation..."
sudo mkdir -p /opt/MonServeurHttp
sudo cp -r * /opt/MonServeurHttp/

# Donner les permissions appropriées
echo "Attribution des permissions..."
sudo chown -R $USER:$USER /opt/MonServeurHttp
chmod +x /opt/MonServeurHttp/install.sh

# Créer un lien symbolique pour faciliter l'exécution du serveur
echo "Création du lien symbolique..."
sudo ln -s /opt/MonServeurHttp/MonServeurHttp.sh /usr/local/bin/monserveur

# Créer un fichier de configuration du serveur
echo "Création du fichier de configuration..."
cat <<EOF > /opt/MonServeurHttp/config.txt
# Configuration du serveur
# Port, Document Root, etc.
port=8080
documentRoot=/opt/MonServeurHttp/htdocs
EOF

# Informations sur l'installation
echo "Installation terminée. Vous pouvez maintenant démarrer votre serveur avec la commande :"
echo "monserveur"
