const kodovi = require("./moduli/kodovi.js");
const portRest = 12390;
const totp = require("./moduli/totp.js");

class Autentifikacija {
	async dodajKorisnika(korisnik) {
		let tijelo = {
			ime: korisnik.ime,
			prezime: korisnik.prezime,
			lozinka: kodovi.kreirajSHA256(korisnik.lozinka, "moja sol"),
			email: korisnik.email,
			korime: korisnik.korime,
			datum_rođenja: korisnik.datum_rođenja,
		};

		console.log(korisnik);
		console.log("Korisnik.lozinka", korisnik.lozinka, "-->", tijelo.lozinka);
		console.log(korisnik.datum_rođenja);

		/*let aktivacijskiKod = kodovi.dajNasumceBroj(10000, 99999);
		tijelo["aktivacijskiKod"] = aktivacijskiKod;*/

		let zaglavlje = new Headers();
		zaglavlje.set("Content-Type", "application/json");

		let parametri = {
			method: "POST",
			body: JSON.stringify(tijelo),
			headers: zaglavlje,
		};
		let odgovor = await fetch(
			"http://localhost:" + portRest + "/baza/korisnici",
			parametri
		);

		if (odgovor.status == 200) {
			console.log("Korisnik ubačen na servisu");
			/*let mailPoruka =
				"Korisničko ime:" + korisnik.korime + "lozinka" + korisnik.lozinka;

			let poruka = await mail.posaljiMail(
				"mbanovic21@student.foi.hr",
				korisnik.email,
				"Aktivacijski kod",
				mailPoruka
			);*/
			return true;
		} else {
			console.log(odgovor.status);
			console.log(await odgovor.text());
			return false;
		}
	}

	async prijaviKorisnika(korime, lozinka) {
		console.log("Prijava klijent: " + korime + " " + lozinka);

		lozinka = kodovi.kreirajSHA256(lozinka, "moja sol");
		console.log("lozinka: ", lozinka);
		let tijelo = {
			korime: korime,
			lozinka: lozinka,
		};

		console.log(tijelo.lozinka);

		let zaglavlje = new Headers();
		zaglavlje.set("Content-Type", "application/json");

		let parametri = {
			method: "POST",
			body: JSON.stringify(tijelo),
			headers: zaglavlje,
		};

		let odgovor = await fetch(
			"http://localhost:" + portRest + "/baza/korisnici/" + korime + "/prijava",
			parametri
		);
		//console.log("Odgovor:", odgovor);

		if (odgovor.status == 200) {
			return await odgovor.text();
		} else {
			return false;
		}
	}

	async dohvatiKorisnika() {
		let odgovor = await fetch(
			"http://localhost:" + portRest + "/baza/korisnici/"
		);
		let podaci = await odgovor.text();
		podaci = JSON.parse(podaci);
		console.log(podaci);
		for (let p of podaci) {
			if (p.prijava == "da") {
				let odgovor2 = await fetch(
					"http://localhost:" + portRest + "/baza/korisnici/" + p.korime
				);
				let podaci2 = await odgovor2.text();
				podaci2 = JSON.parse(podaci2);
				return podaci2;
			}
		}
		console.log("Nema prijavljenih korisnika putem baze!");
		return false;
	}

	async odjaviKorisnika() {
		let odgovor = await fetch(
			"http://localhost:" + portRest + "/baza/korisnici/"
		);
		let podaci = await odgovor.text();
		podaci = JSON.parse(podaci);
		console.log("baza podaci: ", podaci);
		for (let p of podaci) {
			if (p.prijava == "da") {
				let parametri = {
					method: "PUT",
					body: JSON.stringify(p),
				};
				let odgovor2 = await fetch(
					"http://localhost:" +
						portRest +
						"/baza/korisnici/" +
						p.korime +
						"/odjava",
					parametri
				);
				let podaci2 = await odgovor2.text();
				podaci2 = JSON.parse(podaci2);
				console.log(podaci2);
				return podaci2;
			}
		}
		console.log("Nema prijavljenih korisnika putem baze!");
		return false;
	}
}

module.exports = Autentifikacija;
