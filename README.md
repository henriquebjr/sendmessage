# Send Message
Serviço para envio de mensagens de e-mail, SMS, PUSH e whatsapp.


Este serviço foi construído utilizando [Quarkus](https://quarkus.io/).

Necessário banco mysql, que deve ser criado e informado nas propriedades do arquivo `application.properties` localizado na pasta resources. Ao subir a aplicação apontando para o banco, todas as tabelas serão criadas automaticamente.

Por padrão, a aplicação possui a seguinte configuração de conexão com o banco de dados:

```
quarkus.datasource.username=root
quarkus.datasource.password=root
quarkus.datasource.jdbc.url=jdbc:mysql://localhost:3306/sendmessage?autoReconnect=true
```

Para executar localmente (modo dev):

``
./mvnw compile quarkus:dev
``

Para gerar imagem docker com versão nativa:

```
mvn package -Pnative -Dquarkus.native.container-build=true
docker build -f src/main/docker/Dockerfile.native -t quarkus/sendmessage .
```

E, finalmente, execute o container com:
```
docker run -i --rm -p 8080:8080 quarkus/code-with-quarkus
```

## Utilizando

Por padrão, sempre que inicia o `sendmessage` apontando para um banco novo, serão criadas além de todas as tabelas, registros iniciais para pobissibiltar o uso com segurança.
No caso, é criado um tenant e um usuário administrador para esse tenant:

usuário: admin
senha: 123

Nessário utilizar inicialmente este usuário. Recomendado criar usuários específicos ou pelo menos configurar uma senha mais segura.

A api pode ser "navegada" através do Swagger-ui disponível em `http://0.0.0.0:8080/swagger-ui/`.

Todos endpoins são protegidos por autênticação básica http, ou seja, é necessário informar usuário e senha para acessar qualquer recurso.

Recursos de tenant e user, são acessíveis apenas para usuários com papel de administrador. Recurso de mensagem podem ser acessados por usuários com role de administrador ou usuário.

Exemplo para agendar uma mensagem:

```
curl -X POST \
  http://0.0.0.0:8080/messages \
  -H 'authorization: Basic YWRtaW46MTIz' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: cb97bd44-cb23-f923-dc67-d7d8739774a8' \
  -d '{
	"message": "texto da mensagem",
	"sendTo": "destinatário@mail.com",
	"type": "EMAIL",
	"scheduledDate": "2020-11-26T00:34:15Z[UTC]"
}'
```

Exemplo para listar mensagens já agendadas:

```
curl -X GET \
  http://0.0.0.0:8080/messages \
  -H 'authorization: Basic YWRtaW46MTIz' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 72d3317a-b29b-9989-ea57-4a64d0cafde6'
```

## Estrutura/Diagrama de Classes

![Diagrama](src/main/resources/diagram.png?raw=true)

A classe MessageService é a responsável por receber e armazenar as mensagens recebidas, colocando todas elas com status "pending".  

## Melhorias

- Testes integrados/Rest - atualmente apenas testes untários dos services foram implementados
- Versão reativa (há um branch feature com o início desta implementação, porém apenas a busca de registros está funcionando)
- Envio "de fato" de mensagens.