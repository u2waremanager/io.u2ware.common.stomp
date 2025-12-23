package io.u2ware.common.stomp.server.client;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.util.UriComponentsBuilder;

import io.u2ware.common.stomp.server.oauth2.Oauth2Docs;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class Client01Tests {
    
    protected Log logger = LogFactory.getLog(getClass());

    private @LocalServerPort int port;
	private @Autowired ApplicationContext ac;

    protected @Autowired MockMvc mvc;
    protected @Autowired Oauth2Docs od;


    @Test
    public void contextLoads() throws Exception{

        // Jwt u1 = od.jose("oauth2User");
        RestTemplateXhrTransport t = new RestTemplateXhrTransport();


		// /////////////////////////////////////
		// // Check Websocket Server Running 
		// /////////////////////////////////////
		// String websocketInfo = String.format("http://localhost:%d/stomp/info", port);
		// URI websocketInfoUri = UriComponentsBuilder.fromUriString(websocketInfo).build().toUri();
        // String r = null;

		// try{
		// 	r = t.executeInfoRequest(websocketInfoUri, null);
		// 	logger.info(r);
		// 	Assertions.assertNotNull(r); //-> 404 page
		// }catch(Exception e){
        //     // e.printStackTrace();
		// 	logger.info(websocketInfoUri+"  "+e.getMessage());
		// 	Assertions.assertNotNull(e);
		// 	// Assertions.assertEquals(HttpClientErrorException.NotFound.class, e.getClass());
		// }

		/////////////////////////////////////
		// Check Websocket Server Running 
		/////////////////////////////////////
		Jwt u1 = od.jose("oauth2User");
		String websocketInfo2 = String.format("http://localhost:%d/stomp/info?access_token=%s", port, u1.getTokenValue());
		URI websocketInfoUri = UriComponentsBuilder.fromUriString(websocketInfo2).build().toUri();

		try{
			String r = t.executeInfoRequest(websocketInfoUri, null);
			logger.info(r);
			Assertions.assertNotNull(r); //-> 404 page
		}catch(Exception e){
            // e.printStackTrace();
			logger.info(websocketInfoUri+"  "+e.getMessage());
			Assertions.assertNotNull(e);
			// Assertions.assertEquals(HttpClientErrorException.NotFound.class, e.getClass());
		}
    }    
}
