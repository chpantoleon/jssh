package gr.cpad.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Server {

    private String alias;
    private String username;
    private String ipAddress;
    private String port;
    private boolean usesNonDefaultPrivateKey;
    private String keyPath;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isUsesNonDefaultPrivateKey() {
        return usesNonDefaultPrivateKey;
    }

    public void setUsesNonDefaultPrivateKey(boolean usesNonDefaultPrivateKey) {
        this.usesNonDefaultPrivateKey = usesNonDefaultPrivateKey;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }
}
