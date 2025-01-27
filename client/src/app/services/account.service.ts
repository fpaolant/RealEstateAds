import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

// Requests
export interface UserCredentials {
  username: string;
  password: string;
}
export interface OpenAccountRequest {
  username: string;
  password: string;
  email: string;
  name: string;
  surname: string;
  mobile: string;
}

// Responses
export interface TokenResponse {
  token: string;
}

export interface ErrorResponse {
  error: string;
}

export interface AccountResponse {
  id: number;
  name: string;
  surname: string;
  username: string;
  email: string;
  mobile: string;
  role: 'ADMIN' | 'USER';
  updateDate: string;
  createDate: string;
}

export function getPossibleRoles(): AccountResponse['role'][] {
  return ['ADMIN', 'USER'];
}

export interface GetAccountsRequest {
  page: number;
  size: number;
  sortBy?: string;
  sortOrder?: string;
}

export interface AccountsPaginatedResponse {
  content: AccountResponse[];
  currentPage: number;
  totalPages: number;
  totalItems: number;
}


@Injectable({
  providedIn: 'root',
})
export class AccountService {
  private readonly tokenKey = 'auth_token';
  private readonly baseUrl = '/api/account';

  constructor(private http: HttpClient) {}

  /**
   * Effettua il login dell'utente e restituisce il token JWT.
   * @param credentials Credenziali dell'utente (username e password).
   * @returns Un Observable contenente il TokenResponse o un errore.
   */
  login(credentials: UserCredentials): Observable<TokenResponse> {
    return this.http.post<TokenResponse>(`${this.baseUrl}/login`, credentials);
  }

  /**
   * Verifica la validità di un token JWT.
   * @param token Il token JWT da verificare.
   * @returns Un Observable che emette `true` se il token è valido, altrimenti `false`.
   */
  checkTokenValidity(token: TokenResponse): Observable<boolean> {
    return this.http.post<boolean>(`${this.baseUrl}/check-token`, token);
  }

  
  openAccountUser(accountRequest: OpenAccountRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/user-account`, accountRequest).pipe(
      tap({
        next: (response: any) => {
          console.log('User account created successfully', response);
        },
        error: (error: any) => {
          console.error('Error creating user account', error);
        }
      })
    );
  }
  

  getAccount(id: number): Observable<AccountResponse> {
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json',
      'Use-Auth': 'true' // tells interceptor to include bearer token
    });

    return this.http.get<AccountResponse>(`${this.baseUrl}/${id}`, { headers });
  }

  getAccounts(request: GetAccountsRequest): Observable<AccountsPaginatedResponse> {
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json',
      'Use-Auth': 'true' // tells interceptor to include bearer token
    });
    return this.http.post<AccountsPaginatedResponse>(
      `${this.baseUrl}/accounts`,
      request,
      { headers }
    );
  }

  promoteAccount(id: number): Observable<any> {
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json',
      'Use-Auth': 'true' // tells interceptor to include bearer token
    });
    return this.http.patch<void>(`${this.baseUrl}/${id}/promote`, null, {headers});
  }

  demoteAccount(id: number): Observable<any> {
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json',
      'Use-Auth': 'true' // tells interceptor to include bearer token
    });
    return this.http.patch<void>(`${this.baseUrl}/${id}/demote`, null, {headers});
  }

 
}
