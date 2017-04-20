import {Component, Input, OnInit} from '@angular/core';

import {Trend} from './trend';

declare var Plotly: any;

@Component({
  selector: 'candlestick-chart',
  template: `<div id="candlestick-chart"></div>`,
})
export class CandlestickChartComponent implements OnInit{

  @Input() trend: Trend;

  ngOnInit(): void {

    const trace = {
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

    const layout = {
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
          type: 'rect',
          xref: 'x',
          yref: 'paper',
          x0: this.trend.start.dateTime, // TODO should be formatted like the rest ?
          y0: 0,
          x1: this.trend.start.dateTime,
          y1: 1,
          fillcolor: '#d3d3d3',
          opacity: 0.2,
          line: {
            width: 0
          }
        },
        {
          type: 'rect',
          xref: 'x',
          yref: 'paper',
          x0: this.trend.end.dateTime,
          y0: 0,
          x1: this.trend.end.dateTime,
          y1: 1,
          fillcolor: '#d3d3d3',
          opacity: 0.2,
          line: {
            width: 0
          }
        }
      ]
    };

    Plotly.plot('candlestick-chart', [trace], layout);
  }
}
