# Jeu de Mémorisation 

## Prérequis

- Java installé  
  Vérifier avec :
  ```bash
  java -version
  ```

- Apache Ant installé  
  Vérifier avec :
  ```bash
  ant -version
  ```

---

## Commandes à exécuter

Depuis la racine du projet :

### Nettoyer les anciens fichiers
```bash
ant clean
```

### Compiler le projet, générer la Javadoc et créer le fichier `.jar`
```bash
ant dist
```

### Exécuter le jeu directement
```bash
ant run
```

### Générer uniquement la documentation Javadoc (optionnel)
```bash
ant javadoc
```
