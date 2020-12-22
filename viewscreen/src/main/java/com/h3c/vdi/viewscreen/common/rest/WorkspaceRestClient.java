package com.h3c.vdi.viewscreen.common.rest;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Created by x19765 on 2020/10/27.
 */
@Component
public class WorkspaceRestClient {

    @Value("${workspace.protocol}")
    private String protocol;

    @Value("${workspace.port}")
    private int port;

    @Value("${workspace.maxConnection}")
    private int maxTotalConnection;

    @Value("${workspace.perRouteConnection}")
    private int perRouteConnection;

    @Value("${workspace.username}")
    private String username;

    @Value("${workspace.password}")
    private String password;

    public RestClient workspaceRestClient(String hostIp) {
        String protocol = this.protocol;
        int port = this.port;
        int maxTotalConnection = this.maxTotalConnection;
        int perRouteConnection = this.perRouteConnection;
        String username = this.username;
        String password = this.password;
        RestClient restClient = new RestClient(protocol, hostIp, port, username, password, maxTotalConnection, perRouteConnection);
        return restClient;
    }
}
