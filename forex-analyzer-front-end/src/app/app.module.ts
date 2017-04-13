import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import {InMemoryDataService} from './in-memory-data.service';
import {InMemoryWebApiModule} from 'angular-in-memory-web-api';

import {AppComponent} from './app.component';
import { TrendsComponent }  from './trends.component';
import {AppRoutingModule} from './app-routing.module';
import {HttpModule} from '@angular/http';
import {TrendService} from './trend.service';

@NgModule({
  imports:      [
    BrowserModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService),
    AppRoutingModule
  ],
  declarations: [
    AppComponent,
    TrendsComponent
  ],
  providers: [
    TrendService
  ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
