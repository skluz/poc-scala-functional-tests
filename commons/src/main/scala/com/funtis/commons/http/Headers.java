package com.funtis.commons.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sławomir Kluz on 21.04.17.
 */
public class Headers {

    private List<Header> headers = new ArrayList<>();

    public Headers() {

    }

    public Headers(Header ... headers) {
        this.headers.addAll(Arrays.asList(headers));
    }

    public Headers(Headers headers) {
        this.headers.addAll(headers.headers);
    }

    public Headers addHeader(String name, String value) {
        this.headers.add(new Header(name, value));
        return this;
    }

    public Headers addHeader(Header header) {
        this.headers.add(header);
        return this;
    }

    public Headers addHeaders(Header ... headers) {
        this.headers.addAll(Arrays.asList(headers));
        return this;
    }

    public Headers contentType(String contentType) {
        return addHeader("Content-Type", contentType);
    }

    public Headers accept(String accept) {
        return addHeader("Accept", accept);
    }

    public List<Header> headers() {
        return this.headers;
    }

    @Override
    public String toString() {
        return "Headers{" +
                "getHeaders=" + headers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Headers headers1 = (Headers) o;

        return headers != null ? headers.equals(headers1.headers) : headers1.headers == null;
    }

    @Override
    public int hashCode() {
        return headers != null ? headers.hashCode() : 0;
    }
}
