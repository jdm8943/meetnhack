import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { User } from './user';
import { Volunteer_User } from './volunteer_user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = 'http://localhost:8080/users';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  /** GET hero by id. Will 404 if id not found */
  getUser(id: string): Observable<Volunteer_User> {
    const url = `${this.userUrl}/${id}`;
    return this.http.get<Volunteer_User>(url).pipe(
      tap(_ => console.log(`fetched hero id=${id}`)),
      catchError(this.handleError<Volunteer_User>(`getHero id=${id}`))
    );
  }

  login(user: User): Observable<User> {
    const url = `${this.userUrl}/login`;
    return this.http.post<User>(url, user, this.httpOptions)
      .pipe(
        tap((newUser: User) => console.log(`login user=${newUser.username}, pass=${newUser.password}`)),
        catchError(this.handleError<User>('login'))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  joinEvent(UID: String, eventID: number) {
    const url = `${this.userUrl}/${UID}/${eventID}`;
    return this.http.post<User>(url, this.httpOptions)
      .pipe(
        tap((newUser: User) => console.log()),
        catchError(this.handleError<User>('add event'))
      );
  }

  completeEvent(UID: String, eventID: number) {
    const url = `${this.userUrl}/${UID}/${eventID}`;
    return this.http.delete<User>(url, this.httpOptions)
      .pipe(
        tap((newUser: User) => console.log()),
        catchError(this.handleError<User>('add event'))
      );
  }
}