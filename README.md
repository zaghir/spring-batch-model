# Spring batch 
Ce projet est de type Maven avec l’implementation de springboot 2.1.1 pour démarrer  le projet
	Il implémente aussi :
	Poi 4.0.0 pour la creation des fichier excel 
	JavamailSender pour l’envoi de mail , le paramétrage ici c’est fait pour un smtp de google
	Spring 5 pour l’injection des dépendances 
	Spring batch 4 pour la création de batch 
	Thymeleaf 3 et Thymeleaf spring5 pour le moteur de Template et la création des gabarits html des mails
	Jdbc de type Mysql pour la connexion au base de données, c'est possible d'ajouter d'autre jdbc pour d'autre types de base de données
	    et aussi configurer les datasource
	La datasource c'est la datasource primary (@Primary) car c'est dans cette base ou se trouve les table technique de spring batch
	Dans le dossier spring-batch-model/information  se trouve une image pour trouver les script de creation des tables techniques 
	
	Le batch est composé de 
	Un seul step mais c’est possible d’ajouter d’autre 
	Service dao pour la récupération des données et l’insertion : CompteDao , la dao se base sur JdbcTemplate pour passer le requêtes 
	Un service excel pour créer un fichier excel 
	Un servie mail pour l’envoi de mail EnvoiMailService ,et un service mail pour la configuration de l’envoi SendMailService
	Le Step du batch fait :
		Dans le ReaderStep1 : la lecture de données depuis la datasource1 et passe la donnée au ProcessorStep1 
		Dans le ProcessorStep1 : récupère les données depuis le reader , insère la donnée avec la deuxième datasoure2 dans le base2 et passe la donnée au writer
		Dans le WriterStep1 : récupère la liste des données  traités dans processor ,créer un fichier excel avec la liste des données(Compte dans notre exemple) et envoie le mail par le EnvoiMailService, avec en pièce jointe le fichier Excel et les données variables qui seront injecter dans le gabarit de mail
