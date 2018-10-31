<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
int res = 0;
int num1 = Integer.parseInt(request.getParameter("num1"));
int num2 = Integer.parseInt(request.getParameter("num2"));
String op = request.getParameter("op");
int result = 0;
switch (op) {
  case "+":
    res = num1 + num2;
    break;
  case "-":
    res = num1 - num2;
    break;
  case "*":
    res = num1 * num2;
    break;
  case "/":
    res = num1 / num2;
    break;
}
out.println(res);
%>