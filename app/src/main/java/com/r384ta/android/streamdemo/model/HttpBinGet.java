package com.r384ta.android.streamdemo.model;

import com.google.gson.annotations.SerializedName;

public class HttpBinGet {
    public String origin;
    public String url;
    public Headers headers;
    public Args args;

    @Override
    public String toString() {
        return "HttpBinGet{" +
            "origin='" + origin + '\'' +
            ", url='" + url + '\'' +
            ", headers=" + headers +
            ", args=" + args +
            '}';
    }

    public static class Headers {
        @SerializedName("Accept")
        public String accept;
        @SerializedName("Accept-Encoding")
        public String acceptEncoding;
        @SerializedName("Accept-Language")
        public String Language;
        @SerializedName("Cookie")
        public String cookie;
        @SerializedName("Host")
        public String host;
        @SerializedName("Referer")
        public String referer;
        @SerializedName("Upgrade-Insecure-Requests")
        public String upgradeInsecureRequests;
        @SerializedName("User-Agent")
        public String userAgent;

        @Override
        public String toString() {
            return "Headers{" +
                "accept='" + accept + '\'' +
                ", acceptEncoding='" + acceptEncoding + '\'' +
                ", Language='" + Language + '\'' +
                ", cookie='" + cookie + '\'' +
                ", host='" + host + '\'' +
                ", referer='" + referer + '\'' +
                ", upgradeInsecureRequests='" + upgradeInsecureRequests + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
        }
    }

    public static class Args {
        @Override
        public String toString() {
            return "Args{}";
        }
    }
}
