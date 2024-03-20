import express from "express";
import kolacici from "cookie-parser";
import sesija from "express-session";

import { passport, konfigurirajGithubAuth } from "./githubAuth.mjs";

import { Konfiguracija } from "../konfiguracija.js";
import HtmlUpravitelj from "./htmlUpravitelj.js";
import FetchUpravitelj from "./fetchUpravitelj.js";
import path from "path";

//import portovi from "/var/www/RWA/2023/portovi.js";
//const port = portovi.mbanovic21;
const port = 12309;
const server = express();
import cors from "cors";

//putanja
import { fileURLToPath } from "url";
import { dirname } from "path";
import Autentifikacija from "./autentifikacija.js";
const __filename = fileURLToPath(import.meta.url);
const putanja = dirname(__filename);

let konf = new Konfiguracija();
konf
	.ucitajKonfiguraciju()
	.then(pokreniServer)
	.catch((greska) => {
		console.log(greska);
		if (process.argv.length == 2) {
			console.error("Molim unesite naziv datoteke!");
		} else {
			console.error("Pogrešan naziv datoteke!: " + greska.path);
		}
		process.exit(1);
	});

function pokreniServer() {
	server.use(express.urlencoded({ extended: true }));
	server.use(cors());
	server.use(express.json());

	server.use(kolacici());
	server.use(
		sesija({
			secret: konf.dajKonf().tajniKljucSesija,
			saveUninitialized: true,
			cookie: {
				httpOnly: true,
				secure: false,
				maxAge: 1000 * 60 * 60 * 3,
			},
			resave: false,
			saveUninitialized: true,
			cookie: { secure: false },
		})
	);

	//dokumentacija
	server.use("/dokumentacija", express.static(putanja + "/dokumentacija"));
	server.use("/js", express.static(putanja + "/aplikacija/js"));
	server.get("/konfiguracija.js", (zahtjev, odgovor) => {
		odgovor.type("application/javascript");
	});
	server.get("/servis/klijentTMDB.js", (zahtjev, odgovor) => {
		odgovor.type("application/javascript");
	});

	//angular
	server.use(
		express.static(path.join(putanja, "../angular/dist/zadaca_02/browser"))
	);

	server.use(passport.initialize());
	server.use(passport.session());

	konfigurirajGithubAuth();

	pripremiPutanjePocetna();
	pripremiPutanjeAutentifikacija();
	pripremiPutanjeGit();
	//mora na kraj jer ima "*" ilitiga catch-all rutu
	pripremiPutanjuAngular();

	server.use((zahtjev, odgovor) => {
		odgovor.status(404);
		odgovor.json({ opis: "nema resursa!" });
	});
	server.listen(port, () => {
		console.log(`Server pokrenut na portu: ${port}`);
	});
}

function pripremiPutanjePocetna() {
	let fetchUpravitelj = new FetchUpravitelj(konf.dajKonf().jwtTajniKljuc);
	server.get(
		"/detaljiSerije",
		fetchUpravitelj.serijaDetalji.bind(fetchUpravitelj)
	);
}

function pripremiPutanjeAutentifikacija() {
	let fetchUpravitelj = new FetchUpravitelj(konf.dajKonf().jwtTajniKljuc);
	let auth = new Autentifikacija();
	server.get("/dohvatiKorisnika", fetchUpravitelj.dohvatiKorisnika);
	server.post("/prijava", fetchUpravitelj.prijava);
	server.get("/prijava", fetchUpravitelj.prijava);
	server.post("/registracija", fetchUpravitelj.registracija);
	server.get("/odjava", fetchUpravitelj.odjaviKorisnika);
	server.get("/getJWT", fetchUpravitelj.getJWT);
	server.post("/kreirajTajniKljuc", fetchUpravitelj.kreirajTajniKljucFU);
	server.post("/provjeriTOTP", fetchUpravitelj.protvrdiTOTP);
}

function pripremiPutanjeGit() {
	server.get("/odjava/auth", (zahtjev, odgovor) => {
		zahtjev.logOut((err) => {
			if (err) {
				console.log(err);
				odgovor.status(500).send("Greška prilikom odjave.");
			} else {
				odgovor.send(
					"Nema prijavljenih korisnika (github) i sesija uspješno uništena!"
				);
				console.log("Nema prijavljenih korisnika (github)!");
			}
		});
	});

	server.get("/auth/github", passport.authenticate("github"));

	server.get(
		"/auth/github/callback",
		passport.authenticate("github", { failureRedirect: "/prijava" }),
		(zahtjev, odgovor) => {
			console.log("Sesija nakon prijave: ", zahtjev.session);
			zahtjev.session.nacinPrijave = "github";
			console.log("Uspješna prijava putem Github-a");
			odgovor.redirect("/pocetna");
		}
	);

	server.get("/provjeriAutorizaciju", (zahtjev, odgovor) => {
		if (zahtjev.user) {
			odgovor.json({
				gitAutoriziran: true,
				nacinPrijave: zahtjev.session.nacinPrijave || "nepoznato",
			});
		} else {
			odgovor.json({
				gitAutoriziran: false,
				nacinPrijave: "database" || "nepoznato",
			});
		}
	});
}

function pripremiPutanjuAngular() {
	server.get("*", (zahtjev, odgovor) => {
		console.log(zahtjev.user);
		odgovor.sendFile(
			path.join(putanja, "../angular/dist/zadaca_02/browser/index.html")
		);
	});
}
