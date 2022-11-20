import { HttpClient, HttpEvent, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of } from 'rxjs';
import { Discount } from './discount';

@Injectable({
  providedIn: 'root'
})
export class DiscountsService {

  private discountUrl = 'http://localhost:8080/discounts';

  constructor(private http: HttpClient) { }

  getDiscounts(): Observable<Discount[]>{
    return this.http.get<Discount[]>(this.discountUrl)
    .pipe(
      catchError(this.handleError<Discount[]>('getDiscounts', []))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
   private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
