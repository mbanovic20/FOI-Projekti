import { Component } from '@angular/core';
import { HttpService } from '../servisi/http.service';
import { IKorisnik } from '../servisi/IKorisnik';
import { NgForm } from '@angular/forms';
import { ReCaptchaV3Service } from 'ng-recaptcha';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registracija',
  templateUrl: './registracija.component.html',
  styleUrls: ['./registracija.component.scss'],
})
export class RegistracijaComponent {
  reCAPTCHAToken: string = '';
  tokenVisible: boolean = false;

  constructor(
    private userService: HttpService,
    private recaptchaV3Service: ReCaptchaV3Service,
    private router: Router
  ) {}

  public provjeriRECAPTCHA(form: NgForm, data: IKorisnik): void {
    if (data.korime == '' || data.lozinka == '' || form.invalid == true) {
      console.log('Popunite sve podatke!');
      return;
    }

    this.recaptchaV3Service
      .execute('importantAction')
      .subscribe((token: string) => {
        this.tokenVisible = true;
        this.reCAPTCHAToken = `Token [${token}] uspješno generiran`;
        this.userService.dodajKorisnika(data).subscribe((data) => {
          this.router.navigate(['prijava']);
        });
      });
  }
}
