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


@WebServlet("/create")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CreateServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String _token = request.getParameter("_token");

        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            //Class　Instance　取得設定
            Task m = new Task();

            String content = request.getParameter("content");
            m.setContent(content);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            m.setCreated_at(currentTime);
            m.setUpdated_at(currentTime);


           //Varidation ----------------------------------
            List<String> errors = TaskValidator.validate(m);

            if(errors.size() > 0) {
                em.close();

                //Formに初期値を設定、さらにErrorMessageを送る
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("task", m);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/new.jsp");
                rd.forward(request, response);

            } else {
                // DBに保存
                em.getTransaction().begin();
                em.persist(m);
                em.getTransaction().commit();
                request.getSession().setAttribute("flush", "登録が完了しました。");
                em.close();

                // index.jspにRedirect
                response.sendRedirect(request.getContextPath() + "/index");
            }
          //Varidation END----------------------------------


//            Validat無しの場合
//            // TaskClassのObjectを　DBへ保存している
//            em.getTransaction().begin();
//            em.persist(m);
//            em.getTransaction().commit();
//            //FlashMessage　後で追加分
//            request.getSession().setAttribute("flush", "登録が完了しました。");       // ここを追記
//            em.close();
//            //DB更新　終了
//
//            response.sendRedirect(request.getContextPath() + "/index");


	}
    //if文

	}
	//	doPost

}
//Class