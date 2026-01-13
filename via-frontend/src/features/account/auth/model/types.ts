// OAuth2 Provider types
export type OAuth2Provider = 'GOOGLE' | 'KAKAO' | 'FACEBOOK' | 'APPLE';

// Login status types
export type LoginStatus =
  | 'ALLOWED'
  | 'BLOCKED_NEW_MEMBER'
  | 'BLOCKED_TERMS_REQUIRED'
  | 'BLOCKED_PROFILE_REQUIRED'
  | 'BLOCKED_RESIGN_REQUESTED';

// Request types
export interface OAuth2UrlRequest {
  provider: OAuth2Provider;
  codeVerifier: string;
  redirectPath: string;
  redirectUri: string;
}

export interface OAuth2LoginRequest {
  provider: OAuth2Provider;
  code: string;
  state: string;
}

// Response types
export interface OAuth2LoginUrl {
  url: string;
  state: string;
  provider: OAuth2Provider;
  expiresAt: string;
}

export interface LoginResponse {
  redirectPath?: string;
  status: LoginStatus;
}

// Status redirect mapping
export const LOGIN_STATUS_REDIRECT: Record<LoginStatus, string> = {
  ALLOWED: '/',
  BLOCKED_NEW_MEMBER: '/signup',
  BLOCKED_TERMS_REQUIRED: '/terms',
  BLOCKED_PROFILE_REQUIRED: '/profile/setup',
  BLOCKED_RESIGN_REQUESTED: '/resign/cancel',
};
