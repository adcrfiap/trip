# trip

## Pré-requisito

* AWS SAM
* AWS CLI
* DOKCER
* Conta ativa na AWS

## Deploy local
 
 * Iniciar DynamoDB via docker com o comando abaixo:
   docker run -p 8000:8000 -v C:/dynamo/dynamodb:/data/ amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb -dbPath /data
   
 * Criar a tabela trip dentro do dynamoDB local:
    aws dynamodb create-table --table-name trip --attribute-definitions AttributeName=country,AttributeType=S AttributeName=city,AttributeType=S AttributeName=tripDate,AttributeType=S --key-schema AttributeName=country,KeyType=HASH AttributeName=tripDate,KeyType=RANGE --local-secondary-indexes IndexName=cityIndex,KeySchema=[{AttributeName=country,KeyType=HASH},{AttributeName=city,KeyType=RANGE}],Projection={ProjectionType=ALL} --billing-mode PAY_PER_REQUEST --endpoint-url http://localhost:8000
    
 * Dentro da pasta do projeto executar o comando abaixo ( utilizar o arquivo test_environment*.json correspondente ao seu sistema)
   sam local start-api --env-vars src/test/resources/test_environment_windows.json
   
## Deploy AWS
  * Criar um bucket dentro da aws com o comando abaixo:
    aws s3 mb s3://<bucketname>
  
  * Dentro da pasta do projeto fazer o upload do arquivo template para dentro do bucket criado
    sam package  --template-file template.yaml  --output-template-file packaged.yaml  --s3-bucket <bucketname>
  
  * Executar o Deploy para o Cloudformation Stack
    sam deploy --template-file packaged.yaml --stack-name trip-stack --capabilities CAPABILITY_IAM
