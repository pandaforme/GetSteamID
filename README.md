# Introduction
The project is to demo how to get a Steam ID.

# How to execute the project?
* Step 1: Run Jetty
```bash
 ./gradle jettyRun
```

* Step 2: Paste the url at browser
```
http://localhost:8080/steam/login
```

* Step 3: Sign in with Steam 
 
* Step 4: If sign in successfully, Steam will return some information to callback url
```
http://localhost:8080/steam/callback
```

# Step 5: You can get a specific json
```javascript
"{"SteamOpenid":"1234567890"}"
```

## Technical Stack
* Java 1.8
* Jersey 2.22.1
* Swagger API 1.5.4
* Lombok 1.16.6
* Gradle
* Jetty

# Reference:
[ConsumerServlet.java](https://github.com/jbufu/openid4java/blob/master/samples%2Fconsumer-servlet%2Fsrc%2Fmain%2Fjava%2Forg%2Fopenid4java%2Fsamples%2Fconsumerservlet%2FConsumerServlet.java)
