import {PriceRecord} from './price-record';
import {Symbol} from './symbol';

export class Trend {
  id: number;
  symbol: Symbol;
  start: PriceRecord;
  end: PriceRecord;
  priceRecords: PriceRecord[];
}
