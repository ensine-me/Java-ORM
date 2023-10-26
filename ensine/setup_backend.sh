#!/bin/bash

set -e

echo "Script de instalação para a aplicação Spring Boot"
echo ""
sleep 2

echo "Configuração EC2"
sleep 1
echo ""
sudo apt update

echo ""
echo "Verificando instalação do docker..."
sleep 1
echo ""
if ! command -v docker &> /dev/null; then
        echo "Docker não está instalado. Instalando docker.io..."
        sudo apt install -y docker.io
        sudo systemctl start docker
        sudo systemctl enable docker
else
        echo "Docker já instalado."
fi

echo "Verificando se o arquivo JAR existe..."
sleep 1
#if [ ! -d "./target" ] || [ -z "$(find ./target/ -type f -name '*.jar')" ]; then
#    echo "Arquivo JAR não encontrado, gerando arquivo JAR..."
    mvn -f ~/Java-ORM/ensine/pom.xml clean install
#fi


echo "Construindo imagem docker..."
sleep 1
sudo docker build -t ensine-backend-image .

echo "Iniciando container docker..."
sleep 1
sudo docker run --name ensine-backend-container -p 8080:8080 -d ensine-backend-image

echo "Containers ativos:"
sudo docker ps -a
echo ""

echo "Script finalizado!"
