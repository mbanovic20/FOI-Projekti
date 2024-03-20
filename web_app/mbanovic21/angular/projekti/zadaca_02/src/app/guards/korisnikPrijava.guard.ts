import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { HttpService } from '../servisi/http.service';

@Injectable({
  providedIn: 'root',
})
export class KorisnikPrijavaGuard implements CanActivate {
  constructor(private httpServis: HttpService, private router: Router) {}

  canActivate(): Promise<boolean> {
    return new Promise((resolve) => {
      const tipKorisnika_id = sessionStorage.getItem('tipKorisnika_id');
      console.log(tipKorisnika_id);
      if (tipKorisnika_id == '1' || tipKorisnika_id == '2') {
        this.router.navigate(['/pocetna']);
        resolve(false);
      } else {
        resolve(true);
      }
    });
  }
}
