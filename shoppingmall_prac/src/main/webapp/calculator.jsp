<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<%
int num1 = Integer.parseInt(request.getParameter("num1"));
int num2 = Integer.parseInt(request.getParameter("num2"));
int operator = Integer.parseInt(request.getParameter("operator"));
int output = 0;
switch(operator){
case 0: output = num1+num2; break;
case 1: output = num1-num2; break;
case 2: output = num1*num2; break;
case 3: output = num1/num2; break;
}

out.print(output);
%>
