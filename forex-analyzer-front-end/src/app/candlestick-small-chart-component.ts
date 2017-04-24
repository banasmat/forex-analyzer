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

    let layout = {
      height: 250,
      margin: {
        r: 15,
        t: 20,
        b: 50,
        l: 40
      },
      dragmode: 'orbit', // Hack to disable dragmode. Orbit won't work for this type of chart.
      hovermode: false,
      showlegend: false,
      xaxis: {
        autorange: false,
        domain: [0, 1],
        range: [trace.x[0], trace.x[trace.x.length - 1]],
        type: 'date',
        rangeslider: {
          visible: false
        }
      },
      yaxis: {
        autorange: true,
        domain: [0, 1],
        range: [Math.min(...trace.low), Math.max(...trace.high)],
        type: 'linear'
      }
    };

    (setTimeout(function(trend: Trend){
      Plotly.plot('candlestick-chart-' + trend.id, [trace], layout);
    }, 0, this.trend));
  }
}
