/**
 * PKCE (Proof Key for Code Exchange) utilities for OAuth2 authentication
 *
 * Code verifier: Random string 43-128 characters using [A-Za-z0-9-._~]
 * Code challenge: Base64URL encoded SHA-256 hash of code verifier
 */

const PKCE_STORAGE_KEY = 'oauth2_code_verifier';

/**
 * Generates a cryptographically secure random code verifier
 * Must be 43-128 characters using [A-Za-z0-9-._~]
 */
export function generateCodeVerifier(length: number = 64): string {
  const charset = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~';
  const randomValues = new Uint8Array(length);
  crypto.getRandomValues(randomValues);

  return Array.from(randomValues)
    .map((value) => charset[value % charset.length])
    .join('');
}

/**
 * Stores the code verifier in sessionStorage for later use during callback
 */
export function storeCodeVerifier(verifier: string): void {
  if (typeof window !== 'undefined') {
    sessionStorage.setItem(PKCE_STORAGE_KEY, verifier);
  }
}

/**
 * Retrieves and clears the stored code verifier
 */
export function getStoredCodeVerifier(): string | null {
  if (typeof window === 'undefined') {
    return null;
  }

  const verifier = sessionStorage.getItem(PKCE_STORAGE_KEY);
  sessionStorage.removeItem(PKCE_STORAGE_KEY);
  return verifier;
}

/**
 * Clears the stored code verifier without returning it
 */
export function clearCodeVerifier(): void {
  if (typeof window !== 'undefined') {
    sessionStorage.removeItem(PKCE_STORAGE_KEY);
  }
}
