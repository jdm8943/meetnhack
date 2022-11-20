import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { OrgEvent } from './orgEvent';


@Injectable({ providedIn: 'root' })
export class EventsService {

  private eventsUrl = 'http://localhost:8080/events'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient) { }

  /** GET events from the server */
  getEvents(): Observable<OrgEvent[]> {
    return this.http.get<OrgEvent[]>(this.eventsUrl)
      .pipe(
        catchError(this.handleError<OrgEvent[]>('getEvents', []))
      );
  }

  /** GET events by id. Return `undefined` when id not found */
  getEventNo404<Data>(eventID: number): Observable<OrgEvent> {
    const url = `${this.eventsUrl}/?eventID=${eventID}`;
    return this.http.get<OrgEvent[]>(url)
      .pipe(
        map(events => events[0]), // returns a {0|1} element array
        catchError(this.handleError<OrgEvent>(`getEvent id=${eventID}`))
      );
  }

  /** GET event by id. Will 404 if id not found */
  getEvent(eventID: number): Observable<OrgEvent> {
    const url = `${this.eventsUrl}/${eventID}`;
    return this.http.get<OrgEvent>(url).pipe(
      catchError(this.handleError<OrgEvent>(`getEvent id=${eventID}`))
    );
  }

  /* GET events whose name contains search term */
  searchEvents(term: string): Observable<OrgEvent[]> {
    if (!term.trim()) {
      // if not search term, return empty events array.
      return of([]);
    }
    return this.http.get<OrgEvent[]>(`${this.eventsUrl}/?eventName=${term}`).pipe(
      catchError(this.handleError<OrgEvent[]>('searchEvents', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new event to the server */
  addEvent(orgEvent: OrgEvent): Observable<OrgEvent> {
    console.log("add event called")
    return this.http.post<OrgEvent>(this.eventsUrl, orgEvent, this.httpOptions).pipe(
      catchError(this.handleError<OrgEvent>('addEvent'))
    );
  }

  /** DELETE: delete the event from the server */
  deleteEvent(eventID: number): Observable<OrgEvent> {
    const url = `${this.eventsUrl}/${eventID}`;

    return this.http.delete<OrgEvent>(url, this.httpOptions).pipe(
      catchError(this.handleError<OrgEvent>('deleteEvent'))
    );
  }

  /** PUT: update the event on the server */
  updateEvent(events: OrgEvent): Observable<OrgEvent> {
    return this.http.put<OrgEvent>(this.eventsUrl, events, this.httpOptions).pipe(
      catchError(this.handleError<OrgEvent>('updateEvent'))
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