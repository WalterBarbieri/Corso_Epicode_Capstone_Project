# Corso_Epicode_Capstone_Project
Progetto CapstoneProject di Barbieri Walter basato su un sito con funzionalità di ecommerce per una gioielleria artigianale. Utilizzo Trello per la suddivisione dei compiti, DrawIo e Excalidraw per diagrammi e progettazione architettura. Utilizzo GitHub desktop. Utilizzo di Postman per simulare le chiamate Http.
Al momento ha tutte le funzionalità incluso il carrello.
FUNZIONALITA' DA INCLUDERE:

- INVIO EMAIL TRAMITE BACKEND
- #Back-End
Per strutturare il progetto lato back-end sono state utilizzate le seguenti tecnologie:
- Java 17
- Spring Boot
- PostgreSQL

Dependency importate in Java:
- Lombok
- Spring Boot Dev Tools
- PostgreSQL Driver
- Spring Web
- Spring Data JPA
- Spring Security
- JJWT: API
- JJWT: IMPL
- JJWT: EXTENTIONS: JACKSON

- #Front-End
Per strutturare il progetto lato front-end sono state utilizzate le seguenti tecnologie:
- Angular
- Bootstrap

Dependency importate in Angular:
- Auth-JWT

- #Per eseguire il progetto
- * Back-End
- Creare file env.properties con queste proprietà:
PG_USERNAME=  ****
PG_PASSWORD=  ****
PG_DB=  ****
PORT=  ****
JWT_SECRET= ****

- Popolare database con la lista dei comuni
Far partire il seguente runner, rimuovendo il commento al converter:
/EcommerceGioielleria/src/main/java/CaptsoneProject/EcommerceGioielleria/runner/ComuneRunner.java

- Popolare database con utenti random
Far partire il seguente runner, rimuovendo il commento al generator:
/EcommerceGioielleria/src/main/java/CaptsoneProject/EcommerceGioielleria/runner/ComuneRunner.java

- * Front-End
- Fare Run del progetto:
ng s -o



