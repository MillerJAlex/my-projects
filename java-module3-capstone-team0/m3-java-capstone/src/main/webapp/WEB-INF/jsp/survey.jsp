<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />


<section class="survey content">
	<div class="content">

		<h1 class="centered topMarginPush">New Survey Post</h1>
		<br>
		<div class="surveyCentered">
			<c:url var="formPost" value="/survey" />
			<form method="POST" action="${formPost}">

				<span class="right topMarginPushSmall" style="font-size: 14px;"><c:out
						value="Favorite National Park:" /> <select name="parkCode"
					class="tab">
						<option value="CVNP">Cuyahoga Valley National Park</option>
						<option value="ENP">Everglades National Park</option>
						<option value="GCNP">Grand Canyon National Park</option>
						<option value="GNP">Glacier National Park</option>
						<option value="GSMNP">Great Smoky Mountains National Park</option>
						<option value="GTNP">Grand Teton National Park</option>
						<option value="MRNP">Mount Rainier National Park</option>
						<option value="RMNP">Rocky Mountain National Park</option>
						<option value="YNP">Yellowstone National Park</option>
						<option value="YNP2">Yosemite National Park</option>
				</select> </span> <br> <span class="right" style="font-size: 14px;"><c:out
						value="Email Address:" /> <input type="email" name="email"
					placeholder="sam@gmail.com" size="30" /> </span> <br> <span
					class="right" style="font-size: 14px;"><c:out
						value="State of Residence:" /> <input type="text-box"
					name="state" placeholder="Ohio" size="30" class="" /> </span> <br> <span
					class="right" style="font-size: 14px;"><c:out
						value="Activity Level:" /> <input type="text-box"
					name="activityLvl" placeholder="Olympian" size="30" class="" /> </span> <br>
				<input type="submit" name="submit" class="tab10" />
		</div>

	</div>
</section>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />

<c:import url="/WEB-INF/jsp/common/footer.jsp" />