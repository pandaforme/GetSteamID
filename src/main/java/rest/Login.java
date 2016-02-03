package rest;

import utils.OpenIdUtil;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Login {
  @Inject
  HttpServletRequest httpServletRequest;

  @GET
  public Response login() throws Exception {
    final String login = OpenIdUtil.login("http://localhost:8080/steam/callback");

    httpServletRequest.getSession().setAttribute("openid-disc", OpenIdUtil.getDiscovered());
    return Response.temporaryRedirect(new URI(login))
                   .build();
  }
}
