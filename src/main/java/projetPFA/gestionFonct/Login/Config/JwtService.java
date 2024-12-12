package projetPFA.gestionFonct.Login.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "I3NGfDkgp51Op4rOqh6+a0/EwYoU/r9LTEY0jzBPIm95tKUZvp+9PoG7uXuwLcVw4H0QwIEqbiuxnK7GFcIeLPVz/72rOeoXor2iqztwGB9RpCHRQZLH5BelGc7FIgZrFhwBgx4BZlHdt5L39F3oTuluSs5SWnhTK+HJ8H4WBSMfeoMR96GR8ble0EjphEfYXhjQBwU9OMpXJtuf1YK0m+vh19T2wEedJsWJX2wfIAX4DY3iHTTSRkB/NmOr2OgvVirgazzXMR+dcnfOxihhGhTQNjWvi0DHALoB320XEDPbe4scDKfo4AvGnR0MoxaPsgMakL/0j3nWQWKsZ9YcpsJnIU2V0A6jy4qH4Rb0oRr4GLQjmLVWQJgu2MENS47n0e0L1hgoCSgBfOB0oe4iJ3gjJfpwv5k9K4i4GwWTtRZLHUO0UCnmDUuGYScBzF1VebUTDJ7H7KooFbxSn/jlqZh+BxnXcMfHOGejP2ktBOT876IT50ckKV283U68quXw4Ikjq7WUOh+yMk1ABQqewhq/UZM23AkdvCg7DMp5hsxHwIlvgDkGCEJ1Xf30wg3jaMaofEBZg/yaOnfro8xvbUEhI2AcVp3TGPIp2L4MVUfoaKlba5S+nQtO3Nn4eiGbGbW/iG5jtsqDWaqKNsMr3Q6VWc2YP87Qvm0Fc3pC9kIHj1KwHL2Bv0x855/NXj++" ;
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return  extractClaim(token, Claims::getSubject);
    }
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
      return Jwts
              .parserBuilder()
              .setSigningKey(getSignInKey())
              .build()
              .parseClaimsJws(token)
              .getBody();
    }




    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
