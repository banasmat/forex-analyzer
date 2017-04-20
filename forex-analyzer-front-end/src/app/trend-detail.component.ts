import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';
import { Location }                 from '@angular/common';

import 'rxjs/add/operator/switchMap';
import {Trend} from './trend';
import {TrendService} from './trend.service';

declare var Plotly: any;

@Component({
  selector: 'trend-detail',
  templateUrl: './trend-detail.component.html',
})
export class TrendDetailComponent implements OnInit {

  @Input() trend: Trend;
  constructor(
    private trendService: TrendService,
    private route: ActivatedRoute,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.route.params
      .switchMap((params: Params) => this.trendService.getTrend(+params['id']))
      .subscribe(trend => {
        this.trend = trend;

        // TODO consider creating separate CandlestickChart component
        const trace = {
          x: trend.priceRecords.map(function (record) {
            return record['dateTime'];
          }),
          open: trend.priceRecords.map(function (record) {
            return record['open'];
          }),
          high: trend.priceRecords.map(function (record) {
            return record['high'];
          }),
          low: trend.priceRecords.map(function (record) {
            return record['low'];
          }),
          close: trend.priceRecords.map(function (record) {
            return record['close'];
          }),
          decreasing: {line: {color: '#7F7F7F'}},
          increasing: {line: {color: '#17BECF'}},
          line: {color: 'rgba(31,119,180,1)'},
          type: 'candlestick',
          xaxis: 'x',
          yaxis: 'y'
        };
        Plotly.plot('plotly-div', [trace], {
          // dragmode: 'zoom',
          // margin: {
          //   r: 10,
          //   t: 25,
          //   b: 40,
          //   l: 60
          // },
          // showlegend: false,
          // xaxis: {
          //   autorange: true,
          //   domain: [0, 1],
          //   range: ['2017-01-02', '2017-01-03'],
          //   title: 'Date',
          //   type: 'date'
          // },
          // yaxis: {
          //   autorange: true,
          //   domain: [0, 1],
          //   range: [1.0, 1.1],
          //   type: 'linear'
          // }
        });
      }
    );
  }

  goBack(): void {
    this.location.back();
  }

}
