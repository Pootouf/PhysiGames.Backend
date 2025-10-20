-- V9__Insert_Language_fr-fr.sql
-- Ajoute le langage Français (code 'fr-fr') de manière idempotente.

INSERT INTO languages (code, libelle)
SELECT 'fr-fr', 'Français'
WHERE NOT EXISTS (SELECT 1 FROM languages l WHERE l.code = 'fr-fr');

