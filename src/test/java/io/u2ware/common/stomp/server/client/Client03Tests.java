package io.u2ware.common.stomp.server.client;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.u2ware.common.stomp.client.WebsocketStompClient;
import io.u2ware.common.stomp.client.WebsocketStompClientHandler;
import io.u2ware.common.stomp.client.handlers.LoggingHandler;
import io.u2ware.common.stomp.server.oauth2.Oauth2Docs;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class Client03Tests {
    
    protected Log logger = LogFactory.getLog(getClass());

    private @LocalServerPort int port;
	private @Autowired ApplicationContext ac;

    protected @Autowired MockMvc mvc;
    protected @Autowired ObjectMapper mapper;
    protected @Autowired Oauth2Docs od;


    @Test
    public void contextLoads() throws Exception{

		WebsocketStompClient.withSockJS()
			.connect(od.stomp(port, "client1"), new LoggingHandler("client1"))
			.whenComplete((c1, u1)->{
				System.err.println("client1 connect ");

				c1.sleep(100).subscribe("/topic/channel", 

					(h,p)->{
						System.err.println("RECEIVE111: "+p);
					}


					// new WebsocketStompClientHandler() {
					// 	@Override
					// 	public void handleFrame(StompHeaders headers, JsonNode payload) {
					// 		System.err.println("RECEIVE222: "+payload);
					// 	}					
					// }
				
					// new StompFrameHandler(){
					// 	@Override
					// 	public Type getPayloadType(StompHeaders headers) {
					// 		return JsonNode.class;
					// 	}
					// 	@Override
					// 	public void handleFrame(StompHeaders headers, Object payload) {
					// 		System.err.println("RECEIVE3333: "+payload);
					// 	}
					// }
				)
				.whenComplete((c2,u2)->{
					System.err.println("client1 subscribe ");					
				});
			});
		


		Thread.sleep(1000);			
		
		WebsocketStompClient.withSockJS()
			.connect(od.stomp(port, "client2"), new LoggingHandler("client2"))
			.whenComplete((c1, u1)->{
				c1.sleep(100).subscribe("/topic/aaaa", 
				
					//##2				
					// new WebsocketStompClientHandler() {
					// 	@Override
					// 	public void handleFrame(StompHeaders headers, JsonNode payload) {
					// 		System.err.println("RECEIVE: "+payload);
					// 	}					
					// }
					
					//##1
					(h,p)->{
						System.err.println("RECEIVE: "+p);
					}

				);



				c1.sleep(100).unsubscribe("/topic/aaaa");

			});
		
		Thread.sleep(1000);
    }    
}
