package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;


@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public IndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	//  	Browserㇸ場所表示用
    	//    response.getWriter().append("Served at: ").append(request.getContextPath());

    	EntityManager em = DBUtil.createEntityManager();

    	//  全ての　DB　Table　上にあるDataを取得する
    	//  List<Task> tasks = em.createNamedQuery("getAllTasks",Task.class)
    	//  .getResultList();

        //  BrowserㇸDB Tableの中身表示用 確認用
        //  response.getWriter().append(Integer.valueOf(tasks.size()).toString());

        //PageNation  	------------------------------------------
    	 // 開くPage数を取得（初期値は1Page目） 例外処理入れ込み
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) {}

        // 最大件数と開始位置を指定して　やること　を取得
        List<Task> tasks = em.createNamedQuery("getAllTasks", Task.class)
                                   .setFirstResult(15 * (page - 1))
                                   .setMaxResults(15)
                                   .getResultList();

        // 全件数を取得
        long tasks_count = (long)em.createNamedQuery("getTasksCount", Long.class)
                                      .getSingleResult();
        //    	------------------------------------------

        em.close();

        request.setAttribute("tasks",tasks);

        //PageNation    	------------------------------------------
        request.setAttribute("tasks_count", tasks_count);     // 全件数　渡す
        request.setAttribute("page", page);		//　Page変数も渡す
        //    	------------------------------------------

        // FlushMessageがSessionScopに設定されていたら
        // RequestScopeに保存する（SessionScopからは削除）
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        //定型　Forward
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/index.jsp");
        rd.forward(request, response);

    }

}
