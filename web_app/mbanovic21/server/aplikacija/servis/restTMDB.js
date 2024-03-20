const TMDBklijent = require("./klijentTMDB.js");

class RestTMDB {
	constructor(api_kljuc) {
		this.tmdbKlijent = new TMDBklijent(api_kljuc);
		RestTMDB.instance = this;
		console.log(api_kljuc);

		//this.tmdbKlijent.dohvatiSeriju(500).then(console.log).catch(console.log);
	}

	getSerijaDetalji(zahtjev, odgovor) {
		console.log(this);
		odgovor.type("application/json");
		let id = zahtjev.query.id;

		if (id == null || id == "") {
			odgovor.status(417);
			odgovor.send({ greska: "neocekivani podaci" });
			return;
		}

		this.tmdbKlijent
			.dohvatiSeriju(id)
			.then((serija) => {
				//console.log(serije);
				odgovor.send(serija);
			})
			.catch((greska) => {
				odgovor.json(greska);
			});
	}

	getSerije(zahtjev, odgovor) {
		console.log(this);
		odgovor.type("application/json");
		let stranica = zahtjev.query.stranica;
		let trazi = zahtjev.query.trazi;
		console.log(stranica);
		console.log(trazi);

		if (!stranica || !trazi) {
			odgovor.status(417);
			odgovor.send({ greska: "neocekivani podaci" });
			return;
		}

		this.tmdbKlijent
			.pretraziSerijePoNazivu(trazi, stranica)
			.then((serije) => {
				//console.log(serije);
				odgovor.send(serije);
			})
			.catch((greska) => {
				odgovor.json(greska);
			});
	}
}

module.exports = RestTMDB;
