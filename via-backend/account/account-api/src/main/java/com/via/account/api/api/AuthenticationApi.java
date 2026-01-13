package com.via.account.api.api;

import com.via.account.api.request.OAuth2LogInRequest;
import com.via.account.api.request.OAuth2UrlRequest;
import com.via.account.api.response.LoginResponse;
import com.via.account.app.dto.OAuth2LoginUrl;
import com.via.support.security.annotation.RequireAuthority;
import com.via.support.security.enums.Authority;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Authentication", description = "User Authentication API")
@RequestMapping("/auth")
@Validated
public interface AuthenticationApi {
    @Operation(
            summary = "Generate OAuth2 authorization URL",
            description = """
                     ## Key Features

                     Generates an authentication URL to start the OAuth2 authentication process.

                     Uses PKCE method, generating a `code challenge` from the client-generated `code verifier`
                     and including it in the authentication URL.

                     Redirecting the user to the generated URL will navigate to the OAuth2 provider's authentication page.
                     """
    )
    @PostMapping("/oauth2/url")
    ResponseEntity<OAuth2LoginUrl> getOAuth2LogInUrl(@Valid @RequestBody OAuth2UrlRequest request);

    @Operation(
            summary = "Complete OAuth2 login process",
            description = """
                    ## Key Features

                    Completes the OAuth2 authentication process and handles login.

                    After verifying the authorization `code` and `state` received from the OAuth2 provider,
                    obtains the OAuth2 provider's AccessToken to retrieve user profile information and proceeds with login or member creation.

                    During the login process, user validity is evaluated.

                    - Normal user: Service `AccessToken` and `RefreshToken` are stored and `status` is `ALLOWED`.
                    - Abnormal user: If essential requirements like terms consent are not processed, `TemporaryToken` and `RefreshToken` are issued.

                    When `status` is one of the following, redirect to the appropriate page:

                    - BLOCKED_NEW_MEMBER: Navigate to signup page
                    - BLOCKED_TERMS_REQUIRED: Navigate to terms consent page
                    - BLOCKED_PROFILE_REQUIRED: Navigate to profile creation page (not implemented)
                    - BLOCKED_RESIGN_REQUESTED: Navigate to withdrawal cancellation page (not implemented)

                    For non-existent members, a new member is created immediately.

                    If an SNS profile image exists, it will be set as the profile image.
                    If no profile image exists, it will be saved as null.

                    In PKCE flow, `state` is used for CSRF attack prevention and session maintenance.
                    """
    )
    @PostMapping("/oauth2/login")
    ResponseEntity<LoginResponse> oAuth2LogIn(@Valid @RequestBody OAuth2LogInRequest request);

    @Operation(
            summary = "Log out user and clear authentication",
            description = """
               ## Key Features

               Handles user logout and clears authentication information.

               Terminates the current logged-in user's session and invalidates the stored refresh token.

               Also deletes the client's authentication cookies (access token, refresh token)
               to perform a complete logout.

               After logout, re-login is required to access protected resources.
               """
    )
    @PostMapping("/logout")
    @RequireAuthority(Authority.USER)
    ResponseEntity<Void> logOut(HttpServletRequest request);

    @Operation(
            summary = "Refresh access token",
            description = """
                    ## Key Features

                    Refreshes an expired access token.

                    Uses a valid refresh token to obtain a new access token.

                    During the login process, user validity is evaluated.

                    - Normal user: Service `AccessToken` and `RefreshToken` are stored and `status` is `ALLOWED`.
                    - Abnormal user: If essential requirements like terms consent are not processed, `TemporaryToken` and `RefreshToken` are issued.

                    When `status` is one of the following, redirect to the appropriate page:

                    - BLOCKED_NEW_MEMBER: Navigate to signup page
                    - BLOCKED_TERMS_REQUIRED: Navigate to terms consent page
                    - BLOCKED_PROFILE_REQUIRED: Navigate to profile creation page (not implemented)
                    - BLOCKED_RESIGN_REQUESTED: Navigate to withdrawal cancellation page (not implemented)

                    Temporary tokens can be used when calling APIs that allow temporary user permissions.
                    Once processing with the temporary token is complete, call this API again to obtain an AccessToken.

                    Newly issued tokens are automatically set in response cookies.
                    """
    )
    @PostMapping("/token/refresh")
    ResponseEntity<LoginResponse> refreshAuthenticationToken(HttpServletRequest request);
}
