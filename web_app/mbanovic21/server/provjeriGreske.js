async function provjeriGreske(konf) {
	const greske = [];

	await provjeriJwtValjanost(konf, greske);
	await provjeriJwtTajniKljuc(konf, greske);
	await provjeriTajniKljucSesija(konf, greske);
	await provjeriAppStranicenje(konf, greske);
	await provjeriTmdbApikeyV3(konf, greske);
	await provjeriTmdbApikeyV4(konf, greske);

	return greske;
}

async function provjeriJwtValjanost(konf, greske) {
	konf.jwtValjanost = parseInt(konf.jwtValjanost);
	if (!konf.jwtValjanost) {
		greske.push(
			"Greška: Konfiguracijski podatak 'jwtValjanost' nije definiran ili nije broj. ==> Prikaz greške: " +
				konf.jwtValjanost
		);
	} else if (typeof konf.jwtValjanost !== "number") {
		greske.push(
			"Greška: Konfiguracijski podatak 'jwtValjanost' nije broj. ==> Prikaz greške: type_" +
				typeof konf.jwtValjanost
		);
	} else if (konf.jwtValjanost < 15) {
		greske.push(
			"Greška: Konfiguracijski podatak 'jwtValjanost' manji je od 15. ==> Prikaz greške: " +
				konf.jwtValjanost
		);
	} else if (konf.jwtValjanost > 3600) {
		greske.push(
			"Greška: Konfiguracijski podatak 'jwtValjanost' veći je od 3600. ==> Prikaz greške: " +
				konf.jwtValjanost
		);
	}
}

async function provjeriJwtTajniKljuc(konf, greske) {
	if (!konf.jwtTajniKljuc) {
		greske.push(
			"Greška: Konfiguracijski podatak 'jwtTajniKljuc' nije definiran. ==> Prikaz greške: " +
				konf.jwtTajniKljuc
		);
	} else if (typeof konf.jwtTajniKljuc !== "string") {
		greske.push(
			"Greška: Konfiguracijski podatak 'jwtTajniKljuc' mora biti string. ==> Prikaz greške: type_" +
				typeof konf.jwtTajniKljuc
		);
	} else if (konf.jwtTajniKljuc.length < 50) {
		greske.push(
			"Greška: Konfiguracijski podatak 'jwtTajniKljuc' mora sadržavati najmanje 50 znakova. ==> Vaš broj znakova iznosi: " +
				konf.jwtTajniKljuc.length
		);
	} else if (konf.jwtTajniKljuc.length > 100) {
		greske.push(
			"Greška: Konfiguracijski podatak 'jwtTajniKljuc' ne smije biti duži od 100 znakova. ==> Vaš broj znakova iznosi: " +
				konf.jwtTajniKljuc.length
		);
	} else if (!/^[a-zA-Z0-9]*$/.test(konf.jwtTajniKljuc)) {
		greske.push(
			"Greška: Konfiguracijski podatak 'jwtTajniKljuc' smije sadržavati samo velika i mala slova te brojke. ==> Prikaz greške: " +
				konf.jwtTajniKljuc
		);
	}
}

async function provjeriTajniKljucSesija(konf, greske) {
	if (!konf.tajniKljucSesija) {
		greske.push(
			"Greška: Konfiguracijski podatak 'tajniKljucSesija' nije definiran. ==> Prikaz greške: " +
				konf.tajniKljucSesija
		);
	} else if (typeof konf.tajniKljucSesija !== "string") {
		greske.push(
			"Greška: Konfiguracijski podatak 'tajniKljucSesija' mora biti string. ==> Prikaz greške: type_" +
				typeof konf.tajniKljucSesija
		);
	} else if (konf.tajniKljucSesija.length < 50) {
		greske.push(
			"Greška: Konfiguracijski podatak 'tajniKljucSesija' mora sadržavati najmanje 50 znakova. ==> Vaš broj znakova iznosi: " +
				konf.tajniKljucSesija.length
		);
	} else if (konf.tajniKljucSesija.length > 100) {
		greske.push(
			"Greška: Konfiguracijski podatak 'tajniKljucSesija' ne smije biti duži od 100 znakova. ==> Vaš broj znakova iznosi: " +
				konf.tajniKljucSesija.length
		);
	} else if (!/^[a-zA-Z0-9]*$/.test(konf.tajniKljucSesija)) {
		greske.push(
			"Greška: Konfiguracijski podatak 'tajniKljucSesija' smije sadržavati samo slova (velika i mala) i brojke. ==> Prikaz greške: " +
				konf.tajniKljucSesija
		);
	}
}

async function provjeriAppStranicenje(konf, greske) {
	konf.appStranicenje = parseInt(konf.appStranicenje);
	if (!konf.appStranicenje) {
		greske.push(
			"Greška: Konfiguracijski podatak 'appStranicenje' nije definiran. ==> Prikaz greške: " +
				konf.appStranicenje
		);
	} else if (typeof konf.appStranicenje !== "number") {
		greske.push(
			"Greška: Konfiguracijski podatak 'appStranicenje' mora biti broj. ==> Prikaz greške: type_" +
				typeof konf.appStranicenje
		);
	} else if (konf.appStranicenje < 5) {
		greske.push(
			"Greška: Konfiguracijski podatak 'appStranicenje' mora biti najmanje 5. ==> Prikaz greške: " +
				konf.appStranicenje
		);
	} else if (konf.appStranicenje > 100) {
		greske.push(
			"Greška: Konfiguracijski podatak 'appStranicenje' ne smije biti veći od 100. ==> Prikaz greške: " +
				konf.appStranicenje
		);
	}
}

async function provjeriTmdbApikeyV3(konf, greske) {
	if (!konf.tmdbApiKeyV3 || !/^\S+$/.test(konf.tmdbApiKeyV3)) {
		greske.push(
			"Greška: Konfiguracijski podatak 'tmdb.apikey.v3' nedostaje, je prazan ili sadrži razmake."
		);
	}
}

async function provjeriTmdbApikeyV4(konf, greske) {
	if (!konf.tmdbApiKeyV4 || !/^\S+$/.test(konf.tmdbApiKeyV4)) {
		greske.push(
			"Greška: Konfiguracijski podatak 'tmdb.apikey.v4' nedostaje, je prazan ili sadrži razmake."
		);
	}
}

module.exports = provjeriGreske;
