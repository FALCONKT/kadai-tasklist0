<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/app.jsp">

    <c:param name="content">

	<%--FlushMessage--%>
    <c:if test="${flush!=null }">
    <div id="flush_success">
    	<c:out value="${flush}" />
    </div>
    </c:if>

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
                     最新更新<c:out value="${task.updated_at}" />&gt;
                    初回作成<c:out value="${task.created_at}" />
                  </li>
            </c:forEach>
        </ul>

        <div id="pagination">
            （全 ${tasks_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((tasks_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/index?page=${i}"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <p><a href="${pageContext.request.contextPath}/new">新規メッセージの投稿</a></p>

    </c:param>

</c:import>