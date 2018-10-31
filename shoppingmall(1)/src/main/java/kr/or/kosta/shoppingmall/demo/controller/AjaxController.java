package kr.or.kosta.shoppingmall.demo.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosta.shoppingmall.common.controller.Controller;
import kr.or.kosta.shoppingmall.common.controller.ModelAndView;

public class AjaxController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {

		System.out.println("-----------------------e들어오나여");
		int num1 = Integer.parseInt(request.getParameter("num1"));
		int num2 = Integer.parseInt(request.getParameter("num2"));
		String op = request.getParameter("op");
		int result = 0;
		switch (op) {
		  case "-":
		    result = num1 - num2;
		    break;
		  case "*":
		    result = num1 * num2;
		    break;
		  case "/":
		    result = num1 / num2;
		    break;
		  default: result = num1+num2;
		    break;
		}
		response.setContentType("text/plain; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			System.out.println("result값:"+result);
			out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}






