import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TrendsComponent} from './trends.component';

const routes: Routes = [
  // { path: '', redirectTo: '/trends', pathMatch: 'full' },
  { path: 'trends',  component: TrendsComponent },
];
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
