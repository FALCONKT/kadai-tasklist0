package controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import models.validators.TaskValidator;
import utils.DBUtil;


@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			String _token = request.getParameter("_token");

	        if(_token != null && _token.equals(request.getSession().getId())) {
	            EntityManager em = DBUtil.createEntityManager();

	            // SessionScopeから　やることのID　を取得して
	            // そのIDの　やることの1件のみ　をDBから取得
	            Task m = em.find(Task.class, (Integer)(request.getSession().getAttribute("task_id")));
	            //  SessionScopeで取得したDataがObject型になっているためIntegerでCastしている

	            // Formの内容を各Field変数に上書き
	            String content = request.getParameter("content");
	            m.setContent(content);

	            // 更新日時のみ上書き
	            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
	            m.setUpdated_at(currentTime);

	            //Varidation　-------------------
	            //Varidation を実行してErrorがあったら編集画面のFormに戻る
	            List<String> errors = TaskValidator.validate(m);
	            if(errors.size() > 0) {
	                em.close();

	                // Fromに初期値を設定、さらにErrorMessageを送る
	                request.setAttribute("_token", request.getSession().getId());
	                request.setAttribute("task", m);
	                request.setAttribute("errors", errors);

	                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp");
	                rd.forward(request, response);
	            } else {
	                // DBを更新
	                em.getTransaction().begin();
	                em.getTransaction().commit();
	                request.getSession().setAttribute("flush", "更新が完了しました。");
	                em.close();

	                // SessionScop上の不要になったをData削除
	                request.getSession().removeAttribute("message_id");

	                // index.jspへRedirect
	                response.sendRedirect(request.getContextPath() + "/index");
	            }
	            // Varidation　END-------------------


//				Varidate無しの場合
//	            //DB更新
//	            em.getTransaction().begin();
//	            em.getTransaction().commit();
//	             // FlushMessage 後で追加分
//	            request.getSession().setAttribute("flush", "更新が完了しました。");       // ここを追記
//	            em.close();
//	            //DB更新　修了
//
//	            // SessionScope上の不要になったDataを削除
//	            request.getSession().removeAttribute("message_id");
//
//	            // index へ　Redirect
//	            response.sendRedirect(request.getContextPath() + "/index");
	        }
	        //	if文

	}
	//	doPost

}
//Class