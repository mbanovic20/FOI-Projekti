const KorisnikDAO = require("./korisnikDAO");
const kodovi = require("../moduli/kodovi.js");

exports.getKorisnici = function (zahtjev, odgovor) {
	odgovor.type("application/json");
	let kdao = new KorisnikDAO();
	kdao.dajSve().then((korisnici) => {
		console.log(korisnici);
		odgovor.status(200);
		odgovor.send(JSON.stringify(korisnici));
	});
};

exports.postKorisnici = function (zahtjev, odgovor) {
	odgovor.type("application/json");
	let podaci = zahtjev.body;
	let kdao = new KorisnikDAO();
	kdao.dodaj(podaci).then((poruka) => {
		if (poruka == true) {
			odgovor.status(201);
			odgovor.send(JSON.stringify(poruka));
		} else {
			odgovor.status(400);
			let poruka = { greska: "Podaci su krivo uneseni" };
			odgovor.send(JSON.stringify(poruka));
		}
	});
};

exports.deleteKorisnici = function (zahtjev, odgovor) {
	odgovor.type("application/json");
	odgovor.status(501);
	let poruka = { greska: "metoda nije implementirana" };
	odgovor.send(JSON.stringify(poruka));
};

exports.putKorisnici = function (zahtjev, odgovor) {
	odgovor.type("application/json");
	odgovor.status(501);
	let poruka = { greska: "metoda nije implementirana" };
	odgovor.send(JSON.stringify(poruka));
};

exports.getKorisnik = function (zahtjev, odgovor) {
	odgovor.type("application/json");
	let kdao = new KorisnikDAO();
	let korime = zahtjev.params.korime;
	kdao.daj(korime).then((Korisnik) => {
		console.log(Korisnik);
		odgovor.send(JSON.stringify(Korisnik));
	});
};

exports.getKorisnikPrijava = function (zahtjev, odgovor) {
	odgovor.type("application/json");
	let kdao = new KorisnikDAO();
	let korime = zahtjev.params.korime;
	kdao.daj(korime).then((korisnik) => {
		console.log(
			"Korisnik korime: ",
			korisnik.korime,
			"==",
			zahtjev.body.korime
		);

		console.log(
			"Korisnik lozinka: ",
			korisnik.lozinka,
			"==",
			zahtjev.body.lozinka
		);
		if (
			korisnik != null &&
			korisnik.korime == zahtjev.body.korime &&
			korisnik.lozinka == zahtjev.body.lozinka
		) {
			odgovor.send(JSON.stringify(korisnik));
			kdao.azurirajPrijavu(korime);
		} else {
			odgovor.status(401);
			odgovor.send(JSON.stringify({ greska: "Krivi podaci!" }));
		}
	});
};

exports.postKorisnik = function (zahtjev, odgovor) {
	odgovor.type("application/json");
	odgovor.status(405);
	let poruka = { greska: "zabranjeno" };
	odgovor.send(JSON.stringify(poruka));
};

exports.putKorisnikTajniKljuc = function (zahtjev, odgovor) {
	odgovor.type("application/json");
	let korime = zahtjev.params.korime;
	let tajniKljuc = zahtjev.body.tajniKljuc;
	let kdao = new KorisnikDAO();
	kdao.dodajTotp(korime, tajniKljuc).then((poruka) => {
		odgovor.send(JSON.stringify(poruka));
	});
	console.log("Korime", korime, "tajni kljuc", tajniKljuc);
	odgovor.status(200);
	console.log(odgovor.status);
};

exports.putKorisnik = function (zahtjev, odgovor) {
	odgovor.type("application/json");
	let korime = zahtjev.params.korime;
	let podaci = zahtjev.body;
	let kdao = new KorisnikDAO();
	kdao.azuriraj(korime, podaci).then((poruka) => {
		odgovor.send(JSON.stringify(poruka));
	});
};

exports.deleteKorisnik = function (zahtjev, odgovor) {
	odgovor.type("application/json");
	let kdao = new KorisnikDAO();
	let korime = zahtjev.params.korime;
	kdao.obrisi(korime).then((Korisnik) => {
		console.log(Korisnik);
		odgovor.send(JSON.stringify(Korisnik));
	});
};

exports.getKorisnikAktivacija = function (zahtjev, odgovor) {
	odgovor.type("application/json");
	odgovor.status(501);
	let poruka = { greska: "metoda nije implementirana" };
	odgovor.send(JSON.stringify(poruka));
};

exports.putKorisnikAktivacija = function (zahtjev, odgovor) {
	let korime = zahtjev.params.korime;
	console.log(korime);
	let kdao = new KorisnikDAO();
	let korisnik = kdao.daj(korime);
	console.log(korisnik);
	korisnik.aktivan = "da";
	kdao.azurirajAktivnost(korime, korisnik);
	odgovor.redirect("/prijava");
};

exports.putKorisnikOdjava = function (zahtjev, odgovor) {
	odgovor.type("application/json");
	let kdao = new KorisnikDAO();
	let korime = zahtjev.params.korime;
	console.log("korime.zahtjev =", korime);
	kdao.daj(korime).then((korisnik) => {
		if (korisnik != null) {
			kdao.azurirajOdjavu(korime);
			console.log("Korisnik uspješno odjavljen!");
			odgovor.send(JSON.stringify(korisnik));
		} else {
			odgovor.status(401);
			odgovor.send(JSON.stringify({ greska: "Krivi podaci!" }));
		}
	});
};
