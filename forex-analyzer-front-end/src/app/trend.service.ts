import {Injectable} from '@angular/core';
import {Http, URLSearchParams} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {Trend} from './trend';
import {AppSettings} from './app.settings';
import * as moment from 'moment/moment';

@Injectable()
export class TrendService {
  // TODO build url with symbol/start/end settings - use form in trends.component.ts
  private trendsUrl = AppSettings.API_ENDPOINT + 'trend';

  constructor(private http: Http) { }

  getTrends(start: Date, end: Date, symbol: string): Promise<Trend[]> {

    let params: URLSearchParams = new URLSearchParams();
    params.set('start', moment(start).format(AppSettings.DATE_TIME_FORMAT));
    params.set('end', moment(end).format(AppSettings.DATE_TIME_FORMAT));
    params.set('symbol', symbol);

    return this.http.get(this.trendsUrl, {
      search: params
    })
      .toPromise()
      .then((response) => {
        return response.json() as Trend[];
      })
      .catch(this.handleError);
  }

  getTrend(id: number): Promise<Trend> {
    const url = `${this.trendsUrl}/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json() as Trend)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
