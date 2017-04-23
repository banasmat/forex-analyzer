export class TrendSearchData {
  start: Date;
  end: Date;
  symbol: string;

  constructor(start: Date, end: Date, symbol: string) {
    this.start = start;
    this.end = end;
    this.symbol = symbol;
  }
}
