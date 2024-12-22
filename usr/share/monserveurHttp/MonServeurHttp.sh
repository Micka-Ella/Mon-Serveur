#!/bin/bash

# Lancer le serveur HTTP Java avec le classpath correct
echo "Démarrage du serveur HTTP..."
java -cp /opt/MonServeurHttp/serverconfig:/opt/MonServeurHttp/serverpanel:/opt/MonServeurHttp MonServeurHttp.serverpanel.HttpServer
