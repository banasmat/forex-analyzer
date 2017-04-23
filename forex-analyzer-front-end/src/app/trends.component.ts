import {Component, OnInit} from '@angular/core';
import {Trend} from './trend';
import {TrendService} from './trend.service';
import {TrendSearchData} from './trend-search-data';

@Component({
  selector: 'trends',
  templateUrl: './trends.component.html',
  styleUrls: ['./trends.component.css']
})
export class TrendsComponent {
  trends: Trend[];

  constructor(private trendService: TrendService) {}

  ngOnInit(): void {
    this.getTrends(new Date(2016, 1, 1), new Date(2017, 1, 1), 'EURUSD');
  }

  getTrends(start: Date, end: Date, symbol: string): void {
    this.trendService.getTrends(start, end, symbol).then(trends => this.trends = trends);
  }

  onSearchFormSubmit(data: TrendSearchData): void {
    this.getTrends(data.start, data.end, data.symbol);
  }
}
