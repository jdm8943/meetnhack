import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';


@Injectable({ providedIn: 'root' })
export class EventService {

  private eventsUrl = 'http://localhost:8080/events'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient) { }

  /** GET events from the server */
  getEvents(): Observable<Event[]> {
    return this.http.get<Event[]>(this.eventsUrl)
      .pipe(
        catchError(this.handleError<Event[]>('getEvents', []))
      );
  }

  /** GET events by id. Return `undefined` when id not found */
  getEventNo404<Data>(id: number): Observable<Event> {
    const url = `${this.eventsUrl}/?id=${id}`;
    return this.http.get<Event[]>(url)
      .pipe(
        map(events => events[0]), // returns a {0|1} element array
        catchError(this.handleError<Event>(`getEvent id=${id}`))
      );
  }

  /** GET event by id. Will 404 if id not found */
  getEvent(id: number): Observable<Event> {
    const url = `${this.eventsUrl}/${id}`;
    return this.http.get<Event>(url).pipe(
      catchError(this.handleError<Event>(`getEvent id=${id}`))
    );
  }

  /* GET events whose name contains search term */
  searchEvents(term: string): Observable<Event[]> {
    if (!term.trim()) {
      // if not search term, return empty events array.
      return of([]);
    }
    return this.http.get<Event[]>(`${this.eventsUrl}/?name=${term}`).pipe(
      catchError(this.handleError<Event[]>('searchEvents', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new events to the server */
  addEvent(events: Event): Observable<Event> {
    return this.http.post<Event>(this.eventsUrl, events, this.httpOptions).pipe(
      catchError(this.handleError<Event>('addEvent'))
    );
  }

  /** DELETE: delete the events from the server */
  deleteEvent(id: number): Observable<Event> {
    const url = `${this.eventsUrl}/${id}`;

    return this.http.delete<Event>(url, this.httpOptions).pipe(
      catchError(this.handleError<Event>('deleteEvent'))
    );
  }

  /** PUT: update the events on the server */
  updateEvent(events: Event): Observable<Event> {
    return this.http.put<Event>(this.eventsUrl, events, this.httpOptions).pipe(
      catchError(this.handleError<Event>('updateEvent'))
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