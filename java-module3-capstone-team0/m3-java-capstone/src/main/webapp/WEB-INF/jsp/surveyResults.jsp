<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />
<section class="survey">

	<c:if test="${not empty message}">
			<span><strong><c:out value="${message}" /></strong></span> <br>
	</c:if>
	
	<c:forEach var="results" items="${survey}">
		<div class="surveyResults content">

			<div class="surveyImg">

				<c:url var="imageUrl" value="${results.parkImg}" />
				<img src="${imageUrl}">
			</div>
			<div class="surveyInfo">

				<ul class="detailList">

					<li class="surveyParkName"><c:out value="${results.parkName}" /></li>
					<li class="surveyParkLocation"><c:out value="${results.state}" /></li>
					<li style="font-size: 1.2em;"><c:out value="${results.voteCount} votes." /></li>
					<li style="font-size: 1.2em;"><c:out
							value="${results.annualVisCount} visitors per year." /></li>
					<li style="font-size: 1.2em;"><c:out value="${results.milesOfTrail} miles of hikeable trail." /></li>
				</ul>
			</div>
		</div>
	</c:forEach>

</section>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />