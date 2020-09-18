/*
 * Javalin - https://javalin.io
 * Copyright 2017 David Åse
 * Licensed under Apache 2.0: https://github.com/tipsy/javalin/blob/master/LICENSE
 */

package io.javalin.core.security;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

public class SecurityUtil {

    public static Set<Role> roles(Role... roles) {
        return unmodifiableSet(new HashSet<>(asList(roles)));
    }

    public static void noopAccessManager(@NotNull Handler handler, @NotNull Context ctx, @NotNull Set<Role> permittedRoles) throws Exception {
        if (!permittedRoles.isEmpty()) {
            throw new IllegalStateException("No access manager configured. Add an access manager using 'Javalin.create(c -> c.accessManager(...))'.");
        }
        handler.handle(ctx);
    }

    public static void sslRedirect(@NotNull Context ctx) {
        if (isLocalhost(ctx)) {
            return;
        }
        String xForwardedProto = ctx.header("x-forwarded-proto");
        if ("http".equals(xForwardedProto) || (xForwardedProto == null && ctx.scheme().equals("http"))) {
            ctx.redirect(ctx.fullUrl().replace("http", "https"), 301);
        }
    }

    private static boolean isLocalhost(Context ctx) {
        //TODO: This is duplicate code from ContextUtil Context.isLocalhost()
        try {
            final String host = new URL(ctx.url()).getHost();
            return "localhost".equals(host) || "127.0.0.1".equals(host);
        } catch (Exception e){
            return false;
        }
    }
}
