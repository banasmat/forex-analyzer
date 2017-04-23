import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {IMyOptions} from 'mydatepicker';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TrendSearchData} from './trend-search-data';


@Component({
  selector: 'trend-search-form',
  templateUrl: './trend-search-form.component.html',
  styleUrls: ['./trend-search-form.component.css']
})
export class TrendSearchFormComponent implements OnInit {

  myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd-mm-yyyy',
    width: '250px',
    showClearDateBtn: false
  };

  private trendSearchForm: FormGroup;

  @Output() submitEvent: EventEmitter<TrendSearchData> = new EventEmitter<TrendSearchData>();

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.trendSearchForm = this.formBuilder.group({
      start: [{date: { year: 2016, month: 1, day: 1}}, Validators.required],
      end: [{date: { year: 2017, month: 1, day: 1}}, Validators.required],
      symbol: ['EURUSD', Validators.required] // TODO get available symbols from api
    });
  }

  onSubmitReactiveForms(): void {
    this.submitEvent.emit(
      new TrendSearchData(
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
      ));
  }
}

