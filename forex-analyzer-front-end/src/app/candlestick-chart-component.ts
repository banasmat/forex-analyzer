import {Component, Input, OnInit} from '@angular/core';

import {Trend} from './trend';

declare var Plotly: any;

@Component({
  selector: 'candlestick-chart',
  template: `<div id="candlestick-chart"></div>`,
})
export class CandlestickChartComponent implements OnInit {

  @Input() trend: Trend;

  getTrace() {
    return {
      x: this.trend.priceRecords.map(function (record) {
        return record['dateTime'];
      }),
      open: this.trend.priceRecords.map(function (record) {
        return record['open'];
      }),
      high: this.trend.priceRecords.map(function (record) {
        return record['high'];
      }),
      low: this.trend.priceRecords.map(function (record) {
        return record['low'];
      }),
      close: this.trend.priceRecords.map(function (record) {
        return record['close'];
      }),
      decreasing: {line: {color: '#7F7F7F'}},
      increasing: {line: {color: '#17BECF'}},
      line: {color: 'rgba(31,119,180,1)'},
      type: 'candlestick',
      xaxis: 'x',
      yaxis: 'y'
    };
  }

  getLayout(trace: any) {
    return {
      dragmode: 'zoom',
      margin: {
        r: 10,
        t: 25,
        b: 40,
        l: 60
      },
      showlegend: false,
      xaxis: {
        autorange: false,
        domain: [0, 1],
        range: [trace.x[0], trace.x[trace.x.length - 1]],
        title: 'Date',
        type: 'date'
      },
      yaxis: {
        autorange: true,
        domain: [0, 1],
        range: [Math.min(...trace.low), Math.max(...trace.high)],
        type: 'linear'
      },
      shapes: [
        {
          type: 'line',
          xref: 'x',
          yref: 'paper',
          x0: this.trend.start.dateTime,
          y0: 0,
          x1: this.trend.start.dateTime,
          y1: 1,
          line: {
            width: 2,
            color: '#FF0000'
          }
        },
        {
          type: 'line',
          xref: 'x',
          yref: 'paper',
          x0: this.trend.end.dateTime,
          y0: 0,
          x1: this.trend.end.dateTime,
          y1: 1,
          line: {
            width: 2,
            color: '#FF0000'
          }
        }
      ]
    };
  }

  ngOnInit(): void {

    let trace = this.getTrace();

    let layout = this.getLayout(trace);

    Plotly.plot('candlestick-chart', [trace], layout);
  }
}
