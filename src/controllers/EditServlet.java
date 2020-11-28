package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

@WebServlet("/edit")
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EditServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();

		//	Class型変数　に　DBのRequest Parameter　を取得　都度閉じる
		Task m = em.find(Task.class , Integer.parseInt(request.getParameter("id")) );
		em.close();

		 // やることの情報とSessionIDをRequestScopeに登録
        request.setAttribute("task", m);
        request.setAttribute("_token", request.getSession().getId());

        //やることDataが存在している場合のみ
        // やることIDをSessionScopeに登録
        if(m != null){
        	request.getSession().setAttribute("task_id", m.getId());
        }

        // 定型　Forward
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp");
        rd.forward(request, response);

	}
	//	doPost

}
//Class
