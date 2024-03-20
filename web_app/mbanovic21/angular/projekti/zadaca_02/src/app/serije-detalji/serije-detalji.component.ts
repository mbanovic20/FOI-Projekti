import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpService } from '../servisi/http.service';

@Component({
  selector: 'app-serije-detalji',
  templateUrl: './serije-detalji.component.html',
  styleUrls: ['./serije-detalji.component.scss'],
})
export class SerijeDetaljiComponent implements OnInit {
  serija: any;
  poruka: string = '';

  constructor(
    private route: ActivatedRoute,
    private detaljiSerijeService: HttpService,
    private httpServis: HttpService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');
      if (id) {
        this.detaljiSerijeService.dajSerijaDetalji(id).then((data) => {
          this.serija = data;
        });
      }
    });
  }

  spremiUFavorite(serija: any): void {
    this.httpServis.provjeriAutorizaciju().subscribe(
      (data) => {
        if (data.gitAutoriziran && data.nacinPrijave === 'github') {
          let favoriti = JSON.parse(localStorage.getItem('favoriti') || '[]');

          const index = favoriti.findIndex((fav: any) => fav.id === serija.id);

          if (index === -1) {
            favoriti.push(serija);
            localStorage.setItem('favoriti', JSON.stringify(favoriti));
            this.poruka = 'Serija spremljena u favorite';
          } else {
            this.poruka = 'Serija je već u favoritima';
          }
        } else {
          // logika za prijavljene s bazom --> nemam
          this.poruka =
            'Napravio sam da samo korisnici sa gitHub računom mogu unositi favorite i iščitavati ih!';
        }
      },
      (error) => {
        console.error('Došlo je do pogreške pri provjeri autorizacije:', error);
      }
    );
  }
}
