import { HttpClient, HttpEvent, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of } from 'rxjs';
import { Event } from './event';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private eventUrl = 'http://localhost:8080/events';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json'})
  }

  constructor(private http: HttpClient) { }

  addEvent(event: Event): Observable<Event>{
    return this.http.post<Event>(this.eventUrl, event, this.httpOptions);
  }
}
