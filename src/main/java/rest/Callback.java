package rest;

import lombok.extern.slf4j.Slf4j;
import org.openid4java.discovery.DiscoveryInformation;
import utils.OpenIdUtil;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/callback")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class Callback {
  @Inject
  HttpServletRequest httpServletRequest;

  @GET
  public Response user(@QueryParam("openid.ns") String ns,
                       @QueryParam("openid.mode") String mode,
                       @QueryParam("openid.op_endpoint") String op_endpoint,
                       @QueryParam("openid.claimed_id") String claimed_id,
                       @QueryParam("openid.identity") String identity,
                       @QueryParam("openid.return_to") String return_to,
                       @QueryParam("openid.response_nonce") String response_nonce,
                       @QueryParam("openid.assoc_handle") String assoc_handle,
                       @QueryParam("openid.signed") String signed,
                       @QueryParam("openid.sig") String sig) throws Exception {

    DiscoveryInformation discovered = (DiscoveryInformation) httpServletRequest.getSession()
                                                                               .getAttribute("openid-disc");
    StringBuffer receivingURL = httpServletRequest.getRequestURL();
    String       queryString  = httpServletRequest.getQueryString();
    if (queryString != null && queryString.length() > 0)
      receivingURL.append("?").append(httpServletRequest.getQueryString());

    log.debug("receivingURL {}", receivingURL);
    final String steamOpenid = OpenIdUtil.getSteamOpenid(discovered, receivingURL.toString(), httpServletRequest.getParameterMap());

    return Response.ok(String.format("{\"SteamOpenid\":\"%s\"}", steamOpenid))
                   .build();
  }
}
