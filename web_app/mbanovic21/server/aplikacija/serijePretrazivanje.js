const portRest = 12390;
const url = "http://localhost:" + portRest;
//const url = "http://spider.foi.hr" + portRest;
class SerijePretrazivanje {
	async dohvatiSerije(stranica, kljucnaRijec = "") {
		let putanja =
			url + "/api/tmdb/serije?stranica=" + stranica + "&trazi=" + kljucnaRijec;
		console.log(putanja);
		let odgovor = await fetch(putanja);
		let podaci = await odgovor.text();
		let serije = JSON.parse(podaci);
		console.log(serije);
		return serije;
	}
	async dohvatiSeriju(idSerije) {
		let putanja = url + "/api/tmdb/serija?id=" + idSerije;
		console.log(putanja);
		let odgovor = await fetch(putanja);
		let podaci = await odgovor.text();
		let serija = JSON.parse(podaci);
		console.log(serija);
		return serija;
	}
}

module.exports = SerijePretrazivanje;
