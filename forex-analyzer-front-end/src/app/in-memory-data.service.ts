import {InMemoryDbService} from 'angular-in-memory-web-api';
//TODO add id to api
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    let trends =
      [
        {
          id: 1,
          'start': {
            'dateTime': '2017-01-02T02:37:00',
            'open': 1.05207,
            'high': 1.0523,
            'low': 1.052,
            'close': 1.05202
          },
          'end': {
            'dateTime': '2017-01-03T10:14:00',
            'open': 1.03443,
            'high': 1.03456,
            'low': 1.03406,
            'close': 1.03451
          },
          'symbol': {
            'name': 'EURUSD'
          }
        },
        {
          id: 2,
          'start': {
            'dateTime': '2017-01-03T10:14:00',
            'open': 1.03443,
            'high': 1.03456,
            'low': 1.03406,
            'close': 1.03451
          },
          'end': {
            'dateTime': '2017-01-05T01:40:00',
            'open': 1.05721,
            'high': 1.05746,
            'low': 1.05711,
            'close': 1.05731
          },
          'symbol': {
            'name': 'EURUSD'
          }
        },
        {
          id: 3,
          'start': {
            'dateTime': '2017-01-05T01:40:00',
            'open': 1.05721,
            'high': 1.05746,
            'low': 1.05711,
            'close': 1.05731
          },
          'end': {
            'dateTime': '2017-01-05T06:19:00',
            'open': 1.04854,
            'high': 1.04857,
            'low': 1.04824,
            'close': 1.04846
          },
          'symbol': {
            'name': 'EURUSD'
          }
        },
        {
          id: 4,
          'start': {
            'dateTime': '2017-01-05T06:19:00',
            'open': 1.04854,
            'high': 1.04857,
            'low': 1.04824,
            'close': 1.04846
          },
          'end': {
            'dateTime': '2017-01-05T11:10:00',
            'open': 1.06091,
            'high': 1.06151,
            'low': 1.06091,
            'close': 1.06124
          },
          'symbol': {
            'name': 'EURUSD'
          }
        },
        {
          id: 5,
          'start': {
            'dateTime': '2017-01-05T11:10:00',
            'open': 1.06091,
            'high': 1.06151,
            'low': 1.06091,
            'close': 1.06124
          },
          'end': {
            'dateTime': '2017-01-09T05:51:00',
            'open': 1.0512,
            'high': 1.05124,
            'low': 1.05108,
            'close': 1.05113
          },
          'symbol': {
            'name': 'EURUSD'
          }
        },
        {
          id: 6,
          'start': {
            'dateTime': '2017-01-09T05:51:00',
            'open': 1.0512,
            'high': 1.05124,
            'low': 1.05108,
            'close': 1.05113
          },
          'end': {
            'dateTime': '2017-01-09T22:36:00',
            'open': 1.06196,
            'high': 1.0627,
            'low': 1.06188,
            'close': 1.06195
          },
          'symbol': {
            'name': 'EURUSD'
          }
        },
        {
          id: 7,
          'start': {
            'dateTime': '2017-01-09T22:36:00',
            'open': 1.06196,
            'high': 1.0627,
            'low': 1.06188,
            'close': 1.06195
          },
          'end': {
            'dateTime': '2017-01-11T10:28:00',
            'open': 1.04562,
            'high': 1.04566,
            'low': 1.04537,
            'close': 1.04558
          },
          'symbol': {
            'name': 'EURUSD'
          }
        }
      ];
    return {trends};
  }
}
