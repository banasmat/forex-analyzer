import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

// import {InMemoryDataService} from './in-memory-data.service';
// import {InMemoryWebApiModule} from 'angular-in-memory-web-api';

import {AppComponent} from './app.component';
import { TrendSearchComponent }  from './trend-search.component';
import {AppRoutingModule} from './app-routing.module';
import {HttpModule} from '@angular/http';
import {TrendService} from './trend.service';
import {TrendDetailComponent} from './trend-detail.component';
import {CandlestickChartComponent} from './candlestick-chart-component';
import {CandlestickSmallChartComponent} from './candlestick-small-chart-component';
import {MyDatePickerModule} from 'mydatepicker';
import {FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports:      [
    BrowserModule,
    HttpModule,
    // InMemoryWebApiModule.forRoot(InMemoryDataService),
    AppRoutingModule,
    MyDatePickerModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    AppComponent,
    TrendSearchComponent,
    TrendDetailComponent,
    CandlestickChartComponent,
    CandlestickSmallChartComponent
  ],
  providers: [
    TrendService
  ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
