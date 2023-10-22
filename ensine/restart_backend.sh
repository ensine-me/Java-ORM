#!/bin/bash

sudo docker stop ensine-backend-container
sudo docker rm ensine-backend-container

sudo rm -rf ./target

echo ""
echo "Container removido"
echo "Iniciando um novo..."
./setup_backend.sh
