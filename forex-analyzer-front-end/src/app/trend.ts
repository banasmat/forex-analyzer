import {PriceRecord} from './PriceRecord';
import {Symbol} from './Symbol';

export class Trend {
  id: number;
  symbol: Symbol;
  start: PriceRecord;
  end: PriceRecord;
}
