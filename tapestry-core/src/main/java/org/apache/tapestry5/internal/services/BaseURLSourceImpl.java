// Copyright 2008, 2010, 2011, 2012 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.internal.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.BaseURLSource;
import org.apache.tapestry5.services.Request;

public class BaseURLSourceImpl implements BaseURLSource
{
    private final Request request;

    private String hostname;
    private int hostPort;
    private int secureHostPort;

    public BaseURLSourceImpl(Request request, @Inject @Symbol(SymbolConstants.HOSTNAME) String hostname,
                             @Symbol(SymbolConstants.HOSTPORT) int hostPort, @Symbol(SymbolConstants.HOSTPORT_SECURE) int secureHostPort)
    {
        this.request = request;
        this.hostname = hostname;
        this.hostPort = hostPort;
        this.secureHostPort = secureHostPort;
    }

    public String getBaseURL(boolean secure)
    {
        return String.format("%s://%s%s",
                secure ? "https" : "http",
                hostname(),
                portExtension(secure));
    }

    private String portExtension(boolean secure)
    {
        int port = secure ? secureHostPort : hostPort;

        // The default for the ports is 0, which means to use Request.serverPort. That's mostly
        // for development.
        if (port <= 0)
        {
            port = request.getServerPort();
        }

        int expectedPort = secure ? 443 : 80;

        if (port == expectedPort)
        {
            return "";
        }

        return ":" + port;
    }

    private String hostname()
    {

        if (InternalUtils.isBlank(hostname))
        {
            return request.getServerName();
        }

        // This is common in some PaaS deployments, such as Heroku, where the port is passed in as
        // and environment variable.

        if (this.hostname.startsWith("$"))
        {
            return System.getenv(hostname.substring(1));
        }

        return hostname;
    }
}
