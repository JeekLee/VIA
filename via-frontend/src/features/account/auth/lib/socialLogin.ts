import { getOAuth2LoginUrl, oauth2Login } from '../api';
import type { OAuth2Provider, LoginResponse, LOGIN_STATUS_REDIRECT } from '../model';
import { generateCodeVerifier, storeCodeVerifier, getStoredCodeVerifier, clearCodeVerifier } from './pkce';

const OAUTH_STATE_KEY = 'oauth2_state';
const OAUTH_PROVIDER_KEY = 'oauth2_provider';

interface SocialLoginOptions {
  provider: OAuth2Provider;
  redirectPath?: string;
}

/**
 * Initiates social login by generating OAuth2 URL and redirecting user
 */
export async function initiateSocialLogin({
  provider,
  redirectPath = '/',
}: SocialLoginOptions): Promise<void> {
  const codeVerifier = generateCodeVerifier();
  storeCodeVerifier(codeVerifier);

  const redirectUri = `${window.location.origin}/auth/callback/${provider.toLowerCase()}`;

  const response = await getOAuth2LoginUrl({
    provider,
    codeVerifier,
    redirectPath,
    redirectUri,
  });

  // Store state and provider for verification on callback
  sessionStorage.setItem(OAUTH_STATE_KEY, response.state);
  sessionStorage.setItem(OAUTH_PROVIDER_KEY, provider);

  // Redirect to OAuth provider
  window.location.href = response.url;
}

interface CallbackParams {
  code: string;
  state: string;
}

/**
 * Handles OAuth callback after user returns from provider
 * Validates state and exchanges code for tokens
 */
export async function handleOAuthCallback({ code, state }: CallbackParams): Promise<LoginResponse> {
  const storedState = sessionStorage.getItem(OAUTH_STATE_KEY);
  const storedProvider = sessionStorage.getItem(OAUTH_PROVIDER_KEY) as OAuth2Provider | null;

  // Clear stored OAuth data
  sessionStorage.removeItem(OAUTH_STATE_KEY);
  sessionStorage.removeItem(OAUTH_PROVIDER_KEY);
  clearCodeVerifier();

  if (!storedState || storedState !== state) {
    throw new Error('Invalid OAuth state. Possible CSRF attack or expired session.');
  }

  if (!storedProvider) {
    throw new Error('OAuth provider not found. Please start the login process again.');
  }

  return oauth2Login({
    provider: storedProvider,
    code,
    state,
  });
}

/**
 * Gets redirect path based on login status
 */
export function getRedirectForStatus(
  status: keyof typeof LOGIN_STATUS_REDIRECT,
  statusRedirects: typeof LOGIN_STATUS_REDIRECT
): string {
  return statusRedirects[status];
}
