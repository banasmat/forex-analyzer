import {Component, OnInit} from '@angular/core';
import {IMyOptions} from 'mydatepicker';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';


@Component({
  selector: 'trend-search-form',
  templateUrl: './trend-search-form.component.html',
  styleUrls: ['./trend-search-form.component.css']
})
export class TrendSearchFormComponent implements OnInit{

  myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd-mm-yyyy',
    width: '250px',
    showClearDateBtn: false
  };

  private trendSearchForm: FormGroup;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.trendSearchForm = this.formBuilder.group({
      // Empty string means no initial value. Can be also specific date for
      // example: {date: {year: 2018, month: 10, day: 9}} which sets this date to initial
      // value.

      start: [ <Object> {date: { year: 2016, month: 1, day: 1}}, Validators.required],
      end: [ <Object> {date: { year: 2016, month: 3, day: 31}}, Validators.required],
      symbol: ['EURUSD', Validators.required]

    });
  }

  setDate(): void {
    // Set today date using the setValue function
    let date = new Date();
    this.trendSearchForm.setValue({myDate: {
      date: {
        year: date.getFullYear(),
        month: date.getMonth() + 1,
        day: date.getDate()}
    }});
  }

  // clearDate(): void {
  //   // Clear the date using the setValue function
  //   this.trendSearchForm.setValue({myDate: ''});
  // }

  // Defaults
  // private model: Object = {
  //   start: {date: { year: 2016, month: 1, day: 1}},
  //   end: {date: { year: 2016, month: 3, day: 31}},
  //   symbol: 'EURUSD'
  // };

}

