# Tests de performance - MedHead PoC

## Objectif

Ces tests JMeter évaluent le comportement du backend MedHead sous charge et permettent d'identifier les éventuels points de saturation du PoC.

Deux scénarios sont disponibles :

- `medhead-load-test.jmx` : test de charge du backend sur l'endpoint `/hospital/range/...`, sans dépendance au service de routage externe.
- `medhead-e2e-test.jmx` : scénario de bout en bout sur `/nearest/...`, incluant la recherche d'hôpitaux et l'appel au service de calcul d'itinéraire externe.

## Résultats des tests de charge

| Utilisateurs | Requêtes | Erreurs | Temps moyen | P90 | P95 | P99 | Débit |
|---:|---:|---:|---:|---:|---:|---:|---:|
| 20 | 100 | 0 % | 13.26 ms | 20.9 ms | 22 ms | 155.78 ms | 10.52 req/s |
| 50 | 500 | 0 % | 8.78 ms | 12 ms | 17 ms | 34.96 ms | 25.48 req/s |
| 100 | 1000 | 0 % | 7.90 ms | 11 ms | 15 ms | 17.99 ms | 33.65 req/s |

Les trois campagnes ont terminé sans erreur côté backend.

La diminution du temps moyen observée sur les campagnes les plus importantes peut notamment s'expliquer par l'échauffement de la JVM, le JIT et les caches. Elle ne doit pas être interprétée comme la preuve que les performances s'améliorent systématiquement lorsque la charge augmente.

Dans l'environnement local utilisé pour le PoC, le backend est resté stable jusqu'à 100 utilisateurs virtuels et 1000 requêtes sur le scénario testé.

## Dépendance externe

Le scénario de bout en bout `/nearest/...` dépend du service de routage OpenRouteService.

Lors des essais, cette dépendance externe a pu retourner des erreurs HTTP `429 Too Many Requests`. Le backend peut alors retourner une réponse `408 Request Timeout`.

Ce comportement met en évidence un point d'attention architectural pour une mise en production : quota adapté, gestion du rate limiting, mécanisme de retry/backoff, cache ou service de routage disposant d'un niveau de service compatible avec les besoins de MedHead.

## Fichiers

- `medhead-load-test.jmx` : scénario de charge interne
- `medhead-e2e-test.jmx` : scénario fonctionnel de bout en bout
- `results-20u-100req.jtl` : résultats bruts 20 utilisateurs
- `results-50u-500req.jtl` : résultats bruts 50 utilisateurs
- `results-100u-1000req.jtl` : résultats bruts 100 utilisateurs
- `statistics/` : statistiques synthétiques générées par JMeter

## Régénérer un rapport HTML

Exemple :

```bash
jmeter -g performance/results-100u-1000req.jtl -o performance/report-100u-1000req
```

Les dossiers `performance/report-*` sont volontairement ignorés par Git car JMeter y génère plusieurs centaines de fichiers HTML, CSS et JavaScript.
