<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />


<div class="openingQuote content">
<c:out value="Wilderness is more than a natural place, more than a place for recreation. It is a place for inspiration."/>
</div>


<div class="row ">
	<c:forEach var="park" items="${parks}">
	<div>
		<c:url var="imageUrl" value="${park.parkImg}" />
		<c:url var="parkDetail" value="/parkDetail">
			<c:param name="parkCode" value="${park.parkCode}" />
		</c:url>
		<c:url var="formDetails" value="/parkDetail" />
		<form method="GET" action="${formDetails}">
			<a href="${parkDetail}">
				<div class="parks"
					style="background-image:url('${imageUrl}');background-size:100% 100%;">
					<div class="parkInfo">
						<div class="parkName">
							<c:out value="${park.parkName}" />
						</div>
						<div class="location">
							<c:out value="${park.state}" />
						</div>
						<div class="description">
							<c:out value="${park.description}" />
						</div>
					</div>
				</div>
				</div>
			</a>
	</c:forEach>
</div>
<c:import url="/WEB-INF/jsp/common/footer.jsp" />