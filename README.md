# WeGrow API

API REST développée avec **Spring Framework (Spring Core, Spring MVC et
Spring Data)** pour permettre à l'équipe RH de gérer les départements et
leurs employés.

## Objectif

Le projet couvre les opérations suivantes :

-   Créer un département.
-   Lister les départements avec le nombre d'employés.
-   Supprimer un département.
-   Ajouter un employé à un département.
-   Lister les employés d'un département.

## Technologies

-   Java 21
-   Maven
-   Spring Boot / Spring MVC
-   Spring Data JPA / Hibernate
-   Jakarta Validation
-   PostgreSQL
-   Docker Compose
-   Tomcat embarqué
-   Packaging JAR exécutable

## Architecture

Le projet suit une architecture en couches :

-   **Controller** : reçoit les requêtes HTTP et renvoie les réponses.
-   **Service** : applique les règles métier.
-   **Repository** : accède aux données persistées.
-   **Entity** : représente les données enregistrées en base.
-   **DTO / Mapper** : structure les données échangées par l'API.

## Base de données

La base de données utilisée est PostgreSQL et elle peut être lancée avec
Docker Compose.

La configuration locale est dans `.env` (copiée depuis `.env.example`). Les
identifiants sont réservés au développement local. Pour lancer PostgreSQL :

``` bash
docker compose up -d
```

La base `wegrow_db` est initialisée avec les tables `departments` et
`employees`, ainsi que quelques données de démonstration. Pour vérifier :

```bash
docker exec -it spring-postgres psql -U app_user -d wegrow_db -c "\\dt"
docker exec -it spring-postgres psql -U app_user -d wegrow_db -c "SELECT d.name, COUNT(e.id) AS employees FROM departments d LEFT JOIN employees e ON e.department_id = d.id GROUP BY d.id ORDER BY d.id;"
```

Arrêter PostgreSQL sans effacer les données : `docker compose down`. Pour
repartir de zéro, supprimer le volume avec `docker compose down -v` (cela
efface définitivement les données locales de démonstration).

### Gestion du schéma — sans Liquibase

**Liquibase n'est pas configuré dans cette version du projet.** Le
schéma de la base de données est donc géré par le script SQL
d'initialisation `db/init.sql` et/ou par des modifications SQL
manuelles, selon l'état actuel de la base.

Lorsqu'une nouvelle table ou une nouvelle relation est ajoutée :

1.  Vérifier le schéma existant avant de modifier la base.
2.  Ajouter la modification au script SQL approprié ou préparer une
    migration SQL séparée.
3.  Appliquer la modification à la base concernée.
4.  Vérifier que les entités Java et les tables/colonnes utilisent des
    noms et des types compatibles.

> Attention : avec un volume Docker PostgreSQL déjà initialisé, le
> script d'initialisation n'est généralement pas rejoué automatiquement
> à chaque démarrage. Ne supprimez pas le volume pour forcer son
> exécution sans avoir sauvegardé les données.

## Endpoints implémentés dans le code actuel

  Méthode    Endpoint                            Réponses attendues
  ---------- ----------------------------------- ---------------------
  `POST`     `/api/departments`                  `201`, `409`
  `GET`      `/api/departments`                  `200`
  `DELETE`   `/api/departments/{id}`             `204`, `404`, `409`
  `GET`      `/api/departments/with-employee-count` `200`
  `POST`     `/api/departements/{id}/employes`    `201`, `404`, `409`
  `GET`      `/api/departements/{id}/employes`    `200`, `404`

Les contrôleurs utilisent actuellement les chemins anglais pour les
départements et les chemins français pour les employés.

## Règles métier principales

-   Le nom d'un département doit être unique.
-   Le matricule d'un employé doit être unique.
-   Un employé appartient à un seul département existant.
-   Un département contenant des employés ne peut pas être supprimé.
-   Les erreurs doivent utiliser des codes HTTP adaptés, notamment `404`
    et `409`.
-   La base de données doit préserver l'intégrité des données, y compris
    lors de requêtes simultanées.

## Démarrer l'API

Démarrer PostgreSQL avec Docker Compose, puis lancer l'API :

``` bash
docker compose up -d
mvn spring-boot:run
```

L'API écoute sur `http://localhost:8080`. La connexion PostgreSQL utilise
par défaut `localhost:15432`, `wegrow_db` et `app_user`. Le mot de passe
est défini dans `.env`. Ces valeurs peuvent être remplacées par les
variables `DB_URL`, `DB_USERNAME` et `DB_PASSWORD`.

Pour créer le JAR exécutable :

```bash
mvn clean package
java -jar target/wegrow-api-1.0.0.jar
```

## Note

Ce README décrit la configuration prévue pour le projet. Avant le
déploiement, vérifier les paramètres de connexion PostgreSQL, les ports
Docker, le nom du WAR et le contexte de déploiement Tomcat.
