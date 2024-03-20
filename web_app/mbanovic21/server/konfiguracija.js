const ds = require("fs/promises");
const provjeriGreske = require("./provjeriGreske.js");

class Konfiguracija {
	constructor() {
		this.konf = {};
	}
	dajKonf() {
		return this.konf;
	}

	async ucitajKonfiguraciju() {
		//console.log(this.konf);
		let podaci = await ds.readFile(process.argv[2], "UTF-8");
		this.konf = pretvoriJSONkonfig(podaci);
		console.log(this.konf);
		const greske = await provjeriGreske(this.konf);
		if (greske.length === 0) {
			console.log("Konfiguracijski podaci su ispravni. Pokrećem server...");
		} else {
			console.error("Konfiguracija nije ispravna. Server prestaje s radom...");
			greske.forEach((greska) => console.error(greska));
			process.exit(1);
		}
	}

	dohvatiJwtValjanost(podaci) {
		var nizPodataka = podaci.split("\n");
		for (let podatak of nizPodataka) {
			var podatakNiz = podatak.split("=");
			var naziv = podatakNiz[0];
			var vrijednost = podatakNiz[1];
			if (naziv == "jwtValjanost") {
				return vrijednost;
			}
		}
	}
}

function pretvoriJSONkonfig(podaci) {
	//console.log("Ispis konfiguracijskih podataka: \n" + podaci);
	let konf = {};
	var nizPodataka = podaci.split("\n");
	for (let podatak of nizPodataka) {
		var podatakNiz = podatak.split(":");
		var naziv = podatakNiz[0];
		var vrijednost = podatakNiz[1];
		konf[naziv] = vrijednost;
	}
	return konf;
}

module.exports = {
	Konfiguracija,
	pretvoriJSONkonfig,
};
