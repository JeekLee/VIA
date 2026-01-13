import { cookies } from 'next/headers';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

export class ServerApiError extends Error {
  constructor(
    public status: number,
    message: string
  ) {
    super(message);
    this.name = 'ServerApiError';
  }
}

interface ServerRequestOptions extends Omit<RequestInit, 'body'> {
  body?: unknown;
}

/**
 * Server-side API request function that forwards cookies to the backend
 * Use this in Server Components and Server Actions
 */
async function serverRequest<T>(
  endpoint: string,
  options: ServerRequestOptions = {}
): Promise<T> {
  const { body, headers, ...restOptions } = options;
  const cookieStore = await cookies();

  // Get auth cookies to forward
  const accessToken = cookieStore.get('access-token')?.value;
  const refreshToken = cookieStore.get('refresh-token')?.value;

  // Build cookie header
  const cookieHeader = [
    accessToken ? `access-token=${accessToken}` : '',
    refreshToken ? `refresh-token=${refreshToken}` : '',
  ]
    .filter(Boolean)
    .join('; ');

  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...restOptions,
    headers: {
      'Content-Type': 'application/json',
      ...(cookieHeader && { Cookie: cookieHeader }),
      ...headers,
    },
    body: body ? JSON.stringify(body) : undefined,
    cache: 'no-store',
  });

  if (!response.ok) {
    const errorMessage = await response.text().catch(() => 'Unknown error');
    throw new ServerApiError(response.status, errorMessage);
  }

  const contentType = response.headers.get('content-type');
  if (contentType?.includes('application/json')) {
    return response.json();
  }

  return {} as T;
}

export const serverApiClient = {
  get: <T>(endpoint: string, options?: Omit<ServerRequestOptions, 'method' | 'body'>) =>
    serverRequest<T>(endpoint, { ...options, method: 'GET' }),

  post: <T>(endpoint: string, body?: unknown, options?: Omit<ServerRequestOptions, 'method' | 'body'>) =>
    serverRequest<T>(endpoint, { ...options, method: 'POST', body }),

  put: <T>(endpoint: string, body?: unknown, options?: Omit<ServerRequestOptions, 'method' | 'body'>) =>
    serverRequest<T>(endpoint, { ...options, method: 'PUT', body }),

  delete: <T>(endpoint: string, options?: Omit<ServerRequestOptions, 'method' | 'body'>) =>
    serverRequest<T>(endpoint, { ...options, method: 'DELETE' }),
};
