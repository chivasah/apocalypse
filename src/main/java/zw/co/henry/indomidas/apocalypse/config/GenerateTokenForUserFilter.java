package zw.co.henry.indomidas.apocalypse.config;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.*;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import zw.co.henry.indomidas.apocalypse.identity.TokenUser;
import zw.co.henry.indomidas.apocalypse.identity.TokenUtil;
import zw.co.henry.indomidas.apocalypse.model.response.OperationResponse;
import zw.co.henry.indomidas.apocalypse.model.session.SessionItem;
import zw.co.henry.indomidas.apocalypse.model.session.SessionResponse;

import com.fasterxml.jackson.databind.*;


/* This filter maps to /session and tries to validate the username and password */
@Slf4j
public class GenerateTokenForUserFilter extends AbstractAuthenticationProcessingFilter {

    private TokenUtil tokenUtil;

    protected GenerateTokenForUserFilter(String urlMapping, AuthenticationManager authenticationManager, TokenUtil tokenUtil) {
        super(new AntPathRequestMatcher(urlMapping));
        setAuthenticationManager(authenticationManager);
        this.tokenUtil = tokenUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException, JSONException {
        log.info("attemptAuthentication()");
        try{
            String jsonString = IOUtils.toString(request.getInputStream(), "UTF-8");
            /* using org.json */
            JSONObject userJSON = new JSONObject(jsonString);
            String username = userJSON.getString("username");
            String password = userJSON.getString("password");
            String browser = request.getHeader("User-Agent")!= null?request.getHeader("User-Agent"):"";
            String ip = request.getRemoteAddr();
            log.info("\nip:{} \nbrowser:{} \n----",ip,browser);

//            final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken("demo", "demo");
//            System.out.println("token: "+authToken);
            final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            return    getAuthenticationManager().authenticate(authToken); // This will take to successfulAuthentication or faliureAuthentication function
        }
        catch( JSONException | AuthenticationException e){
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication ( HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authToken) throws IOException, ServletException {
        log.info("successfulAuthentication()");
        SecurityContextHolder.getContext().setAuthentication(authToken);

        TokenUser tokenUser = (TokenUser)authToken.getPrincipal();
        SessionResponse response = new SessionResponse();
        SessionItem respItem = new SessionItem();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String tokenString = this.tokenUtil.createTokenForUser(tokenUser);

        respItem.setFirstName(tokenUser.getUser().getFirstName());
        respItem.setLastName(tokenUser.getUser().getLastName());
        respItem.setUserId(tokenUser.getUser().getUserId());
        respItem.setEmail(tokenUser.getUser().getEmail());
        respItem.setToken(tokenString);

        response.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
        response.setOperationMessage("Login Success");
        response.setItem(respItem);
        String jsonRespString = ow.writeValueAsString(response);

        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().write(jsonRespString);
        //res.getWriter().write(jsonResp.toString());
        res.getWriter().flush();
        res.getWriter().close();

        // DONT call supper as it contine the filter chain super.successfulAuthentication(req, res, chain, authResult);
    }
}
