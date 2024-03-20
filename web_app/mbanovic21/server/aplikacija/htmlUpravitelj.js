const ds = require("fs/promises");

class HtmlUpravitelj {
	constructor(tajniKljucJWT) {
		this.tajniKljucJWT = tajniKljucJWT;
		console.log(this.tajniKljucJWT);
	}

	pocetna = async function (zahtjev, odgovor) {
		let pocetna = await ucitajStranicu("pocetna");
		odgovor.send(pocetna);
	};

	registracija = async function (zahtjev, odgovor) {
		console.log(zahtjev.body);
		let greska = "";
		if (zahtjev.method == "POST") {
			let uspjeh = await this.auth.dodajKorisnika(zahtjev.body);
			if (uspjeh) {
				odgovor.redirect("/prijava");
				return;
			} else {
				greska = "Dodavanje nije uspjelo provjerite podatke!";
			}
		}

		let stranica = await ucitajStranicu("registracija", greska);
		odgovor.send(stranica);
	};

	serijePretrazivanje = async function (zahtjev, odgovor) {
		let stranica = await ucitajStranicu("pocetna");
		odgovor.send(stranica);
	};

	serijeDetalji = async function (zahtjev, odgovor) {
		let stranica = await ucitajStranicu("detaljiSerije");
		odgovor.send(stranica);
	};
}

module.exports = HtmlUpravitelj;

async function ucitajStranicu(nazivStranice, poruka = "") {
	let stranice = [ucitajHTML(nazivStranice), ucitajHTML("navigacija")];
	let [stranica, nav] = await Promise.all(stranice);
	stranica = stranica.replace("#navigacija#", nav);
	stranica = stranica.replace("#poruka#", poruka);
	return stranica;
}

function ucitajHTML(htmlStranica) {
	if (htmlStranica == "dokumentacija") {
		return ds.readFile(
			__dirname + "/dokumentacija/" + htmlStranica + ".html",
			"UTF-8"
		);
	}

	return ds.readFile(__dirname + "/html/" + htmlStranica + ".html", "UTF-8");
}
