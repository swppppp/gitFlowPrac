<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
request.setCharacterEncoding("utf-8");
String message = request.getParameter("message");
//System.out.println("수신받은 메세지: "+message);
//Thread.sleep(3000);
out.println(message);
%>
