import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SerijeDetaljiComponent } from './serije-detalji/serije-detalji.component';
import { DokumentacijaComponent } from './dokumentacija/dokumentacija.component';
import { ProfilComponent } from './profil/profil.component';
import { PocetnaComponent } from './pocetna/pocetna.component'; //
import { HttpService } from './servisi/http.service';
import { PrijavaComponent } from './prijava/prijava.component';
import { environment } from '../environments/environment';
import { RegistracijaComponent } from './registracija/registracija.component';
import { RECAPTCHA_V3_SITE_KEY, RecaptchaV3Module } from 'ng-recaptcha';
import { KorisniciComponent } from './korisnici/korisnici.component';
import { FavoritiComponent } from './favoriti/favoriti.component';
import { FavoritiDetaljiComponent } from './favoriti-detalji/favoriti-detalji.component';

@NgModule({
  declarations: [
    AppComponent,
    SerijeDetaljiComponent,
    DokumentacijaComponent,
    ProfilComponent,
    PocetnaComponent,
    PrijavaComponent,
    RegistracijaComponent,
    KorisniciComponent,
    FavoritiComponent,
    FavoritiDetaljiComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RecaptchaV3Module,
  ],
  providers: [
    HttpService,
    {
      provide: RECAPTCHA_V3_SITE_KEY,
      useValue: environment.recaptcha.siteKey,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
