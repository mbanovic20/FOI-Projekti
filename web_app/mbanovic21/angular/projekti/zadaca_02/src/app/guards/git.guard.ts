import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { HttpService } from '../servisi/http.service';

@Injectable({
  providedIn: 'root',
})
export class GitGuard implements CanActivate {
  constructor(private httpServis: HttpService, private router: Router) {}

  canActivate(): Promise<boolean> {
    return new Promise((resolve) => {
      this.httpServis.provjeriAutorizaciju().subscribe(
        (data) => {
          if (!data.gitAutoriziran && data.nacinPrijave !== 'github') {
            resolve(true);
          } else {
            this.router.navigate(['/pocetna']);
            resolve(false);
          }
        },
        () => {
          this.router.navigate(['/pocetna']);
          resolve(false);
        }
      );
    });
  }
}
