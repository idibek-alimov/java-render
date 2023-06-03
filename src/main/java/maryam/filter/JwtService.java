package maryam.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {
    private static final String SECRET_KEY = "357538782F413F4428472B4B6150645367566B59703373367639792442264529\n";
    public String extractUsername(String token){

        return extractClaim(token,Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        log.info("claims");
        System.out.println(claims);
        System.out.println("subject");
        System.out.println(claims.getSubject());
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails,Integer month){
        return generateToken(new HashMap<>(),userDetails,month);
    }
//    public String generateAccessToken(UserDetails userDetails){
//        return generateToken(new HashMap<>(),userDetails,1);
//    }
//    public String generateRefreshToken(UserDetails userDetails){
//        return generateToken(new HashMap<>(),userDetails,2);
//    }
    public Boolean isTokenValid(String token,UserDetails userDetails){

        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()))&& !isTokenExpired(token);
    }
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    public String generateToken(
            Map<String,Object> extraClaims,
            UserDetails userDetails,
            Integer month
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24*30*month))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}