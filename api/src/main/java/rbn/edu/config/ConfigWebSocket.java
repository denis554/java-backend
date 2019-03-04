package rbn.edu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import rbn.edu.config.websockets.GetAllProductsWebSocketHandler;

@Configuration
@EnableWebSocket
public class ConfigWebSocket implements WebSocketConfigurer {

    @Autowired
    private GetAllProductsWebSocketHandler getAllProductsWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
	registry.addHandler(getAllProductsWebSocketHandler, "/getAllProducts").setAllowedOrigins("*");
    }

}