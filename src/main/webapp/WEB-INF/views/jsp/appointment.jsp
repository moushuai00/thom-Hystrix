<%--
  Created by IntelliJ IDEA.
  User: ThinkPad
  Date: 2016/11/5
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>新增预约</title>
    <spring:url value="/resources/core/css/hello.css" var="coreCss"/>
    <spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss"/>
    <link href="${bootstrapCss}" rel="stylesheet"/>
    <link href="${coreCss}" rel="stylesheet"/>
</head>
<body>


<spring:url value="/resources/core/js/hello.js" var="coreJs"/>
<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs"/>
<spring:url value="/resources/core/js/jquery.js" var="jqueryJs"/>

<script src="${jqueryJs}"></script>
<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
</body>
</html>
