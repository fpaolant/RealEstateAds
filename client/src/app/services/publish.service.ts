import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { interval, Observable, switchMap, takeWhile, tap } from 'rxjs';


export interface GetAdsRequest {
  page: number;
  size: number;
  sortBy?: string;
  sortOrder?: string;
  status?: 'PENDING_APPROVAL' | 'APPROVED' | 'REJECTED' | 'PUBLISHED';
}

export function getPossibleStatuses(): GetAdsRequest['status'][] {
  return ['PENDING_APPROVAL', 'APPROVED', 'REJECTED', 'PUBLISHED'];
}

export interface AdPaginatedResponse {
  content: AdResponse[];
  currentPage: number;
  totalPages: number;
  totalItems: number;
}

export interface AdResponse {
  id: number;
  title: string;
  description: string;
  squareMeters: number;
  price: number;
  location: string;
  latitude: number;
  longitude: number;
  status: string;
  accountId: number;
}

export interface AdStatusResponse {
  status: string;
}

export interface PublishGetAdsResponse {
  adsResponse: AdResponse[];
}

export interface PublishAdRejectRequest {
  reason: string;
}

export interface OpenPublishAccountRequest {
  username: string;
  password: string;
  email: string;
  name: string;
  surname: string;
  mobile: string;
}

export interface OpenPublishAccountResponse {
  id: number;
  username: string;
  walletId: string; // UUID as string
}

export interface PublishAdRequest {
  accountId: number | null;
  latitude: number | null;
  location: string |null;
  longitude: number | null;
  price: number | null;
  squareMeters: number | null;
  title: string | null;
  description?: string |null;
}

export interface PublishAdResponse {
  adId: number; // int64
  title: string;
}




@Injectable({
  providedIn: 'root'
})
export class PublishService {
  private baseUrl = '/api/publish';

  constructor(private http: HttpClient) {}

  /**
   * Approve an ad.
   * @param adId - ID of the ad to approve.
   */
  approveAd(adId: number): Observable<any> {
    const url = `${this.baseUrl}/ad/${adId}/approve`;
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json',
      'Use-Auth': 'true' // tells interceptor to include bearer token
    });
    return this.http.put<any>(url, null, { headers });
  }

  /**
   * Publish a new ad.
   * @param publishAdRequest - Object containing ad details.
   */
  publishAd(publishAdRequest: PublishAdRequest): Observable<PublishAdResponse> {
    const url = `${this.baseUrl}/ad`;
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json',
      'Use-Auth': 'true' // tells interceptor to include bearer token
    });
    return this.http.post<PublishAdResponse>(url, publishAdRequest, { headers });
  }

  /**
   * Get the status of an ad.
   * @param adId 
   * @returns 
   */
  getAdStatus(adId: number): Observable<AdStatusResponse> {
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json',
      'Use-Auth': 'true' // tells interceptor to include bearer token
    });
    return this.http.get<AdStatusResponse>(`${this.baseUrl}/ad/${adId}/status`, { headers });
  }

  /**
   * Poll the status of an ad.
   * @param adId - ID of the ad to poll.
   * @param pollInterval - Interval in milliseconds between polls.
   * @returns Observable that emits the status of the ad.
   *         Completes when the status is no longer 'PENDING_APPROVAL'.
   *        Errors if the ad does not exist.
   *       Unsubscribing stops the polling.
   *     The first value emitted is the current status of the ad.
   */
  pollStatus(adId: number, pollInterval : number = 2000): Observable<AdStatusResponse> {
    return interval(pollInterval).pipe( // Polling ogni 2 secondi
      switchMap(() => this.getAdStatus(adId)),
      takeWhile(response => response.status === 'PENDING_APPROVAL', true), // Ferma il polling quando lo stato cambia
      tap(response => console.log('Stato attuale:', response.status))
    );
  }


  /**
   * Reject an ad.
   * @param adId - ID of the ad to reject.
   * @param rejectionReason - Reason for rejection.
   */
  rejectAd(adId: number, rejectionReason: PublishAdRejectRequest ): Observable<any> {
    const url = `${this.baseUrl}/ad/${adId}/reject`;
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json',
      'Use-Auth': 'true' // tells interceptor to include bearer token
    });
    return this.http.put<any>(url, rejectionReason, { headers });
  }

  getAds(request: GetAdsRequest): Observable<AdPaginatedResponse> {
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json',
      'Use-Auth': 'true' // tells interceptor to include bearer token
    });
    return this.http.post<AdPaginatedResponse>(
      `${this.baseUrl}/ads`,
      request,
      { headers }
    );
  }

  getAdsAccount(accountId: number): Observable<PublishGetAdsResponse> {
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json',
      'Use-Auth': 'true' // tells interceptor to include bearer token
    });
    return this.http.get<PublishGetAdsResponse>(
      `${this.baseUrl}/ads/account/${accountId}`,
      { headers }
    );
  }

  /**
   * Open a new publish account.
   * @param openPublishAccountRequest - Object containing account details.
   */
  openPublishAccount(openPublishAccountRequest: OpenPublishAccountRequest): Observable<OpenPublishAccountResponse> {
    const url = `${this.baseUrl}/account`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Use-Auth': 'true' // tells interceptor to include bearer token
    });
    return this.http.post<OpenPublishAccountResponse>(url, openPublishAccountRequest, { headers });
  }
}
