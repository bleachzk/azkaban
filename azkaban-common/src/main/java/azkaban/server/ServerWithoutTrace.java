package azkaban.server;

import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Created by bianzexin on 1/7/17.
 */
public class ServerWithoutTrace extends Server {

    public ServerWithoutTrace() {
        super();
    }

    public ServerWithoutTrace(int port)
    {
        super();
    }

    public void handle(HttpConnection connection) throws IOException, ServletException {
        if ("TRACE".equals(connection.getRequest().getMethod())) {
            Request base_request = connection.getRequest() instanceof Request ? (Request) connection.getRequest() : HttpConnection
                    .getCurrentConnection().getRequest();
            base_request.setHandled(true);
            connection.getResponse().setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        } else {
            super.handle(connection);
        }
    }
}
