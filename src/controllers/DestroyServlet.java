package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

@WebServlet("/destroy")
public class DestroyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DestroyServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			String _token = request.getParameter("_token");
	        if(_token != null && _token.equals(request.getSession().getId())) {

	        	EntityManager em = DBUtil.createEntityManager();

	            // Sessionから　やることのID　を取得して
	            // そのIDの　やること1件のみ　をDBから取得
	            Task m = em.find(Task.class, (Integer)(request.getSession().getAttribute("task_id")));

	            //  DBへAccess　対象を消す　確定し　閉じる
	            em.getTransaction().begin();
	            em.remove(m);       // ここでData削除
	            em.getTransaction().commit();
	            em.close();

	            // SessionScope上の不要になったDataを削除
	            request.getSession().removeAttribute("message_id");

	            // indexPage　へ　Redirect
	            response.sendRedirect(request.getContextPath() + "/index");
	        }
	        // if文

	}
	//	doPost

}
//Class