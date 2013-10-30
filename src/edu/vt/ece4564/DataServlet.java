package edu.vt.ece4564;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;

import edu.vt.ece4564.Place;

public class DataServlet extends HttpServlet {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		
		WebAppContext context = new WebAppContext();
		context.setWar("war");
		context.setContextPath("/data");
		server.setHandler(context);
		
		try {

			server.start();
			server.join();
			
		}
		catch(Exception e) {
			
			e.printStackTrace();
			
		}

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Writer out = resp.getWriter();

		String formatreq = req.getParameter("format");
		String locationreq = req.getParameter("location");
		String radiusreq = req.getParameter("radius");
		String typesreq = req.getParameter("type");

		if (locationreq != "") {

			try {

				// Create the Jetty HttpClient and start it
				HttpClient client = new HttpClient(new SslContextFactory(true));
				try {
					client.start();
				} catch (Exception e) {
					e.printStackTrace();
					out.write("Unable to start client");
					return;
				}

				String queryRequestURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/"
						+ formatreq
						+ "?location="
						+ locationreq.replaceAll("\n", "")
						+ "&radius="
						+ radiusreq.replaceAll("\n", "")
						+ "&types="
						+ typesreq
						+ "&sensor=false&key=AIzaSyAdOcXxrIz0YOO8LS6Sy0Djb7GDFz6w2w4";

				// This call will wait until the request is complete
				ContentResponse response;
				try {
					response = client.GET(queryRequestURL);
				} catch (InterruptedException e) {
					e.printStackTrace();
					out.write("Unable to start client");
					return;
				} catch (ExecutionException e) {
					e.printStackTrace();
					out.write("Unable to start client");
					return;
				} catch (TimeoutException e) {
					e.printStackTrace();
					out.write("Unable to start client");
					return;
				}

				switch (response.getStatus()) {
				case 200: // A-ok
					// Here is where the response in the form of a string goes
					// back to the Android app request.
					out.write(new String(response.getContent(), "UTF-8"));
					break;
				case 404: // If you want to check for specific error types
					out.write("Oh no, 404");
					break;
				default: // Then don't forget to handle all of the other
							// response codes!
					out.write("Unknown response");
					break;
				}

			} catch (IOException e) {
				// Error

			}

		}

	}

}
