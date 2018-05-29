<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/WEB-INF/jsp/common/header.jsp" />
<section class="parkDetail">
	<div class="detail content">
		<div class="detailImg">
			<c:url var="imageUrl" value="${park.parkImg}" />
			<img src="${imageUrl}">
		</div>
		<div class="detailInfo">
			<ul class="detailList">
				<li class="detailParkName"><c:out value="${park.parkName}" /></li>
				<li class="detailParkLocation"><c:out value="${park.state}" /></li>
				<li><c:out value="${park.acreage}" /></li>
				<li><c:out value="${park.milesOfTrail}" /></li>
				<li><c:out value="$ ${park.entryFee}" /></li>
				<li><c:out value="${park.animalSpecies}" /></li>
				<li><c:out value="${park.elevationFt}" /></li>
				<li><c:out value="${park.numCampsites}" /></li>
				<li><c:out value="${park.climate}" /></li>
				<li><c:out value="${park.yearFounded}" /></li>
				<li><c:out value="${park.inspirQuote}" /></li>
				<li><c:out value="${park.inspirQuoteSource}" /></li>
				<li><c:out value="${park.annualVisCount}" /></li>
				<li><c:out value="${park.description}" /></li>
			</ul>
		</div>
	</div>

	<div class="weatherBox">
		<div>
	<a name="jump"></a>
		<c:url var="formPost" value="/parkDetail" />
		<form action="${formPost}" method="POST">
			<div class="formEntry radioButtons">
				<label for="tempChoice">Temperature scale select:</label>
				<c:choose>
				<c:when test="${isFahrenheit == true}">
					<input type="radio" name="tempChoice" value= false checked="checked" >Fahrenheit 
					<input type="radio" name="tempChoice" value= true>Celsius 
					 <input type="hidden" name="parkCode" value="${park.parkCode}"> 
					</c:when>
					<c:when test="${isFahrenheit == false || isFahrenheit == null}">
					<input type="radio" name="tempChoice" value= false >Fahrenheit 
					 <input type="radio" name="tempChoice" value= true checked="checked" >Celsius 
					<input type="hidden" name="parkCode" value="${park.parkCode}"> 
					</c:when>
					</c:choose>
					<span class="submitButton"> <input id="formSubmitButton"
					type="submit" value="Convert Temperature" />
				</span>
			</div>

		</form>
	</div>
	<div class="weather">
		<c:forEach var="day" items="${weather}">
			<div class="forecastDay">
			
			<div class="weatherItemGrid">
			<div class="weatherImg">
			<c:url var="weatherUrl" value="/img/weather/${day.forecast}.png" />
				<img class="weatherImg" src="${weatherUrl}" />
			</div>	
			
			<div class="weatherForecast"> 
				<c:choose>
					<c:when test="${day.fivedayforecastvalue == 1 }">
						<c:out value="Today's weather" />
					</c:when>
					<c:otherwise>
						<c:out value="Weekly forecast" />
					</c:otherwise>
				</c:choose>
			</div>	

			<div class="weatherTemp">
				<c:choose>
					<c:when test="${isFahrenheit == true}">
					<c:out value="Low ${day.low}" />
					<c:out value="High ${day.high}" />
					
					</c:when>
					<c:otherwise>
					<fmt:formatNumber var="low" value="${(day.low - 32) * (5/9)}" maxFractionDigits="2" />
					<c:out value="Low ${low}" />
					<fmt:formatNumber var="high" value="${(day.high - 32) * (5/9)}" maxFractionDigits="2" />
					<c:out value="High ${high}" />
					</c:otherwise>
				</c:choose>
				</div>
				
				<div class="forecastWarning">
				<c:if test="${day.forecast.equals(\"partly cloudy\")}">
					<p>It's partly cloudy, please pack rain gear and bring
						waterproof, and maybe sunblock!</p>
				</c:if>
				<c:if test="${day.forecast.equals(\"sunny\")}">
					<p>It will be sunny, please pack sunblock</p>
				</c:if>
				<c:if test="${day.forecast.equals(\"rain\")}">
					<p>It's raining, please pack rain gear and bring waterproof
						shoes.</p>
				</c:if>

				<c:if test="${day.forecast.equals(\"thunderstorms\")}">
					<p>There will be thunderstorms, please seek shelter and avoid
						hiking on exposed ridges</p>
				</c:if>
				<c:if test="${day.forecast.equals(\"snow\")}">
					<p>There will be snow, please pack snow shoes</p>
				</c:if>
				</div>
				
				<div class="tempWarning">
				<c:if test="${day.high >= 70}">
					<p>It will be HOT! Please bring an extra gallon of water!</p>
					<c:out value="${day.forecast}" />
				</c:if>

				<c:if test="${day.low <= 20}">
					<p>It will be COLD! Please be wary of frostbite and
						hypothermia!</p>
				</c:if>
				<c:if test="${(day.high-day.low) >= 20}">
					<p>The temperature will fluctuate! Please wear breathable
						layers!</p>
				</c:if>
				</div>
			</div>
			</div>
		</c:forEach>
	</div>
</section>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />