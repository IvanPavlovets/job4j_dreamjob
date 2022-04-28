CREATE TABLE if not exists post (
  id SERIAL PRIMARY KEY,
  name TEXT,
  description TEXT,
  created TEXT,
  visible bool,
  city_id int
);
