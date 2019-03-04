package rbn.edu.config.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

import rbn.edu.model.Product;
import rbn.edu.service.IProductService;

@Component
public class GetAllProductsWebSocketHandler extends TextWebSocketHandler {

    private WebSocketSession session;

    @Autowired
    private IProductService iProductService;

    public void updateListeners() {
	if (session != null && session.isOpen()) {
	    try {
		String json = parseProductList();
		session.sendMessage(new TextMessage(json));
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    private String parseProductList() {
	StringBuilder sb = new StringBuilder();
	sb.append("[");
	for (Product product : iProductService.getAll()) {
	    String gson = new Gson().toJson(product);
	    sb.append(gson);
	    sb.append(",");
	}
	sb.deleteCharAt(sb.toString().length() - 1);
	sb.append("]");
	return sb.toString();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
	System.out.println("Connection established");
	this.session = session;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	if ("CLOSE".equalsIgnoreCase(message.getPayload())) {
	    session.close();
	} else {
	    System.out.println("Received:" + message.getPayload());
	}
    }
}
