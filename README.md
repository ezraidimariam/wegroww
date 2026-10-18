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
-   Spring Framework / Spring MVC
-   Spring Data
-   PostgreSQL
-   Docker Compose
-   Tomcat 10.1+
-   Packaging WAR
-   Configuration Spring en XML

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

Pour démarrer les services définis dans le fichier `compose.yaml` ou
`docker-compose.yml` :

``` bash
docker compose up -d
```

Pour ouvrir une session PostgreSQL dans le conteneur `spring-postgres` :

``` bash
docker exec -it spring-postgres psql -U postgres -d wegrow
```

Pour afficher les tables :

``` sql
\dt
```

### Gestion du schéma --- sans Liquibase

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

## Endpoints prévus

  Méthode    Endpoint                            Réponses attendues
  ---------- ----------------------------------- ---------------------
  `POST`     `/api/departements`                 `201`, `409`
  `GET`      `/api/departements`                 `200`
  `DELETE`   `/api/departements/{id}`            `204`, `404`, `409`
  `POST`     `/api/departements/{id}/employes`   `201`, `404`, `409`
  `GET`      `/api/departements/{id}/employes`   `200`, `404`

Les réponses indiquées correspondent aux spécifications attendues.
Vérifier l'implémentation actuelle avant de considérer chaque endpoint
comme terminé.

## Règles métier principales

-   Le nom d'un département doit être unique.
-   Le matricule d'un employé doit être unique.
-   Un employé appartient à un seul département existant.
-   Un département contenant des employés ne peut pas être supprimé.
-   Les erreurs doivent utiliser des codes HTTP adaptés, notamment `404`
    et `409`.
-   La base de données doit préserver l'intégrité des données, y compris
    lors de requêtes simultanées.

## Compilation

À la racine du projet :

``` bash
mvn clean package
```

Le fichier WAR est généré dans le dossier `target/`. Il peut ensuite
être déployé sur Tomcat 10.1+, selon la configuration du serveur.

## Note

Ce README décrit la configuration prévue pour le projet. Avant le
déploiement, vérifier les paramètres de connexion PostgreSQL, les ports
Docker, le nom du WAR et le contexte de déploiement Tomcat.
