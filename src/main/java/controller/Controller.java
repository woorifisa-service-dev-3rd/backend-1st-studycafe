package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 개별 컨트롤러들이 각자 수행할 메서드가 정의된 인터페이스
public interface Controller {

   // 요청 경로별 세부 로직 처리를 수행할 메서드, 개별 컨트롤러들에서 해당 메서드를 구현
   void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException;
}