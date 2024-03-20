import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PocetnaComponent } from './pocetna/pocetna.component';
import { SerijeDetaljiComponent } from './serije-detalji/serije-detalji.component';
import { DokumentacijaComponent } from './dokumentacija/dokumentacija.component';
import { PrijavaComponent } from './prijava/prijava.component';
import { ProfilComponent } from './profil/profil.component';
import { RegistracijaComponent } from './registracija/registracija.component';
import { GitGuard } from './guards/git.guard';
import { AdminGuard } from './guards/admin.guard';
import { KorisnikGuard } from './guards/korisnik.guard';
import { KorisnikPrijavaGuard } from './guards/korisnikPrijava.guard';
import { KorisniciComponent } from './korisnici/korisnici.component';
import { FavoritiComponent } from './favoriti/favoriti.component';
import { FavoritiDetaljiComponent } from './favoriti-detalji/favoriti-detalji.component';

const routes: Routes = [
  { path: 'pocetna', component: PocetnaComponent },
  { path: 'detaljiSerije/:id', component: SerijeDetaljiComponent },
  { path: 'dokumentacija', component: DokumentacijaComponent },
  {
    path: 'prijava',
    component: PrijavaComponent,
    canActivate: [GitGuard, KorisnikPrijavaGuard],
  },
  {
    path: 'profil',
    component: ProfilComponent,
    canActivate: [GitGuard, KorisnikGuard],
  },
  {
    path: 'registracija',
    component: RegistracijaComponent,
    canActivate: [GitGuard, AdminGuard],
  },
  {
    path: 'korisnici',
    component: KorisniciComponent,
    canActivate: [GitGuard, AdminGuard],
  },
  {
    path: 'favoriti',
    component: FavoritiComponent,
  },
  { path: 'detaljiFavoriti/:id', component: FavoritiDetaljiComponent },
  { path: '', redirectTo: '/pocetna', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
