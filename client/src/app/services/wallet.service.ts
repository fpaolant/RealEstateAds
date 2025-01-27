import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


export interface WalletResponse {
  id: number;
  walletId: string;
  accountId: number;
  balance: number;
  updateDate: string;
  createDate: string;
}

export interface ErrorResponse {
  detailMessageArguments?: any[];
  detailMessageCode?: string;
  titleMessageCode?: string;
  typeMessageCode?: string;
  statusCode?: string;
  body?: any;
  headers?: any;
}

export interface WalletRechargeRequest {
  walletId: string;
  amount: number;
}


@Injectable({
  providedIn: 'root'
})
export class WalletService {
  private baseUrl = '/api/wallet';
  constructor(private http: HttpClient) {}

  /**
   * Get Wallet Details by accountId
   * @param accountId The ID of the account
   * @returns Observable containing WalletResponse or an error
   */
  getWalletByAccountId(accountId: number): Observable<WalletResponse> {
    const url = `${this.baseUrl}/account/${accountId}`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Use-Auth': 'true' // tells interceptor to include bearer token
    });
    return this.http.get<WalletResponse>(url, { headers });
  }

  rechargeWallet(request: WalletRechargeRequest): Observable<WalletResponse> {
    const url = `${this.baseUrl}/recharge`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Use-Auth': 'true' // tells interceptor to include bearer token
    });
    return this.http.post<WalletResponse>(url, request, { headers });
  }
}
