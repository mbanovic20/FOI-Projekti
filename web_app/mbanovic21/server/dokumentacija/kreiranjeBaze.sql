-- Creator:       MySQL Workbench 8.0.32/ExportSQLite Plugin 0.1.0
-- Author:        Unknown
-- Caption:       New Model
-- Project:       Name of the project
-- Changed:       2024-01-04 16:32
-- Created:       2023-10-21 18:23
BEGIN;
CREATE TABLE "tipKorisnika"(
  "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "uloga" VARCHAR(20) NOT NULL,
  "opis" TEXT
);
CREATE TABLE "Serije"(
  "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "id_tmdb" INTEGER NOT NULL,
  "naziv" VARCHAR(50) NOT NULL,
  "opis" TEXT,
  "broj_sezona" INTEGER NOT NULL,
  "broj_epizoda" INTEGER NOT NULL,
  "popularnost" FLOAT NOT NULL,
  "slika" TEXT,
  "poveznica" TEXT,
  CONSTRAINT "id_tmdb_UNIQUE"
    UNIQUE("id_tmdb"),
  CONSTRAINT "homepage_UNIQUE"
    UNIQUE("poveznica")
);
CREATE TABLE "Sezone"(
  "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "id_tmdb" INTEGER NOT NULL,
  "naziv" VARCHAR(100) NOT NULL,
  "opis" TEXT NOT NULL,
  "slika" TEXT,
  "broj_sezone" INTEGER NOT NULL,
  "broj_epizoda" INTEGER NOT NULL,
  "Serije_id" INTEGER NOT NULL,
  CONSTRAINT "id_tmdb_UNIQUE"
    UNIQUE("id_tmdb"),
  CONSTRAINT "fk_Sezone_Serije1"
    FOREIGN KEY("Serije_id")
    REFERENCES "Serije"("id")
);
CREATE INDEX "Sezone.fk_Sezone_Serije1_idx" ON "Sezone" ("Serije_id");
CREATE TABLE "Korisnik"(
  "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "korime" VARCHAR(20) NOT NULL,
  "lozinka" VARCHAR(30) NOT NULL,
  "email" VARCHAR(45) NOT NULL,
  "ime" VARCHAR(30),
  "prezime" VARCHAR(30),
  "adresa" TEXT,
  "datum_rođenja" TEXT,
  "totp" VARCHAR(50),
  "aktivan" VARCHAR(2),
  "prijava" VARCHAR(2),
  "aktivacijskiKod" VARCHAR(100),
  "tajniKljuc" VARCHAR(45),
  "tajniKljucPrikazan" VARCHAR(2),
  "tipKorisnika_id" INTEGER NOT NULL,
  CONSTRAINT "e-mail_UNIQUE"
    UNIQUE("email"),
  CONSTRAINT "korime_UNIQUE"
    UNIQUE("korime"),
  CONSTRAINT "fk_Korisnik_tipKorisnika"
    FOREIGN KEY("tipKorisnika_id")
    REFERENCES "tipKorisnika"("id")
);
CREATE INDEX "Korisnik.fk_Korisnik_tipKorisnika_idx" ON "Korisnik" ("tipKorisnika_id");
CREATE TABLE "Favoriti"(
  "Serije_id" INTEGER NOT NULL,
  "Korisnik_id" INTEGER NOT NULL,
  PRIMARY KEY("Serije_id","Korisnik_id"),
  CONSTRAINT "fk_Favoriti_Serije1"
    FOREIGN KEY("Serije_id")
    REFERENCES "Serije"("id"),
  CONSTRAINT "fk_Favoriti_Korisnik1"
    FOREIGN KEY("Korisnik_id")
    REFERENCES "Korisnik"("id")
);
CREATE INDEX "Favoriti.fk_Favoriti_Serije1_idx" ON "Favoriti" ("Serije_id");
CREATE INDEX "Favoriti.fk_Favoriti_Korisnik1_idx" ON "Favoriti" ("Korisnik_id");
CREATE TABLE "Dnevnik"(
  "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "datum" TEXT NOT NULL,
  "vrijeme" TIME NOT NULL,
  "vrsta_zahtjeva" VARCHAR(15) NOT NULL,
  "trazeni_resurs" VARCHAR(500) NOT NULL,
  "tijelo" VARCHAR(500) NOT NULL,
  "Korisnik_id" INTEGER NOT NULL,
  CONSTRAINT "id_UNIQUE"
    UNIQUE("id"),
  CONSTRAINT "fk_Dnevnik_Korisnik1"
    FOREIGN KEY("Korisnik_id")
    REFERENCES "Korisnik"("id")
);
CREATE INDEX "Dnevnik.fk_Dnevnik_Korisnik1_idx" ON "Dnevnik" ("Korisnik_id");
COMMIT;

-- tipKorisnika
INSERT INTO tipKorisnika(uloga, opis) 
VALUES("administrator", "Uloga administratora osigurava učinkovit rad i zaštitu informacijskog sustava.");

INSERT INTO tipKorisnika(uloga, opis) 
VALUES("registrirani korisnik", "Korisnik koji je kreirao račun i unio svoje podatke.");

-- Korisnik
INSERT INTO Korisnik(korime, lozinka, "email", ime, prezime, datum_rođenja,  aktivan, tipKorisnika_id)
VALUES ('obican', '2317c5cc4e67b0cb5f55b26fdcf5fe0a24012503ae99d22b26f3c866d281be2b', 'obican@student.foi.hr', 'Obican', 'Korisnik', '19.11.2002.', 'da', 2);

INSERT INTO Korisnik(korime, lozinka, "email", ime, prezime, datum_rođenja, aktivan, tipKorisnika_id)
VALUES ('admin', '2317c5cc4e67b0cb5f55b26fdcf5fe0a24012503ae99d22b26f3c866d281be2b', 'admin@student.foi.hr', 'Admin', 'Korisnik', '19.11.2002.', 'da', 1);

DELETE FROM Korisnik WHERE korime = 'admin';