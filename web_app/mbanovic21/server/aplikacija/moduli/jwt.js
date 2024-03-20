const jwt = require("jsonwebtoken");
const ds = require("fs/promises");
const { pretvoriJSONkonfig } = require("../../konfiguracija.js");
const path = require("path");
const putanja = path.join(__dirname, "../../rwa_mbanovic21_conf.csv");

async function ucitajKonfiguraciju() {
	try {
		let csvContent = await ds.readFile(putanja, { encoding: "utf-8" });
		let konf = pretvoriJSONkonfig(csvContent);
		return konf;
	} catch (error) {
		console.error("Greška prilikom čitanja konfiguracijske datoteke:", error);
		throw error;
	}
}

exports.kreirajToken = async function (korisnik) {
	try {
		let konf = await ucitajKonfiguraciju();
		if (!konf.jwtValjanost || !konf.jwtTajniKljuc) {
			throw new Error("Nisu definirani jwtValjanost ili jwtTajniKljuc");
		}
		let token = jwt.sign({ korime: korisnik.korime }, konf.jwtTajniKljuc, {
			expiresIn: konf.jwtValjanost,
		});
		console.log("JWT:", token);
		return token;
	} catch (error) {
		console.error("Greška prilikom kreiranja tokena:", error);
	}
};

exports.provjeriToken = function (zahtjev, tajniKljucJWT) {
	console.log("Provjera tokena: " + zahtjev.headers.authorization);
	if (zahtjev.headers.authorization != null) {
		console.log(zahtjev.headers.authorization);
		let token = zahtjev.headers.authorization;
		try {
			let podaci = jwt.verify(token, tajniKljucJWT);
			console.log("JWT podaci: " + podaci);
			return true;
		} catch (e) {
			console.log(e);
			return false;
		}
	}
	return false;
};

exports.ispisiDijelove = function (token) {
	let dijelovi = token.split(".");
	let zaglavlje = dekodirajBase64(dijelovi[0]);
	console.log(zaglavlje);
	let tijelo = dekodirajBase64(dijelovi[1]);
	console.log(tijelo);
	let potpis = dekodirajBase64(dijelovi[2]);
	console.log(potpis);
};

exports.dajTijelo = function (token) {
	let dijelovi = token.split(".");
	return JSON.parse(dekodirajBase64(dijelovi[1]));
};

function dekodirajBase64(data) {
	let buff = new Buffer(data, "base64");
	return buff.toString("ascii");
}
