import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TrendSearchComponent} from './trend-search.component';
import {TrendDetailComponent} from './trend-detail.component';

const routes: Routes = [
  // { path: '', redirectTo: '/trends', pathMatch: 'full' },
  { path: 'trends',  component: TrendSearchComponent },
  { path: 'trend-detail/:id', component: TrendDetailComponent },
];
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
