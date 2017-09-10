package com.funtis.commons.http;

/**
 * Created by Sławomir Kluz on 21.04.2017.
 */
public class Header {

    private String name;
    private String value;

    public Header(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }


    @Override
    public String toString() {
        return "Header{" + name + ": " + value + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Header header = (Header) o;

        if (name != null ? !name.equals(header.name) : header.name != null) return false;
        return value != null ? value.equals(header.value) : header.value == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
