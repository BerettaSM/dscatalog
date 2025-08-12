package com.devsuperior.dscatalog.utils;

import java.util.Optional;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public class PathUtils {

    public static String getCurrentPath() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(req -> ((ServletRequestAttributes) req))
                .map(ServletRequestAttributes::getRequest)
                .map(HttpServletRequest::getRequestURI)
                .orElse("Unknown path");
    }

}
