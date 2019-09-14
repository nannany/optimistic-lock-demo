DROP TABLE IF EXISTS demodata;

CREATE TABLE demodata (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  career VARCHAR(250) DEFAULT NULL,
  updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
  insertTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);

INSERT INTO demodata (first_name, last_name, career) VALUES
  ('Aliko', 'Dangote', 'Billionaire Industrialist'),
  ('Bill', 'Gates', 'Billionaire Tech Entrepreneur'),
  ('Folrunsho', 'Alakija', 'Billionaire Oil Magnate');