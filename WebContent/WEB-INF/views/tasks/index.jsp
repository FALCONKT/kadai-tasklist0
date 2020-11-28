<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/app.jsp">

    <c:param name="content">
        <h2>メッセージ一覧</h2>

        <ul>
            <c:forEach var="task" items="${tasks}">
                <li>
	                <%--ID--%>
                    <a href="${pageContext.request.contextPath}/show?id=${task.id}">
                        <c:out value="${task.id}" />
                    </a>：
                    <%--やることの内容--%>
                    <c:out value="${task.content}"></c:out> &gt;
                     最新更新日<c:out value="${tssk.updated_at}" />&gt;
                    初回作成日<c:out value="${tssk.created_at}" />
                  </li>
            </c:forEach>
        </ul>

        <p><a href="${pageContext.request.contextPath}/new">新規メッセージの投稿</a></p>

    </c:param>

</c:import>