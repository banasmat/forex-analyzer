import {Component, OnInit} from '@angular/core';
import {Trend} from './trend';
import {TrendService} from './trend.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {IMyOptions} from 'mydatepicker';
import {Symbol} from './symbol';

@Component({
  selector: 'trends',
  templateUrl: './trend-search.component.html',
  styleUrls: ['./trend-search.component.css']
})
export class TrendSearchComponent implements OnInit {
  trends: Trend[];
  symbols: Symbol[] = [new Symbol('EURUSD')]; // We assume that eurusd is always available

  myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd-mm-yyyy',
    width: '250px',
    showClearDateBtn: false
  };

  private trendSearchForm: FormGroup;

  constructor(private trendService: TrendService, private formBuilder: FormBuilder) {}

  ngOnInit() {

    this.trendService.getSymbols().then(symbols => this.symbols = symbols);

    this.trendSearchForm = this.formBuilder.group({
      start: [{date: { year: 2016, month: 6, day: 1}}, Validators.required],
      end: [{date: { year: 2017, month: 1, day: 1}}, Validators.required],
      symbol: ['EURUSD', Validators.required] // TODO get available symbols from api
    });
    this.onSubmitReactiveForms();
  }

  getTrends(start: Date, end: Date, symbol: string): void {
    this.trendSearchForm.disable();
    this.trendService.getTrends(start, end, symbol).then(trends => {
      this.trends = trends;
      this.trendSearchForm.enable();
    });
  }

  onSubmitReactiveForms(): void {
    this.getTrends(
        new Date(
          this.trendSearchForm.controls['start'].value.date.year,
          this.trendSearchForm.controls['start'].value.date.month,
          this.trendSearchForm.controls['start'].value.date.day
        ),
        new Date(
          this.trendSearchForm.controls['end'].value.date.year,
          this.trendSearchForm.controls['end'].value.date.month,
          this.trendSearchForm.controls['end'].value.date.day
        ),
        this.trendSearchForm.controls['symbol'].value
      );
  }
}
