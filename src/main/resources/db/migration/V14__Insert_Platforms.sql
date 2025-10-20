-- Insert PS4 and PS5 platforms
-- Idempotent: if a platform with the same code already exists, do nothing

INSERT INTO platforms (code, libelle) VALUES
  ('PS4', 'PlayStation 4'),
  ('PS5', 'PlayStation 5')
ON CONFLICT (code) DO NOTHING;

