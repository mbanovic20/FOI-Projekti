import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { HttpService } from '../servisi/http.service';

@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate {
  constructor(private httpServis: HttpService, private router: Router) {}

  canActivate(): Promise<boolean> {
    return new Promise((resolve) => {
      const tipKorisnika_id = sessionStorage.getItem('tipKorisnika_id');
      console.log(tipKorisnika_id);
      if (tipKorisnika_id == '1') {
        resolve(true);
      } else {
        this.router.navigate(['/pocetna']);
        resolve(false);
      }
    });
  }
}
