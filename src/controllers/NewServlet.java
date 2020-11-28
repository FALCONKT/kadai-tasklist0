package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;


@WebServlet("/new")
public class NewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public NewServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();
        em.getTransaction().begin();

        // Taskのインスタンスを生成
		Task m =new Task();

		String content ="やることの内容";
		m.setContent(content);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());     // 現在の日時を取得
        m.setCreated_at(currentTime);
        m.setUpdated_at(currentTime);

        // Databaseに保存   動作確認用
		// em.persist(m);
		// em.getTransaction().commit();

        // 自動採番されたIDの値をBrowserに表示　動作確認用
        // response.getWriter().append(Integer.valueOf(m.getId()).toString());

        // CSRF対策　Security
        request.setAttribute("_token", request.getSession().getId());

        // 事前設定として　Instance生成　Task 呼び出し　
        request.setAttribute("task", new Task());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/new.jsp");
        rd.forward(request, response);

        em.close();
	}

}
