import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ReCaptchaV3Service } from 'ng-recaptcha';
import { environment } from '../../environments/environment';
import { HttpService } from '../servisi/http.service';
import { IPrijava } from '../servisi/IKorisnik';
import { Router } from '@angular/router';

@Component({
  selector: 'app-prijava',
  templateUrl: './prijava.component.html',
  styleUrls: ['./prijava.component.scss'],
})
export class PrijavaComponent implements OnInit {
  modelPrijava: IPrijava = {
    korime: '',
    lozinka: '',
    totp: '',
    tajniKljuc: '',
    tipKorisnika_id: 0,
  };
  dvostupanjskaAutentikacijaUkljucena: boolean = false;
  reCAPTCHAToken: string = '';
  tokenVisible: boolean = false;

  constructor(
    private userService: HttpService,
    private recaptchaV3Service: ReCaptchaV3Service,
    private router: Router
  ) {}

  ngOnInit(): void {}

  public provjeriRECAPTCHA(form: NgForm): void {
    if (this.modelPrijava.korime == '' || this.modelPrijava.lozinka == '') {
      console.log('Popunite sve podatke!');
      return;
    }

    this.recaptchaV3Service.execute('LOGIN').subscribe((token: string) => {
      this.tokenVisible = true;
      this.reCAPTCHAToken = `Token: [${token}] USPJEŠNO GENERIRAN!`;
      if (this.dvostupanjskaAutentikacijaUkljucena) {
        this.prijavaSa2FA();
      } else {
        this.prijavaBez2FA();
      }
    });
  }

  private prijavaBez2FA() {
    this.userService.provjeriKorisnika(this.modelPrijava).subscribe((data) => {
      sessionStorage.setItem(
        'tipKorisnika_id',
        data.tipKorisnika_id.toString()
      );
      if (data.korime != this.modelPrijava.korime) {
        console.error('Neispravno korisničko ime ili lozinka!');
        return;
      }
      console.log('Korisnik je uspješno prijavljen.');
      this.router.navigate(['/pocetna']);
    });
  }

  private async prijavaSa2FA() {
    this.userService
      .dohvatiKorisnikaPoKorime(this.modelPrijava.korime as string)
      .subscribe(async (korisnik) => {
        if (korisnik.korime !== this.modelPrijava.korime) {
          alert('Neispravno korisničko ime ili lozinka!');
          return;
        }
        if (!korisnik.tajniKljuc || korisnik.tajniKljuc === '') {
          alert(
            'Nemate tajni ključ, kako biste ga dobili najprije se prijavite bez dvostupanjske autentifikacija, a potom otiđite na stranicu profila, kliknite na gumb kreiraj tajni ključ i tajni ključ će vam se prikazati odmah ispod gumba!'
          );
          return;
        }
        if (!this.modelPrijava.totp || this.modelPrijava.totp === '') {
          alert(
            'Dvostupanjska autentifikacija je uključena. Molimo unesite TOTP kod.'
          );
          return;
        }

        this.userService
          .provjeriTOTPKod(
            this.modelPrijava.totp as string,
            korisnik.tajniKljuc
          )
          .subscribe((totpIspravan) => {
            if (!totpIspravan) {
              console.log('Neispravan TOTP kod. Pokušajte ponovno.');
              return;
            }

            this.userService
              .provjeriKorisnika(this.modelPrijava)
              .subscribe((data) => {
                sessionStorage.setItem(
                  'tipKorisnika_id',
                  data.tipKorisnika_id.toString()
                );
                console.log('Korisnik je uspješno prijavljen.');
                this.router.navigate(['/pocetna']);
              });
          });
      });
  }

  preusmjeriKorisnikaNaGit(): void {
    this.recaptchaV3Service.execute('LOGIN').subscribe((token: string) => {
      this.tokenVisible = true;
      this.reCAPTCHAToken = `Token: [${token}] USPJEŠNO GENERIRAN!`;

      window.location.href = environment.appServis + '/auth/github';
    });
  }
}
