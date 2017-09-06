import {PriceRecord} from './price-record';
import {Symbol} from './symbol';
import {ForexCalendarEvent} from './forex-calendar-event';
import {ForexCalendarEventAssoc} from './forex-calendar-event-assoc';

export class Trend {
  id: number;
  symbol: Symbol;
  start: PriceRecord;
  end: PriceRecord;
  priceRecords: PriceRecord[];
  forexCalendarEventAssocs: ForexCalendarEventAssoc[];
}
