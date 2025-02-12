import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

// Request models
export interface SearchByTitleRequest {
  title: string;
  status?: 'PENDING_APPROVAL' | 'APPROVED' | 'REJECTED' | 'PUBLISHED';
  sortBy?: string;
  sortOrder?: string;
  page?: number;
  size?: number;
}

export interface SearchByLatLongRequest {
  latitude: number;
  longitude: number;
  radius: number;
  maxPrice?: number;
  status?: 'PENDING_APPROVAL' | 'APPROVED' | 'REJECTED' | 'PUBLISHED';
  sortBy?: string;
  sortOrder?: string;
  page?: number;
  size?: number;
}

// Response models
export interface Ad {
  id?: number;
  title: string;
  description?: string;
  squareMeters?: number;
  price: number;
  location: string;
  latitude?: number;
  longitude?: number;
  status: string;
  accountId?: number;
}

export interface ErrorResponse {
  error: string;
}

@Injectable({
  providedIn: 'root',
})
export class SearchAdsService {
  private readonly searchByTitleUrl = '/api/search/searchByTitle';
  private readonly searchByLatLongUrl = '/api/search/searchByLatLong';
  private readonly baseUrl = '/api/search';

  constructor(private http: HttpClient) {}

  /**
   * Search ads by title.
   * @param request The search request payload.
   * @returns An observable with the search result or an error.
   */
  searchByTitle(request: SearchByTitleRequest): Observable<Ad[]> {
    const url = `${this.baseUrl}/searchByTitle`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<Ad[]>(url, request, { headers });
  }

  /**
   * Search ads by latitude and longitude.
   * @param request The search request payload.
   * @returns An observable with the search result or an error.
   */
  searchByLatLong(request: SearchByLatLongRequest): Observable<Ad[]> {
    const url = `${this.baseUrl}/searchByLatLong`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<Ad[]>(url, request, { headers });
  }

  /**
   * Search an ad by ID.
   * @param id The ID of the ad to search.
   * @returns An observable with the ad or an error.
   */
  searchById(id: number): Observable<Ad> {
    const url = `${this.baseUrl}/searchById/${id}`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Ad>(url, { headers });
  }
}