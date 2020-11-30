# sendmessage
Serviço para envio de mensagens de e-mail, SMS, PUSH e whatsapp.


Este serviço foi construído utilizando [Quarkus](https://quarkus.io/).

Necessário banco mysql, que deve ser criado e informado nas propriedades do arquivo `application.properties` localizado na pasta resources. Ao subir a aplicação apontando para o banco, todas as tabelas serão criadas automaticamente.

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

A api pode ser "navegada" através do Swagger-ui disponível em `http://0.0.0.0:8080/swagger-ui/`.

Todos endpoins são protegidos por autênticação básica http, ou seja, é necessário informar usuário e senha para acessar qualquer recurso.

Recursos de tenant e user, são acessíveis apenas para usuários com papel de administrador. Recurso de mensagem podem ser acessadas por usuários com role de administrador ou usuário.

Por padrão, sempre que inicia o `sendmessage` apontando para um banco novo, serão criadas além de todas as tabelas, registros iniciais para pobissibiltar o uso com segurança.
No caso, é criado um tenant e um usuário administrador para esse tenant:

usuário: admin
senha: 123

Nessário utilizar este usuário seja para configurar outros usuários ou para apenas configurar com uma senha mais segura.

