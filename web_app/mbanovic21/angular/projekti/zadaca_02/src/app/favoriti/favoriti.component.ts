import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpService } from '../servisi/http.service';

@Component({
  selector: 'app-favoriti',
  templateUrl: './favoriti.component.html',
  styleUrls: ['./favoriti.component.scss'],
})
export class FavoritiComponent implements OnInit {
  serije: any[] = [];
  autorizacijskaPoruka: string = '';

  constructor(private router: Router, private httpServis: HttpService) {}

  ngOnInit(): void {
    this.ucitajFavorite();
  }

  ucitajFavorite(): void {
    this.httpServis.provjeriAutorizaciju().subscribe(
      (data) => {
        if (data.gitAutoriziran && data.nacinPrijave === 'github') {
          this.serije = JSON.parse(localStorage.getItem('favoriti') || '[]');
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

  prikaziDetaljeOSezonama(idSerije: number) {
    this.router.navigate(['/detaljiFavoriti', idSerije]);
  }

  obrisiSeriju(index: number): void {
    this.serije.splice(index, 1);
    localStorage.setItem('favoriti', JSON.stringify(this.serije));
  }
}
