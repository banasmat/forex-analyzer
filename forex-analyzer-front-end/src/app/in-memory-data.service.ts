import {InMemoryDbService} from 'angular-in-memory-web-api';
//TODO add id to api
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    let trends =
      [
        {
          id: 1,
          'start': {
            'symbol': {
              'name': 'EURUSD'
            },
            'dateTime': '2017-01-02T02:37:00',
            'open': 1.05207,
            'high': 1.0523,
            'low': 1.052,
            'close': 1.05202
          },
          'end': {
            'symbol': {
              'name': 'EURUSD'
            },
            'dateTime': '2017-01-03T10:14:00',
            'open': 1.03443,
            'high': 1.03456,
            'low': 1.03406,
            'close': 1.03451
          },
          'symbol': {
            'name': 'EURUSD'
          },
          'links': [
            {
              'rel': 'self',
              'href': 'http://localhost:8080/trend/1'
            },
            {
              'rel': 'priceRecords',
              'href': 'http://localhost:8080/price-record?symbol=EURUSD&start=02-01-2017%2002:37:00&end=03-01-2017%2010:14:00&interval=1H'
            }
          ]
        },
        {
          id: 2,
          'start': {
            'symbol': {
              'name': 'EURUSD'
            },
            'dateTime': '2017-01-03T10:14:00',
            'open': 1.03443,
            'high': 1.03456,
            'low': 1.03406,
            'close': 1.03451
          },
          'end': {
            'symbol': {
              'name': 'EURUSD'
            },
            'dateTime': '2017-01-05T01:40:00',
            'open': 1.05721,
            'high': 1.05746,
            'low': 1.05711,
            'close': 1.05731
          },
          'symbol': {
            'name': 'EURUSD'
          },
          'links': [
            {
              'rel': 'self',
              'href': 'http://localhost:8080/trend/2'
            },
            {
              'rel': 'priceRecords',
              'href': 'http://localhost:8080/price-record?symbol=EURUSD&start=03-01-2017%2010:14:00&end=05-01-2017%2001:40:00&interval=1H'
            }
          ]
        },
        {
          id: 3,
          'start': {
            'symbol': {
              'name': 'EURUSD'
            },
            'dateTime': '2017-01-05T01:40:00',
            'open': 1.05721,
            'high': 1.05746,
            'low': 1.05711,
            'close': 1.05731
          },
          'end': {
            'symbol': {
              'name': 'EURUSD'
            },
            'dateTime': '2017-01-05T06:19:00',
            'open': 1.04854,
            'high': 1.04857,
            'low': 1.04824,
            'close': 1.04846
          },
          'symbol': {
            'name': 'EURUSD'
          },
          'links': [
            {
              'rel': 'self',
              'href': 'http://localhost:8080/trend/3'
            },
            {
              'rel': 'priceRecords',
              'href': 'http://localhost:8080/price-record?symbol=EURUSD&start=05-01-2017%2001:40:00&end=05-01-2017%2006:19:00&interval=1H'
            }
          ]
        },
        {
          id: 4,
          'start': {
            'symbol': {
              'name': 'EURUSD'
            },
            'dateTime': '2017-01-05T06:19:00',
            'open': 1.04854,
            'high': 1.04857,
            'low': 1.04824,
            'close': 1.04846
          },
          'end': {
            'symbol': {
              'name': 'EURUSD'
            },
            'dateTime': '2017-01-05T11:10:00',
            'open': 1.06091,
            'high': 1.06151,
            'low': 1.06091,
            'close': 1.06124
          },
          'symbol': {
            'name': 'EURUSD'
          },
          'links': [
            {
              'rel': 'self',
              'href': 'http://localhost:8080/trend/4'
            },
            {
              'rel': 'priceRecords',
              'href': 'http://localhost:8080/price-record?symbol=EURUSD&start=05-01-2017%2006:19:00&end=05-01-2017%2011:10:00&interval=1H'
            }
          ]
        },
        {
          id: 5,
          'start': {
            'symbol': {
              'name': 'EURUSD'
            },
            'dateTime': '2017-01-05T11:10:00',
            'open': 1.06091,
            'high': 1.06151,
            'low': 1.06091,
            'close': 1.06124
          },
          'end': {
            'symbol': {
              'name': 'EURUSD'
            },
            'dateTime': '2017-01-09T05:51:00',
            'open': 1.0512,
            'high': 1.05124,
            'low': 1.05108,
            'close': 1.05113
          },
          'symbol': {
            'name': 'EURUSD'
          },
          'links': [
            {
              'rel': 'self',
              'href': 'http://localhost:8080/trend/5'
            },
            {
              'rel': 'priceRecords',
              'href': 'http://localhost:8080/price-record?symbol=EURUSD&start=05-01-2017%2011:10:00&end=09-01-2017%2005:51:00&interval=1H'
            }
          ]
        },
        {
          id: 6,
          'start': {
            'symbol': {
              'name': 'EURUSD'
            },
            'dateTime': '2017-01-09T05:51:00',
            'open': 1.0512,
            'high': 1.05124,
            'low': 1.05108,
            'close': 1.05113
          },
          'end': {
            'symbol': {
              'name': 'EURUSD'
            },
            'dateTime': '2017-01-09T22:36:00',
            'open': 1.06196,
            'high': 1.0627,
            'low': 1.06188,
            'close': 1.06195
          },
          'symbol': {
            'name': 'EURUSD'
          },
          'links': [
            {
              'rel': 'self',
              'href': 'http://localhost:8080/trend/6'
            },
            {
              'rel': 'priceRecords',
              'href': 'http://localhost:8080/price-record?symbol=EURUSD&start=09-01-2017%2005:51:00&end=09-01-2017%2022:36:00&interval=1H'
            }
          ]
        },
        {
          id: 7,
          'start': {
            'symbol': {
              'name': 'EURUSD'
            },
            'dateTime': '2017-01-09T22:36:00',
            'open': 1.06196,
            'high': 1.0627,
            'low': 1.06188,
            'close': 1.06195
          },
          'end': {
            'symbol': {
              'name': 'EURUSD'
            },
            'dateTime': '2017-01-11T10:28:00',
            'open': 1.04562,
            'high': 1.04566,
            'low': 1.04537,
            'close': 1.04558
          },
          'symbol': {
            'name': 'EURUSD'
          },
          'links': [
            {
              'rel': 'self',
              'href': 'http://localhost:8080/trend/7'
            },
            {
              'rel': 'priceRecords',
              'href': 'http://localhost:8080/price-record?symbol=EURUSD&start=09-01-2017%2022:36:00&end=11-01-2017%2010:28:00&interval=1H'
            }
          ]
        }
      ];
    return {trends};
  }
}
