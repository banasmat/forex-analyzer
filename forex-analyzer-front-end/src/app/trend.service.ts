import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {Trend} from './trend';

@Injectable()
export class TrendService {
  private trendsUrl = 'api/trends';

  constructor(private http: Http) { }

  getTrends(): Promise<Trend[]> {
    return this.http.get(this.trendsUrl)
      .toPromise()
      .then(response => response.json().data as Trend[])
      .catch(this.handleError);
  }

  getTrend(id: number): Promise<Trend> {
    const url = `${this.trendsUrl}/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json().data as Trend)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
