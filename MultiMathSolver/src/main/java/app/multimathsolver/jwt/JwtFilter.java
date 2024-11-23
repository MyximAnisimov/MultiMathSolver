package app.multimathsolver.jwt;

import app.multimathsolver.user.UserDetailsServiceImpl;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtTokenRepository;

    @Autowired
    private UserDetailsServiceImpl userDetail;
//    public JwtFilter(CsrfTokenRepository tokenRepository, HandlerExceptionResolver handlerExceptionResolver){
//        this.tokenRepository = tokenRepository;
//        this.resolver = handlerExceptionResolver;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, java.io.IOException {
//        request.setAttribute(HttpServletResponse.class.getName(), response);
//        CsrfToken csrfToken = this.tokenRepository.loadToken(request);
//        boolean missingToken = csrfToken == null;
//        if (missingToken){
//            csrfToken = this.tokenRepository.generateToken(request);
//            this.tokenRepository.saveToken(csrfToken, request, response);
//        }
//        request.setAttribute(CsrfToken.class.getName(), csrfToken);
//        request.setAttribute(csrfToken.getParameterName(), csrfToken);
//        if(request.getServletPath().equals("/user/login")){
//            try{
//                filterChain.doFilter(request, response);
//            } catch(Exception e){
//                resolver.resolveException(request,response, null, new MissingCsrfTokenException(csrfToken.getToken()));
//
//            }
//        }
//        else {
//            String actualToken = request.getHeader(csrfToken.getHeaderName());
//            if(actualToken == null){
//                actualToken = request.getHeader(csrfToken.getHeaderName());
//            }
//            try{
//                if (!StringUtils.isEmpty(actualToken)){
//                    Jwts.parser().setSigningKey((Key) tokenRepository).parseClaimsJws(actualToken);
//                    filterChain.doFilter(request, response);
//                }
//            } catch (java.io.IOException e) {
//                if(this.logger.isDebugEnabled()) {
//                    this.logger.debug("Invalid CSRF token");
//                }
//
//                if(missingToken){
//                    resolver.resolveException(request, response, null, new MissingCsrfTokenException(actualToken));
//                }
//                else{
//                    resolver.resolveException(request, response, null, new InvalidCsrfTokenException(csrfToken, actualToken));
//                }
//            }
//        }
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtTokenRepository.validateJwtToken(jwt)) {
                String username = jwtTokenRepository.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetail.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception e){
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    public String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        if(!StringUtils.isEmpty(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7);
        }
        return null;
    }

}
