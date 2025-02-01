CREATE TABLE "items" (
  "id" serial PRIMARY KEY,
  "name" varchar(255) NOT NULL,
  "description" text DEFAULT NULL,
  "count" int DEFAULT 1
);

INSERT INTO "items" ("name", "description")
VALUES
    ('HP ProLiant DL360 Gen7', 'Пустая платформа, HP Array SAS');