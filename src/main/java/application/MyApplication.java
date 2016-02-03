package application;

import lombok.extern.java.Log;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/*")
@Log
public class MyApplication extends ResourceConfig {
  public MyApplication() {
    super();
    // Optionally remove existing handlers attached to j.u.l root logger
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
    // the initialization phase of your application
    SLF4JBridgeHandler.install();

    packages("provider", "rest");

    register(new LoggingFilter(log, true));
  }

}
