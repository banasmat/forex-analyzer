import {Component, OnInit} from '@angular/core';
import {Trend} from './trend';
import {TrendService} from './trend.service';

@Component({
  selector: 'trends',
  templateUrl: './trends.component.html',
  styleUrls: ['./trends.component.css']
})
export class TrendsComponent implements OnInit {
  trends: Trend[];

  constructor(private trendService: TrendService){}

  ngOnInit(): void {
    this.getTrends('01-01-2016 02:00:00', '31-03-2017 13:00:00', 'EURUSD');
  }

  getTrends(start: string, end: string, symbol: string): void {
    this.trendService.getTrends(start, end, symbol).then(trends => this.trends = trends);
  }
}
