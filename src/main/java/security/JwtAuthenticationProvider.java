package security;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
 
	@Value("#{config['signing.key']}")
	String signingKey;
	
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	
    	String jwt = (String)authentication.getCredentials();
    	
    	Claims body = Jwts.parser()
    	  .setSigningKey(signingKey)
    	  .parseClaimsJws(jwt).getBody();
    	
    	Integer userId = Integer.parseInt((String) body.get("id"));
    	List<GrantedAuthority> authorityList = AuthorityUtils
    			.commaSeparatedStringToAuthorityList((String) body.get("role"));
    	
        return new UsernamePasswordAuthenticationToken(userId, jwt, authorityList);

    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
}
