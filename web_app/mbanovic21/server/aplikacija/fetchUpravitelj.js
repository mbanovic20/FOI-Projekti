const SerijePretrazivanje = require("./serijePretrazivanje.js");
const Autentifikacija = require("./autentifikacija.js");
const jwt = require("./moduli/jwt.js");
const { kreirajTajniKljuc } = require("./moduli/totp.js");
const totp = require("./moduli/totp.js");

let auth = new Autentifikacija();

const portRest = 12390;

class FetchUpravitelj {
	constructor(tajniKljucJWT) {
		this.sp = new SerijePretrazivanje();
		this.tajniKljucJWT = tajniKljucJWT;
	}

	serijePretrazivanje = async function (zahtjev, odgovor) {
		console.log("Fetch");
		let str = zahtjev.query.str;
		let filter = zahtjev.query.filter;
		console.log(zahtjev.query);
		odgovor.json(await this.sp.dohvatiSerije(str, filter));
	};

	serijaDetalji = async function (zahtjev, odgovor) {
		console.log("Fetch");
		let id = zahtjev.query.id;
		console.log(zahtjev.query.id);
		odgovor.json(await this.sp.dohvatiSeriju(id));
	};

	registracija = async function (zahtjev, odgovor) {
		console.log(zahtjev.body);
		let greska = "";
		let uspjeh = await auth.dodajKorisnika(zahtjev.body);
		if (uspjeh) {
			console.log("Uspješno dodan korisnik!");
			odgovor.redirect("/prijava");
			return;
		} else {
			return (greska = "Dodavanje nije uspjelo provjerite podatke!");
		}
	};

	prijava = async function (zahtjev, odgovor) {
		if (zahtjev.method == "POST") {
			var korime = zahtjev.body.korime;
			var lozinka = zahtjev.body.lozinka;
			try {
				var korisnik = await auth.prijaviKorisnika(korime, lozinka);
				var proba = JSON.parse(korisnik);
				console.log(proba);

				if (proba.aktivan == "da") {
					console.log("Uspješna prijava");

					var token = await jwt.kreirajToken(korisnik);

					zahtjev.session.jwt = token;
					zahtjev.session.korisnik = proba;
					zahtjev.session.korime = proba.korime;
					zahtjev.session.tipKorisnika_id = proba.tipKorisnika_id;
					(zahtjev.session.autoriziran = true),
						(zahtjev.session.nacinPrijave = "database");
					zahtjev.session.tajniKljuc = proba.tajniKljuc;

					odgovor.set("Authorization", "Bearer " + token);
					odgovor.status(201).json({
						success: true,
						message: "Uspješna prijava",
						token: token,
						korisnik: zahtjev.session.korisnik,
						korime: zahtjev.session.korime,
						autoriziran: zahtjev.session.autoriziran,
						nacinPrijave: zahtjev.session.nacinPrijave,
						tipKorisnika_id: zahtjev.session.tipKorisnika_id,
						tajniKljuc: zahtjev.session.tajniKljuc,
					});
				} else {
					console.log("Zabranjen pristup!");
					odgovor.status(401).json({
						success: false,
						message: "Zabranjen pristup",
					});
				}
			} catch (e) {
				console.log("Greška: ", e);
				odgovor.status(400).json({
					success: false,
					message: "Interna greška servera",
				});
			}
		} /*else if (zahtjev.method == "GET") {
			try {
				if (zahtjev.session && zahtjev.session.korisnik) {
					console.log("Sesija postoji");

					// Ovdje možete dodati dodatne provjere ako su potrebne prije odobravanja pristupa.

					odgovor.status(200).json({
						success: true,
						message: "Pristup odobren",
						korisnik:
							zahtjev.session.korisnik.ime +
							" " +
							zahtjev.session.korisnik.prezime,
						korime: zahtjev.session.korisnik.korime,
					});
				} else {
					console.log("Zabranjen pristup!");
					odgovor.status(401).json({
						success: false,
						message: "Zabranjen pristup",
					});
				}
			} catch (e) {
				console.log("Greška: ", e);
				odgovor.status(400).json({
					success: false,
					message: "Interna greška servera",
				});
			}
		}*/
	};

	async kreirajTajniKljucFU(zahtjev, odgovor) {
		try {
			var korime = zahtjev.body.korime;
			console.log(korime);
			var tajniKljuc = totp.kreirajTajniKljuc(korime);

			let tijelo = {
				tajniKljuc: tajniKljuc,
			};

			console.log(tijelo);

			let zaglavlje = new Headers();
			zaglavlje.set("Content-Type", "application/json");

			let parametri = {
				method: "PUT",
				body: JSON.stringify(tijelo),
				headers: zaglavlje,
			};

			let url =
				"http://localhost:12390/baza/korisnici/" + korime + "/dodajTajniKljuc";

			console.log(url, parametri);
			let odgovorServera = await fetch(url, parametri);

			if (odgovorServera.status == 200) {
				console.log("Korisniku je dodan tajni ključ na servisu");
				let podaciOdgovora = await odgovorServera.json();
				odgovor.status(200).json({
					poruka:
						"Korisničko ime koje je poslano: " + korime + ", tajni ključ: ",
					tajniKljuc,
				});
			} else {
				console.log("Greška pri ažuriranju:", odgovorServera.status);
				console.log(await odgovorServera.text());
				odgovor
					.status(odgovorServera.status)
					.json({ poruka: "Došlo je do greške na serveru" });
			}
		} catch (error) {
			console.error("Došlo je do greške:", error);
			odgovor.status(500).json({ poruka: "Došlo je do greške na serveru" });
		}
	}

	async protvrdiTOTP(zahtjev, odgovor) {
		var uneseniKod = zahtjev.body.totp;
		var tajniKljuc = zahtjev.body.tajniKljuc;

		console.log("Totp zahtjev: ", totp);
		console.log("Tajni kljuc zahtjev: ", tajniKljuc);

		var provjeraKljuca = totp.provjeriTOTP(uneseniKod, tajniKljuc);

		if (provjeraKljuca) {
			odgovor.status(200).json({ poruka: "TOTP provjera ispravna!" });
		} else {
			odgovor.status(401).json({ poruka: "TOTP provjera neispravana!" });
		}
	}

	odjaviKorisnika = async function (zahtjev, odgovor) {
		odgovor.json(await auth.odjaviKorisnika());
	};

	dohvatiKorisnika = async function (zahtjev, odgovor) {
		odgovor.json(await auth.dohvatiKorisnika());
	};

	getJWT = async function (zahtjev, odgovor) {
		odgovor.type("json");
		if (zahtjev.session.jwt != null) {
			let k = { korime: jwt.dajTijelo(zahtjev.session.jwt).korime };
			let noviToken = jwt.kreirajToken(k);
			odgovor.send({ ok: noviToken });
			return;
		}
		odgovor.status(404);
		odgovor.send({ greska: "nemam token!" });
	};
}
module.exports = FetchUpravitelj;
