echo "Logging in to Amazon ECR..."
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 264927492157.dkr.ecr.us-east-1.amazonaws.com
if [ $? -ne 0 ]; then
  echo "Erro ao fazer login no ECR."
  exit 1
fi

echo "Login bem-sucedido no ECR."

# Passo 2: Construir a imagem Docker
echo "Building Docker image..."
docker build -t tastytap-payments .

if [ $? -ne 0 ]; then
  echo "Erro ao construir a imagem Docker."
  exit 1
fi

echo "Imagem Docker construída com sucesso."

# Passo 3: Tag da imagem para o repositório ECR
echo "Tagging the Docker image..."
docker tag tastytap-payments:latest 264927492157.dkr.ecr.us-east-1.amazonaws.com/tastytap-payments:latest
if [ $? -ne 0 ]; then
  echo "Erro ao taggear a imagem Docker."
  exit 1
fi

echo "Imagem Docker taggeada com sucesso."

# Passo 4: Enviar a imagem para o ECR
echo "Pushing Docker image to ECR..."
docker push 264927492157.dkr.ecr.us-east-1.amazonaws.com/tastytap-payments:latest
if [ $? -ne 0 ]; then
  echo "Erro ao enviar a imagem para o ECR."
  exit 1
fi

echo "Imagem Docker enviada com sucesso para o ECR."