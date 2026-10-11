# MedHead - Proof of Concept

PoC réalisé dans le cadre du projet 11 du parcours **Architecte Logiciel / Software Architect** d'OpenClassrooms.

L'objectif est de démontrer qu'il est possible de recommander rapidement un hôpital adapté à une urgence à partir de la position de l'ambulance, de la spécialité médicale recherchée, de la disponibilité des lits et de la distance routière réelle.

## Architecture du PoC

Le projet est composé de deux parties :

- **Backend** : Java 11, Spring Boot 2.7, Maven, Spring Data JPA et H2.
- **Frontend** : Vue.js avec Vite.
- **Routage externe** : OpenRouteService pour calculer les distances et durées routières.
- **Tests de performance** : Apache JMeter.
- **CI** : GitHub Actions.

Flux principal :

1. L'utilisateur renseigne longitude, latitude, rayon de recherche et spécialité.
2. Le frontend appelle l'API Spring Boot.
3. Le backend filtre les hôpitaux compatibles et disponibles.
4. Le service de routage calcule la distance routière.
5. Les établissements sont classés et retournés au frontend.

## Prérequis

- Java 11
- Maven
- Node.js 22 recommandé
- npm
- Apache JMeter pour les tests de performance

## Lancer le backend

Depuis la racine du projet :

```bash
mvn spring-boot:run
```

Le backend est disponible sur :

```text
http://localhost:9000
```

La base H2 est utilisée en mémoire pour le PoC.

## Lancer le frontend

Dans un second terminal :

```bash
cd frontend
npm install
npm run dev
```

Le frontend est alors disponible sur :

```text
http://localhost:5173
```

Le serveur Vite redirige les appels `/api` vers le backend Spring Boot sur le port `9000`.

## Endpoint principal

Exemple de recherche d'hôpitaux :

```text
GET /nearest/-2.0115245/52.00514984/100/pharmacology
```

Paramètres :

- longitude ;
- latitude ;
- rayon maximal de recherche ;
- spécialité médicale.

Exemple d'endpoint utilisé pour isoler les performances internes du backend sans appel au service de routage externe :

```text
GET /hospital/range/-2.0115245/52.00514984/100/
```

## Tests backend

Lancer les tests unitaires et les tests d'intégration :

```bash
mvn clean verify -Pfailsafe
```

Le projet utilise notamment Maven Surefire, Failsafe et JaCoCo.

## Tests frontend

```bash
cd frontend
npm test
```

Construire le frontend :

```bash
npm run build
```

Les tests Vue sont exécutés avec Vitest et Vue Test Utils.

## Tests de performance

Les scénarios JMeter et leurs résultats sont disponibles dans le dossier :

```text
performance/
```

Trois campagnes de charge ont été exécutées sur le backend :

| Utilisateurs virtuels | Requêtes | Erreurs | Temps moyen | P95 | Débit |
|---:|---:|---:|---:|---:|----:|
| 20 | 100 | 0 % | 13.26 ms | 22 ms | 10.52 req/s |
| 50 | 500 | 0 % | 8.78 ms | 17 ms | 25.48 req/s |
| 100 | 1000 | 0 % | 7.90 ms | 15 ms | 33.65 req/s |

Le détail des scénarios, des métriques et des limites observées est documenté dans [`performance/README.md`](performance/README.md).

Le scénario de bout en bout dépend de **OpenRouteService**. Des réponses `429 Too Many Requests` ont été observées lors de tests sollicitant fortement cette API externe. Ce point constitue une limite du PoC et un sujet d'architecture à traiter pour une mise en production.

## Intégration continue

Le workflow GitHub Actions **MedHead CI** est exécuté sur les push et pull requests vers `main`.

Il vérifie notamment :

- compilation et tests du backend ;
- tests d'intégration ;
- génération du JAR ;
- tests du frontend ;
- build du frontend ;
- publication des rapports et artéfacts de build.

Le workflow est défini dans :

```text
.github/workflows/publish.yml
```

## Structure principale

```text
.
│── frontend/                 # Interface Vue.js
│── performance/              # Scénarios et résultats JMeter
│── src/                       # Backend Spring Boot
│── .github/workflows/        # Pipeline CI
│── pom.xml                   # Configuration Maven
└── README.md
```

## Limites du PoC

Ce dépôt constitue une preuve de concept et non une solution prête pour la production.

Les principaux sujets à renforcer pour une industrialisation sont notamment :

- résilience face aux limitations de l'API de routage ;
- stratégie de retry/backoff et cache ;
- observabilité et supervision ;
- persistance de données adaptée  à la production ;
- configuration et secrets externalisés ;
- déploiement et montée en charge dans un environnement cible.

## Origine du projet

Le PoC s'appuie sur un squelette technique fourni dans le cadre du projet OpenClassrooms et a été adapté et complété pour répondre aux objectifs du projet 11, notamment avec une interface Vue.js, des tests frontend, une pipeline CI et des scénarios de performance JMeter.

Repository de référence initial :

```text
https://github.com/etcomment/PoCApiMedHead
```
