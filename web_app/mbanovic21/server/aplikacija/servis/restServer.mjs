import express from "express";
import { Konfiguracija } from "../../konfiguracija.js";
import RestTMDB from "./restTMDB.js";
import restKorisnik from "./restKorisnik.js";
import path from "path";
//import portovi from "/var/www/RWA/2023/portovi.js";
//const port = portovi.mbanovic21;
const port = 12390;
const server = express();
import cors from "cors";

//putanja
import { fileURLToPath } from "url";
import { dirname } from "path";
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
	});

function pokreniServer() {
	server.use(express.urlencoded({ extended: true }));
	server.use(cors());
	server.use(express.json());

	server.get("/konfiguracija.js", (zahtjev, odgovor) => {
		odgovor.type("application/javascript");
	});

	pripremiPutanjeKorisnik();
	//pripremiPutanjeKorisnikAktivacija();
	//pripremiPutanjeSerija();
	pripremiPutanjeTMDB();

	server.use((zahtjev, odgovor) => {
		odgovor.status(404);
		odgovor.json({ opis: "nema resursa!" });
	});
	server.listen(port, () => {
		console.log(`Server pokrenut na portu: ${port}`);
	});
}

function pripremiPutanjeKorisnik() {
	server.get("/baza/korisnici", restKorisnik.getKorisnici);
	server.post("/baza/korisnici", restKorisnik.postKorisnici);
	server.put("/baza/korisnici", restKorisnik.putKorisnici);
	server.delete("/baza/korisnici", restKorisnik.deleteKorisnici);

	server.get("/baza/korisnici/:korime", restKorisnik.getKorisnik);
	server.post("/baza/korisnici/:korime", restKorisnik.postKorisnik);
	server.put("/baza/korisnici/:korime", restKorisnik.putKorisnik);
	server.delete("/baza/korisnici/:korime", restKorisnik.deleteKorisnik);

	server.get(
		"/baza/korisnici/:korime/prijava",
		restKorisnik.getKorisnikAktivacija
	);
	server.post(
		"/baza/korisnici/:korime/prijava",
		restKorisnik.getKorisnikPrijava
	);
	server.put("/baza/korisnici/:korime/prijava", restKorisnik.putKorisnici);
	server.delete("/baza/korisnici/:korime/prijava", restKorisnik.deleteKorisnik);

	server.put("/baza/korisnici/:korime/odjava", restKorisnik.putKorisnikOdjava);

	server.put(
		"/baza/korisnici/:korime/dodajTajniKljuc",
		restKorisnik.putKorisnikTajniKljuc
	);
}

function pripremiPutanjeKorisnikAktivacija() {
	server.get(
		"/baza/korisnici/:korime/aktivacija",
		restKorisnik.getKorisnikAktivacija
	);
	server.post("/baza/korisnici/:korime/aktivacija", restKorisnik.postKorisnik);
	server.put(
		"/baza/korisnici/:korime/aktivacija",
		restKorisnik.putKorisnikAktivacija
	);
	server.delete(
		"/baza/korisnici/:korime/aktivacija",
		restKorisnik.deleteKorisnik
	);
}

function pripremiPutanjeSerija() {
	server.get("/baza/serije/:id", restFilm.getFilm);
	server.post("/baza/serije/:id", restFilm.postFilm);
	server.put("/baza/serije/:id", restFilm.putFilm);
	server.delete("/baza/serije/:id", restFilm.deleteFilm);

	server.get("/baza/serije", restFilm.getFilmovi);
	server.post("/baza/serije", restFilm.postFilmovi);
}

function pripremiPutanjeTMDB() {
	let restTMDB = new RestTMDB(konf.dajKonf()["tmdbApiKeyV3"]);
	server.get("/api/tmdb/serije", restTMDB.getSerije.bind(restTMDB));
	server.get("/api/tmdb/serija", restTMDB.getSerijaDetalji.bind(restTMDB));
}
