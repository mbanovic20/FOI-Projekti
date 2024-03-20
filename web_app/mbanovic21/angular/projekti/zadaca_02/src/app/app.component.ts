import { Location } from '@angular/common';
import { Component } from '@angular/core';

import { environment } from '../environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = 'zadaca_02';
  putanja = 'nema';

  constructor(private lokacija: Location) {
    lokacija.onUrlChange((v) => {
      console.log('Promjena putanje: ' + v);
    });
  }

  ngDoCheck() {
    this.putanja = this.lokacija.path();
  }

  async odjavi() {
    let odgovor = await fetch(environment.appServis + '/odjava');
    let odgovor_auth = await fetch(environment.appServis + '/odjava/auth');
    let podaci = await odgovor.text();
    let podaci_auth = await odgovor_auth.text();
    console.log(podaci);
    console.log(podaci_auth);
    sessionStorage.clear();
    window.location.reload();
  }
}
