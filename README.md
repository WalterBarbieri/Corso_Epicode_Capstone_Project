# Corso_Epicode_Capstone_Project
Progetto CapstoneProject di Barbieri Walter basato su un sito con funzionalità di ecommerce per una gioielleria artigianale. Utilizzo Trello per la suddivisione dei compiti, DrawIo e Excalidraw per diagrammi e progettazione architettura. Utilizzo GitHub desktop. Utilizzo di Postman per simulare le chiamate Http.
Al momento ha tutte le funzionalità incluso il carrello.
FUNZIONALITA' DA INCLUDERE:
- INTEGRAZIONE SISTEMA DI PAGAMENTO
- PROCESSO DELL'ORDINE
- INVIO EMAIL TRAMITE BACKEND
- 
#Back-End
Per strutturare il progetto lato back-end sono state utilizzate le seguenti tecnologie:
1.Java 17
2.Spring Boot
3.PostgreSQL

Dependency importate in Java:
1.Lombok
2.Spring Boot Dev Tools
3.PostgreSQL Driver
4.Spring Web
5.Spring Data JPA
6.Spring Security
7.JJWT: API
8.JJWT: IMPL
9.JJWT: EXTENTIONS: JACKSON

#Front-End
Per strutturare il progetto lato front-end sono state utilizzate le seguenti tecnologie:
1.Angular
2.Bootstrap

Dependency importate in Angular:
1.Auth-JWT

#Per eseguire il progetto
* Back-End
1. Creare file env.properties con queste proprietà:
PG_USERNAME=  ****
PG_PASSWORD=  ****
PG_DB=  ****
PORT=  ****
JWT_SECRET= ****

2. Popolare database con la lista dei comuni
Far partire il seguente runner, rimuovendo il commento al converter:
/EcommerceGioielleria/src/main/java/CaptsoneProject/EcommerceGioielleria/runner/ComuneRunner.java

2.1 Popolare database con utenti random
Far partire il seguente runner, rimuovendo il commento al generator:
/EcommerceGioielleria/src/main/java/CaptsoneProject/EcommerceGioielleria/runner/ComuneRunner.java

* Front-End
1. Fare Run del progetto:
ng s -o



