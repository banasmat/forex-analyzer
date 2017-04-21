import {Injectable} from '@angular/core';
import {Http, URLSearchParams} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {Trend} from './trend';
import {AppSettings} from './app.settings';

@Injectable()
export class TrendService {
  // TODO build url with symbol/start/end settings - use form in trends.component.ts
  private trendsUrl = AppSettings.API_ENDPOINT + 'trend';

  constructor(private http: Http) { }

  getTrends(): Promise<Trend[]> {

    let params: URLSearchParams = new URLSearchParams();
    params.set('start', '01-01-2016 02:00:00');
    params.set('end', '31-03-2017 13:00:00');
    params.set('symbol', 'EURUSD');

    return this.http.get(this.trendsUrl, {
      search: params
    })
      .toPromise()
      .then((response) => {
       // console.log(response.json() as Trend[]);
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
