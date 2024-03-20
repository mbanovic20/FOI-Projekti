import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'; // Uvoz za navigaciju
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-pocetna',
  templateUrl: './pocetna.component.html',
  styleUrls: ['./pocetna.component.scss'],
})
export class PocetnaComponent implements OnInit {
  serije: any[] = [];
  filter: string = '';
  appStranicenje: number = 5;
  trenutnaStranica: number = 1;
  ukupnoStranica: number = 1;

  constructor(private router: Router) {}

  ngOnInit(): void {}

  pocetak(prvaStranica: number): void {
    this.trenutnaStranica = prvaStranica;
    this.dajSerije(this.trenutnaStranica);
  }

  prethodnaStranica(): void {
    if (this.trenutnaStranica > 1) {
      this.trenutnaStranica--;
      this.dajSerije(this.trenutnaStranica);
    }
  }

  sljedecaStranica(): void {
    if (this.trenutnaStranica < this.ukupnoStranica) {
      this.trenutnaStranica++;
      this.dajSerije(this.trenutnaStranica);
    }
  }

  kraj(ukupnoStranica: number): void {
    this.trenutnaStranica = ukupnoStranica;
    this.dajSerije(this.trenutnaStranica);
  }

  async dajSerije(trenutnaStranica: number): Promise<void> {
    const porukaElement = document.getElementById('poruka');
    if (this.filter.length < 3) {
      this.serije = [];
      porukaElement!.innerText = 'Unesite najmanje 3 znaka za pretragu.';
      return;
    }
    porukaElement!.innerText = '';

    try {
      const response = await fetch(
        `${environment.restServis}/api/tmdb/serije?trazi=${this.filter}&stranica=${trenutnaStranica}`
      );

      if (response.ok) {
        const data = await response.json();
        this.serije = data.results;
        console.log(this.serije);
        this.ukupnoStranica = data.total_pages;
        console.log(this.ukupnoStranica);
      } else {
        porukaElement!.innerText = `Greška u dohvatu serija: ${response.statusText}`;
      }
    } catch (error) {
      console.error('Greška prilikom dohvata serija:', error);
      porukaElement!.innerText = 'Došlo je do greške prilikom dohvata serija!';
    }
  }

  prikaziDetalje(idSerije: number) {
    this.router.navigate(['/detaljiSerije', idSerije]);
  }
}
