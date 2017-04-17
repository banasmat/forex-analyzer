import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';
import { Location }                 from '@angular/common';

import 'rxjs/add/operator/switchMap';
import {Trend} from './trend';
import {TrendService} from './trend.service';

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
      .subscribe(trend => this.trend = trend);
  }

  goBack(): void {
    this.location.back();
  }

}
