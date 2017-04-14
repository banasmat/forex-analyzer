import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TrendsComponent} from './trends.component';
import {TrendDetailComponent} from './trend-detail.component';

const routes: Routes = [
  // { path: '', redirectTo: '/trends', pathMatch: 'full' },
  { path: 'trends',  component: TrendsComponent },
  { path: 'trend-detail/:id', component: TrendDetailComponent },
];
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
