const totp = require("totp-generator");
const kodovi = require("./kodovi.js");
const base32 = require("base32-encoding");

exports.kreirajTajniKljuc = function (korime) {
	let tekst = korime + new Date() + kodovi.dajNasumceBroj(10000000, 90000000);
	let hash = kodovi.kreirajSHA256(tekst);
	let tajniKljuc = base32.stringify(hash, "ABCDEFGHIJKLMNOPRSTQRYWXZ234567");
	return tajniKljuc.toUpperCase();
};

exports.provjeriTOTP = function (uneseniKod, tajniKljuc) {
	const kod = totp(tajniKljuc, {
		digits: 6,
		algorithm: "SHA-512",
		period: 60,
	});
	console.log("Kod server:", kod);
	console.log("Uneseni kod ", uneseniKod, "==", "kod server ", kod);
	if (uneseniKod == kod) return true;
	return false;
};
