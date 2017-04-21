import {Component, Input, OnInit} from '@angular/core';

import {Trend} from './trend';
import {CandlestickChartComponent} from './candlestick-chart-component';

declare var Plotly: any;

@Component({
  selector: 'candlestick-small-chart',
  template: `<div *ngIf="trend"><div id="candlestick-chart-{{trend.id}}"></div></div>`,
})
export class CandlestickSmallChartComponent extends CandlestickChartComponent{

  @Input() trend: Trend;


  ngOnInit(): void {

    let trace = super.getTrace();

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

    (setTimeout(function(trend: Trend){
      Plotly.plot('candlestick-chart-' + trend.id, [trace], {layout});
    }, 0, this.trend));
  }
}
