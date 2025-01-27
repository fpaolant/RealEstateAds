import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


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
