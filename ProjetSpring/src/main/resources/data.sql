INSERT INTO categorie (nom) values ("Sauce"), ("Poisson"), ("Viande");

INSERT INTO produit (duree_de_conservation_en_jours, nom, categorie_id) values (3, "Cabillaud", 2);
INSERT INTO produit (duree_de_conservation_en_jours, nom, categorie_id) values (3, "Dorade", 2);
INSERT INTO produit (duree_de_conservation_en_jours, nom, categorie_id) values (3, "Saumon", 2);
INSERT INTO produit (duree_de_conservation_en_jours, nom, categorie_id) values (4, "Sauce tartare", 1);
INSERT INTO produit (duree_de_conservation_en_jours, nom, categorie_id) values (4, "Sauce Béarnaise", 1);
INSERT INTO produit (duree_de_conservation_en_jours, nom, categorie_id) values (3, "Sauce pesto", 1);
INSERT INTO produit (duree_de_conservation_en_jours, nom, categorie_id) values (4, "Sauce chèvre", 1);
INSERT INTO produit (duree_de_conservation_en_jours, nom, categorie_id) values (4, "Sauce burger", 1);
INSERT INTO produit (duree_de_conservation_en_jours, nom, categorie_id) values (1, "Sauce salade", 3);
INSERT INTO produit (duree_de_conservation_en_jours, nom, categorie_id) values (2, "Sauce salade", 3);
INSERT INTO produit (duree_de_conservation_en_jours, nom, categorie_id) values (3, "Sauce salade", 3);
INSERT INTO produit (duree_de_conservation_en_jours, nom, categorie_id) values (3, "Sauce salade", 3);
INSERT INTO produit (duree_de_conservation_en_jours, nom, categorie_id) values (3, "Sauce salade", 3);

insert into role (nom) values ('UTILISATEUR'), ('ADMIN');

INSERT INTO utilisateur (login, adresse_mail, mot_de_passe, nom, prenom, role_id) values ("csns", "csns@gmail.com", "$2a$10$JsC.ZH9hFfsPjtOU2UIjjeg3tct7gZyVrT4mfIJuCXa7FDJbNBFP.", "Sepetauskas", "Catia", 2);