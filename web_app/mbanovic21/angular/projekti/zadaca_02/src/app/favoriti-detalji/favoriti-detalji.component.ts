import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpService } from '../servisi/http.service';

@Component({
  selector: 'app-favoriti-detalji',
  templateUrl: './favoriti-detalji.component.html',
  styleUrls: ['./favoriti-detalji.component.scss'],
})
export class FavoritiDetaljiComponent implements OnInit {
  sezone: any[] = [];
  autorizacijskaPoruka: string = '';

  constructor(private route: ActivatedRoute, private httpServis: HttpService) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      const idSerije = +params['id'];
      this.ucitajPodatkeSerije(idSerije);
    });
  }

  ucitajPodatkeSerije(idSerije: number) {
    this.httpServis.provjeriAutorizaciju().subscribe(
      (data) => {
        if (data.gitAutoriziran && data.nacinPrijave === 'github') {
          const sveSerije = JSON.parse(
            localStorage.getItem('favoriti') || '[]'
          );

          const odabranaSerija = sveSerije.find(
            (serija: any) => serija.id === idSerije
          );

          if (odabranaSerija && odabranaSerija.seasons) {
            this.sezone = odabranaSerija.seasons;
          } else {
            console.log(
              'Nisu pronađeni podaci o sezonama za seriju s ID-om:',
              idSerije
            );
          }
        } else {
          this.autorizacijskaPoruka =
            'Napravio sam da se favoriti i favoritiDetalji prikazuju samo prema uputama iz zadaće 2, odnosno da se čitaju iz local storage-a kad je korisnik prijavljen putem gitHub računa!';
        }
      },
      (error) => {
        console.error('Došlo je do pogreške pri provjeri autorizacije', error);
      }
    );
  }
}
