import { apiClient } from '@/shared/api';
import type {
  OAuth2UrlRequest,
  OAuth2LoginRequest,
  OAuth2LoginUrl,
  LoginResponse,
} from '../model';

/**
 * OAuth2 Authentication API
 *
 * Flow:
 * 1. Call getOAuth2LoginUrl to get authorization URL with PKCE
 * 2. Redirect user to OAuth provider
 * 3. On callback, call oauth2Login with code and state from provider
 */

/**
 * Generates OAuth2 authorization URL for the specified provider
 * Uses PKCE flow with code verifier for security
 */
export async function getOAuth2LoginUrl(request: OAuth2UrlRequest): Promise<OAuth2LoginUrl> {
  return apiClient.post<OAuth2LoginUrl>('/auth/oauth2/url', request);
}

/**
 * Completes OAuth2 login with authorization code from provider
 * Returns login response with status indicating next steps
 */
export async function oauth2Login(request: OAuth2LoginRequest): Promise<LoginResponse> {
  return apiClient.post<LoginResponse>('/auth/oauth2/login', request);
}

/**
 * Refreshes the access token using stored refresh token
 */
export async function refreshToken(): Promise<LoginResponse> {
  return apiClient.post<LoginResponse>('/auth/token/refresh');
}

/**
 * Logs out the user and clears authentication cookies
 */
export async function logout(): Promise<void> {
  return apiClient.post<void>('/auth/logout');
}
