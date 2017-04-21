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
    this.getTrends();
  }

  getTrends(): void {
    this.trendService.getTrends().then(trends => this.trends = trends);
  }
}
