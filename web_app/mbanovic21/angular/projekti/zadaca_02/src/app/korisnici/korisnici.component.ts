import { Component, OnInit } from '@angular/core';
import { HttpService } from '../servisi/http.service';
import { IKorisnik } from '../servisi/IKorisnik';

@Component({
  selector: 'app-korisnici',
  templateUrl: './korisnici.component.html',
  styleUrls: ['./korisnici.component.scss'],
})
export class KorisniciComponent implements OnInit {
  korisnici: IKorisnik[] = [];

  constructor(private httpService: HttpService) {}

  ngOnInit(): void {
    this.ucitajKorisnike();
  }

  ucitajKorisnike(): void {
    this.httpService.dohvatiSveKorisnike().subscribe(
      (data) => {
        this.korisnici = data;
      },
      (error) => {
        console.error('Došlo je do greške!', error);
      }
    );
  }

  obrisiKorisnika(korime: String): void {
    if (korime.toLowerCase() === 'admin') {
      alert('Korisnik "admin" ne može biti obrisan!');
      return;
    }

    this.httpService.obrisiKorisnika(korime as string).subscribe(
      (data) => {
        console.log(`Korisnik ${korime} je uspješno obrisan.`);
        this.ucitajKorisnike(); // Ponovno učitajte korisnike nakon brisanja
      },
      (error) => {
        console.error('Došlo je do greške prilikom brisanja korisnika!', error);
      }
    );
  }
}
