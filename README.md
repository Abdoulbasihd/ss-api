# ss-api
1. Avoir un ide du genre STS (eclipse) ou IntellijIdea installé
2. Intaller la base de données postgresql (sous linux, aussi simple que apt-get install postgresql :-))
3. Créer une ,ouvelle base de donnée ss_api dans postgresql
4. Créer un utilissateur du nom postgres ayant comme mot de passe "postgres" (modifiable dans le fichier application-test.properties si souhaité)
5. Attribuer tous les droit à l'utilisateur précédemment créé sur la BD ss_api
6. Compiler puis executer le projet (par maven ou directement sur li'IDE )
      mvn clean install
      mvn clean package (pour obtenir un exécutable jar)
      
