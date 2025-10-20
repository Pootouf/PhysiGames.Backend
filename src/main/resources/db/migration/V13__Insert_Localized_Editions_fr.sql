INSERT INTO localized_editions (edition_id, language_id, name)
SELECT e.id, l.id, v.name
FROM (VALUES
    ('standard', 'Standard'),
    ('promo', 'Promo'),
    ('review', 'Review'),
    ('slipcover', 'Fourreau'),
    ('limited', 'Limitée'),
    ('collector', 'Collector'),
    ('ps_hits', 'Playstation Hits'),
    ('steelbook', 'Steelbook')
) AS v(code, name)
JOIN editions e ON e.code = v.code
JOIN languages l ON l.code = 'fr-fr'
WHERE NOT EXISTS (
    SELECT 1 FROM localized_editions le WHERE le.edition_id = e.id AND le.language_id = l.id
);
