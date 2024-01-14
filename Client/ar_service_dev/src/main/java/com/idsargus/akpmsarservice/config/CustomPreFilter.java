//package com.idsargus.akpmsarservice.config;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomPreFilter extends ZuulFilter {
//
//    private static final Logger log = LoggerFactory.getLogger(CustomPreFilter.class);
//
//    @Override
//    public String filterType() {
//        return "pre";
//    }
//
//    @Override
//    public int filterOrder() {
//        return 6;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        log.info(String.format("Before filter ['%s': '%s', '%s': '%s']",
//                "X-Forwarded-Proto",
//                ctx.getZuulRequestHeaders().get("X-Forwarded-Proto"),
//                "X-Forwarded-Port",
//                ctx.getZuulRequestHeaders().get("X-Forwarded-Port")));
//
//        final String originalXForwardedProto = ctx.getRequest().getHeader("X-Forwarded-Proto");
//        final String originalXForwardedPort = ctx.getRequest().getHeader("X-Forwarded-Port");
//
//        if (originalXForwardedProto != null) {
//            ctx.addZuulRequestHeader("X-Forwarded-Proto", originalXForwardedProto);
//        }
//
//        if (originalXForwardedPort != null) {
//            ctx.addZuulRequestHeader("X-Forwarded-Port", originalXForwardedPort);
//        }
//
//        log.info(String.format("After filter ['%s': '%s', '%s': '%s']",
//                "X-Forwarded-Proto",
//                ctx.getZuulRequestHeaders().get("X-Forwarded-Proto"),
//                "X-Forwarded-Port",
//                ctx.getZuulRequestHeaders().get("X-Forwarded-Port")));
//
//        return null;
//    }
//}
