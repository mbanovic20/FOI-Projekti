import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { IKorisnik, IPrijava } from './IKorisnik';

@Injectable({
  providedIn: 'root',
})
export class HttpService {
  constructor(private http: HttpClient) {}

  async dajSerijaDetalji(idSerije: string) {
    const headers = new HttpHeaders({ Accept: 'application/json' });
    const response = await this.http
      .get<any>(`/detaljiSerije?id=${idSerije}`, { headers: headers })
      .toPromise();
    return response;
  }

  probareCAPTHA(token: any) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(
      'https://www.google.com/recaptcha/api/siteverify',
      {
        secret: environment.recaptcha.siteKey,
        response: token,
      },
      { headers: headers }
    );
  }

  provjeriKorisnika(korisnik: IPrijava): Observable<IPrijava> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<IPrijava>(
      environment.appServis + '/prijava',
      korisnik,
      { headers: headers }
    );
  }

  dohvatiKorisnikaPoKorime(korime: string): Observable<any> {
    return this.http.get<any>(
      environment.restServis + '/baza/korisnici/' + korime
    );
  }

  azurirajKorisnika(korisnik: IKorisnik): Observable<IKorisnik> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<IKorisnik>(
      environment.restServis + '/baza/korisnici/' + korisnik.korime,
      korisnik
    );
  }

  obrisiKorisnika(korime: string): Observable<any> {
    return this.http.delete<any>(
      environment.restServis + '/baza/korisnici/' + korime
    );
  }

  dodajKorisnika(korisnik: IKorisnik): Observable<IKorisnik> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.http.post<IKorisnik>(
      environment.appServis + '/registracija',
      korisnik
    );
  }

  provjeriAutorizaciju(): Observable<any> {
    return this.http.get(environment.appServis + '/provjeriAutorizaciju');
  }

  dvorazinskaAutentifikacija(korisnik: IKorisnik): Observable<any> {
    return this.http.post(
      environment.appServis + '/dvorazinskaAutentikacija',
      korisnik
    );
  }

  dohvatiSveKorisnike(): Observable<any> {
    return this.http.get(environment.restServis + '/baza/korisnici');
  }

  kreirajTajniKljuc(korime: string): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    const tijeloZahtjeva = { korime: korime };
    return this.http.post<any>(
      environment.appServis + '/kreirajTajniKljuc',
      tijeloZahtjeva,
      { headers: headers }
    );
  }

  provjeriTOTPKod(totp: string, tajniKljuc: string) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    const tijeloZahtjeva = { totp: totp, tajniKljuc: tajniKljuc };
    return this.http.post<any>(
      environment.appServis + '/provjeriTOTP',
      tijeloZahtjeva,
      { headers: headers }
    );
  }
}
