package org.studing.manager.security;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.core.context.SecurityContextHolder.getContextHolderStrategy;
import static org.springframework.security.oauth2.client.OAuth2AuthorizeRequest.withClientRegistrationId;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class OAuthClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    OAuth2AuthorizedClientManager authorizedClientManager;
    String registrationId;
    SecurityContextHolderStrategy securityContextHolderStrategy = getContextHolderStrategy();

    @Override
    public ClientHttpResponse intercept(
        HttpRequest request,
        byte[] body,
        ClientHttpRequestExecution execution) throws IOException {

        if (!request.getHeaders().containsKey(AUTHORIZATION)) {
            val authorizedClient = authorizedClientManager.authorize(
                withClientRegistrationId(registrationId)
                    .principal(securityContextHolderStrategy.getContext().getAuthentication())
                    .build());
            request.getHeaders().setBearerAuth(authorizedClient.getAccessToken().getTokenValue());
        }

        return execution.execute(request, body);
    }
}
