package utils;

import org.openid4java.association.AssociationSessionType;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.ParameterList;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class OpenIdUtil {
  private static final String STEAM_OPENID = "https://steamcommunity.com/openid";
  private static final ConsumerManager      manager;
  private static       DiscoveryInformation discovered;

  static {
    manager = new ConsumerManager();
    manager.setMaxAssocAttempts(0);
    manager.setAssociations(new InMemoryConsumerAssociationStore());
    manager.setNonceVerifier(new InMemoryNonceVerifier(5000));
    manager.setMinAssocSessEnc(AssociationSessionType.DH_SHA256);
  }

  public static String login(String callbackUrl) throws Exception {
    final List discover = manager.discover(STEAM_OPENID);
    discovered = manager.associate(discover);

    AuthRequest authReq = manager.authenticate(discovered, callbackUrl);
    return authReq.getDestinationUrl(true);
  }

  public static String getSteamOpenid(DiscoveryInformation discovered, String receivingUrl, Map responseMap) throws Exception {
    ParameterList      responseList = new ParameterList(responseMap);
    VerificationResult verification = manager.verify(receivingUrl, responseList, discovered);
    verification.getAuthResponse().validate();
    Identifier verifiedId = verification.getVerifiedId();

    if (verifiedId == null) {
      throw new RuntimeException("Wrong payload");
    }

    final String identifier = verifiedId.getIdentifier();
    final String steamId    = new File(new URI(identifier).getPath()).getName();

    return steamId;
  }

  public static DiscoveryInformation getDiscovered() {
    return discovered;
  }
}
