INSERT INTO editions (code) VALUES
  ('standard'),
  ('promo'),
  ('review'),
  ('slipcover'),
  ('limited'),
  ('collector'),
  ('ps_hits'),
  ('steelbook')
ON CONFLICT (code) DO NOTHING;

