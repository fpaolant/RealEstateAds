import { inject, Injectable } from '@angular/core';
import { AccountService, TokenResponse, UserCredentials } from './account.service';
import { jwtDecode } from 'jwt-decode';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  accountService = inject(AccountService);
  private readonly tokenKey = 'auth_token';

  login(credentials: UserCredentials): Observable<TokenResponse> {
    return this.accountService.login(credentials).pipe(
      tap((response) => this.saveToken(response.token))
    );
  }

  logout(): void {
    this.removeToken();
  }

  isLogged(): boolean {
    return !!this.getToken();
  }

  getRole(): string | null {
    const decodedToken = this.decodeToken();
    return decodedToken?.role || null;
  }

  getUsername(): string | null {
    const decodedToken = this.decodeToken();
    return decodedToken?.sub || null;
  }

  getUserId(): number | null {
    const decodedToken = this.decodeToken();
    return decodedToken?.identifier || null;
  }

  // Metodi per la gestione del token JWT
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  saveToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  removeToken(): void {
    localStorage.removeItem(this.tokenKey);
  }

  isTokenValid(): Observable<boolean> {
    const token = this.getToken();
    
    if (token) {
      return this.accountService.checkTokenValidity({ token }).pipe(
        tap((isValid) => {
          if (!isValid) {
            this.removeToken();
          }
        })
      );     
    } else {
      return new Observable<boolean>((observer) => {
        observer.next(false);
        observer.complete();
      });
    }
  }

  decodeToken(): any | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      return jwtDecode(token);
    } catch (error) {
      console.error('Errore nella decodifica del token JWT', error);
      return null;
    }
  }
}
