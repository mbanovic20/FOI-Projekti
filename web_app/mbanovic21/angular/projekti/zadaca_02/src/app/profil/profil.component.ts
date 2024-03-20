import { Component, OnInit, ViewChild } from '@angular/core';
import { Form, NgForm } from '@angular/forms';
import { ReCaptchaV3Service } from 'ng-recaptcha';
import { environment } from '../../environments/environment';
import { HttpService } from '../servisi/http.service';
import { IKorisnik } from '../servisi/IKorisnik';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.scss'],
})
export class ProfilComponent implements OnInit {
  reCAPTCHAToken: string = '';
  tajniKljuc: string = '';
  totp: string = '';
  tokenVisible: boolean = false;
  dvostupanjskaAutentikacijaUkljucena: boolean = false;

  constructor(
    private httpServis: HttpService,
    private recaptchaV3Service: ReCaptchaV3Service
  ) {}

  async ngOnInit() {
    let odgovor = await fetch(environment.appServis + '/dohvatiKorisnika');
    let korisnik = await odgovor.text();
    let podaci = JSON.parse(korisnik) as IKorisnik;
    console.log(podaci);

    let ime = document.getElementById('ime') as HTMLInputElement;
    ime.value = podaci.ime as string;

    let prezime = document.getElementById('prezime') as HTMLInputElement;
    prezime.value = podaci.prezime as string;

    let korime = document.getElementById('korime') as HTMLInputElement;
    korime.value = podaci.korime as string;

    let lozinka = document.getElementById('lozinka') as HTMLInputElement;
    lozinka.value = podaci.lozinka as string;

    let email = document.getElementById('email') as HTMLInputElement;
    email.value = podaci.email as string;

    let datum_rođenja = document.getElementById(
      'datum_rođenja'
    ) as HTMLInputElement;
    datum_rođenja.value = podaci.datum_rođenja as string;
  }

  async kreirajTajniKljuc() {
    let odgovor = await fetch(environment.appServis + '/dohvatiKorisnika');
    let korisnik = await odgovor.text();
    let podaci = JSON.parse(korisnik) as IKorisnik;
    console.log(podaci);
    if (podaci.tajniKljuc == '' || podaci.tajniKljuc == null) {
      console.log(
        'Tajni ključ je prazan ili je null -> sad ćete dobiti tajni ključ...'
      );
      let korime = document.getElementById('korime') as HTMLInputElement;
      this.httpServis
        .kreirajTajniKljuc(korime.value as string)
        .subscribe((data: any) => {
          this.tajniKljuc = data.tajniKljuc;
          console.log('this.tajniKljuc ==', this.tajniKljuc);
          console.log(
            'poslije kreiranja tajnog ključa: -> data.tajniKljuc ==',
            data.tajniKljuc
          );
        });
    } else {
      console.log(
        'Tajni ključ se već prikazao jednom i njegova vrijednost je zapisana u bazi!'
      );
      this.tajniKljuc = '';
    }
  }

  async azurirajKorisnikaSaProvjera() {
    let odgovor = await fetch(environment.appServis + '/dohvatiKorisnika');
    let korisnik = await odgovor.text();
    let podaci = JSON.parse(korisnik) as IKorisnik;

    if (this.dvostupanjskaAutentikacijaUkljucena) {
      if (podaci.tajniKljuc == null || podaci.tajniKljuc == '') {
        this.kreirajTajniKljuc();
      }

      if (this.totp && this.totp.trim() !== '') {
        this.httpServis
          .provjeriTOTPKod(this.totp, podaci.tajniKljuc as string)
          .subscribe(
            (odgovor) => {
              console.log('Odgovor servera za TOTP:', odgovor);
              this.provjeriRECAPTCHA(podaci);
            },
            (error) => {
              console.error('Greška prilikom provjere TOTP koda:', error);
            }
          );
      } else {
        console.error('TOTP kod je prazan!');
      }
    } else {
      console.log('Dvostupanjska autentifikacija isključena.');
      this.provjeriRECAPTCHA(podaci);
    }
  }

  public provjeriRECAPTCHA(data: IKorisnik): void {
    this.recaptchaV3Service
      .execute('importantAction')
      .subscribe((token: string) => {
        this.tokenVisible = true;
        this.reCAPTCHAToken = `Token [${token}] uspješno generiran`;
        this.azuriraj(data);
      });
  }

  async azuriraj(podaci: IKorisnik) {
    let korime = document.getElementById('korime') as HTMLInputElement;
    podaci.korime = korime.value;

    let ime = document.getElementById('ime') as HTMLInputElement;
    podaci.ime = ime.value;

    let prezime = document.getElementById('prezime') as HTMLInputElement;
    podaci.prezime = prezime.value;

    let lozinka = document.getElementById('lozinka') as HTMLInputElement;
    podaci.lozinka = lozinka.value;

    let email = document.getElementById('email') as HTMLInputElement;
    podaci.email = email.value;

    let datum_rođenja = document.getElementById(
      'datum_rođenja'
    ) as HTMLInputElement;
    podaci.datum_rođenja = datum_rođenja.value;

    console.log(podaci);
    this.httpServis.azurirajKorisnika(podaci).subscribe((data) => {
      this.ngOnInit();
    });
  }
}
