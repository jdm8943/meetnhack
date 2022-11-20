import { HttpClient, HttpEvent, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of } from 'rxjs';
import { OrgEvent } from './orgEvent';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private eventUrl = 'http://localhost:8080/events';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  constructor(private http: HttpClient) { }

  addEvent(event: OrgEvent): Observable<OrgEvent> {
    console.log(event);
    return this.http.post<OrgEvent>(this.eventUrl, event, this.httpOptions);
  }
}
