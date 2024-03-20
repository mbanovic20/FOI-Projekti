export interface IKorisnik {
  id: Number;
  korime: String;
  lozinka: String;
  email: String;
  ime: String;
  prezime: String;
  adresa: String;
  totp: String;
  datum_rođenja: String;
  aktivan: String;
  prijava: String;
  aktivacijskiKod: String;
  tajniKljuc: String;
  tajniKljucPrikazan: String;
  tipKorisnika_id: Number;
}

export interface IPrijava {
  korime: String;
  lozinka: String;
  totp: String;
  tajniKljuc: String;
  tipKorisnika_id: Number;
}
