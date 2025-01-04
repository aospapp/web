<%@page import="app.aosp.web.PageConfig" %>
<%@page import="app.aosp.web.IndexEntry" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page session="false" %>
<%
    PageConfig cfg = PageConfig.get(request);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="./css/style.css" media="screen">
    <title>AospApp</title>
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.svg"/>
</head>
<body>
<div id="web_body">
    <div id="header">
        <h1><strong>AospApp - AOSPXRef</strong></h1>
    </div>
    <div id="split_top" class="split"></div>
    <div id="content">
        <div id="sidebar">
            <h2 class="title">Android Source</h2>
            <ul class="ul-list">
                <%
                    for (IndexEntry entry : cfg.getIndexList()) { %>
                <li><a href="/<%=entry.getPath()%>/"><%=entry.getName()%>
                </a></li>
                <%
                    }
                %>
            </ul>
        </div>
        <div id="main">
            <h2 class="title">News</h2>
            <hr>
            <ul class="ul-list">
                <li><b>2023-11-21</b> - Switch: <a href="http://aosp.ylarod.cn">HTTP</a> | <a href="https://aosp.app">HTTPS</a></li>
                <li><b>2025-01-05</b> - Xref for XNU: <a href="https://xnu.aosp.app">XNU Kernel Xref</a></li>
                <%
                    for (IndexEntry entry : cfg.getIndexList()) { %>
                <li><b><%=entry.getDate()%>
                </b> - New Index: <a href="/<%=entry.getPath()%>/"><%=entry.getName()%>
                </a></li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
    <div id="split_bottom" class="split"></div>
    <div id="footer">
        © 2023-2025 <a href="mailto:me@ylarod.cn">Ylarod</a>
        | <a href="http://aospxref.com">AOSPXRef</a>
        | <a href="http://androidxref.com">AndroidXRef</a>
        | <a href="http://xrefandroid.com">XRefAndroid</a>
    </div>
</div>
</body>
</html>
