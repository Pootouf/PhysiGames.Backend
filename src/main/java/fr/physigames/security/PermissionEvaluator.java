package fr.physigames.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PermissionEvaluator {

    public boolean hasPermission(Authentication authentication, String resource, String scope) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        var permissions = (List<Map<String, Object>>) jwt.getClaimAsMap("authorization").get("permissions");

        return permissions.stream()
                .anyMatch(p -> resource.equals(p.get("rsname")) &&
                        ((List<String>) p.get("scopes")).contains(scope));
    }
}
