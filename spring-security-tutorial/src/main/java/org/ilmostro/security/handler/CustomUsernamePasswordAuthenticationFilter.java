package org.ilmostro.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ilmostro.security.entity.UserLoginParameter;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author li.bowei
 **/
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //attempt Authentication when Content-Type is json
        if(request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)){

            //use jackson to deserialize json
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken result = null;
            try (InputStream is = request.getInputStream()){
                UserLoginParameter parameter = mapper.readValue(is, UserLoginParameter.class);
                result = new UsernamePasswordAuthenticationToken(
                        parameter.getUsername(), parameter.getPassword());
                setDetails(request, result);
                return this.getAuthenticationManager().authenticate(result);
            }catch (IOException e) {
                logger.error("read bean error! cause:{}", e.getCause());
                return result;
            }
        }

        //transmit it to UsernamePasswordAuthenticationFilter
        else {
            return super.attemptAuthentication(request, response);
        }
    }
}
