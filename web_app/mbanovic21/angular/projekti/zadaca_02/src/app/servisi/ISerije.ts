export interface ISerije {
  tmbd_id: Number;
  naziv: String;
  opis: String;
  broj_sezona: Number;
  broj_epizoda: Number;
  popularnost: Number;
  slika: String;
  poveznica: String;
  sezone: ISezone[];
}

export interface ISezone {
  sezona_id: Number;
  broj_sezone: Number;
  broj_epizoda_u_sezoni: Number;
}
