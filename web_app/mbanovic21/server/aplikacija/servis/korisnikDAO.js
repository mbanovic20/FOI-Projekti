const Baza = require("./baza");

class KorisnikDAO {
	constructor() {
		console.log(
			require("path").resolve("../../RWA2023mbanovic21.sqlite")
		);
		this.baza = new Baza("../../RWA2023mbanovic21.sqlite");
	}

	dajSve = async function () {
		this.baza.spojiSeNaBazu();
		let sql = "SELECT * FROM Korisnik;";
		var podaci = await this.baza.izvrsiUpit(sql, []);
		this.baza.zatvoriVezu();
		return podaci;
	};

	daj = async function (korime) {
		this.baza.spojiSeNaBazu();
		let sql = "SELECT * FROM Korisnik WHERE korime=?;";
		var podaci = await this.baza.izvrsiUpit(sql, [korime]);
		this.baza.zatvoriVezu();
		if (podaci.length == 1) return podaci[0];
		else return null;
	};

	dodaj = async function (Korisnik) {
		console.log(Korisnik);
		let sql = `INSERT INTO korisnik (korime, lozinka, email, ime, prezime, datum_rođenja, aktivan, tipKorisnika_id) VALUES (?,?,?,?,?,?,?,?)`;
		let podaci = [
			Korisnik.korime,
			Korisnik.lozinka,
			Korisnik.email,
			Korisnik.ime,
			Korisnik.prezime,
			Korisnik.datum_rođenja,
			"da",
			2,
		];
		await this.baza.izvrsiUpit(sql, podaci);
		return true;
	};

	obrisi = async function (korime) {
		let sql = "DELETE FROM Korisnik WHERE korime=?";
		await this.baza.izvrsiUpit(sql, [korime]);
		return true;
	};

	azuriraj = async function (korime, Korisnik) {
		this.baza.spojiSeNaBazu();
		let sql = `UPDATE Korisnik SET ime=?, prezime=?, lozinka=?, datum_rođenja=? WHERE korime=?`;
		let podaci = [
			Korisnik.ime,
			Korisnik.prezime,
			Korisnik.lozinka,
			Korisnik.datum_rođenja,
			korime,
		];
		await this.baza.izvrsiUpit(sql, podaci);
		this.baza.zatvoriVezu();
		return true;
	};

	dodajTotp = async function (korime, tajniKljuc) {
		this.baza.spojiSeNaBazu();
		let sql = `UPDATE Korisnik SET tajniKljuc=? WHERE korime=?`;
		let podaci = [tajniKljuc, korime];
		await this.baza.izvrsiUpit(sql, podaci);
		this.baza.zatvoriVezu();
		return true;
	};

	azurirajPrijavu = async function (korime) {
		this.baza.spojiSeNaBazu();
		let sql = `UPDATE Korisnik SET prijava=? WHERE korime=?`;
		let podaci = ["da", korime];
		await this.baza.izvrsiUpit(sql, podaci);
		this.baza.zatvoriVezu();
		return true;
	};

	azurirajOdjavu = async function (korime) {
		this.baza.spojiSeNaBazu();
		let sql = `UPDATE Korisnik SET prijava=? WHERE korime=?`;
		let podaci = ["ne", korime];
		await this.baza.izvrsiUpit(sql, podaci);
		this.baza.zatvoriVezu();
		return true;
	};
}

module.exports = KorisnikDAO;
