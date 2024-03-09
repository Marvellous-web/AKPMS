<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="newline" value="<%= \"\n\" %>" />
<c:set var="newline1" value="<%= \"\r\" %>" />

<script type="text/javascript">
var currval = "${traineeEvaluate.rating}";
$(document).ready(function() {
	if(currval == ""){
		currval = 0;
	}

	$('#rate1').rating('-', {maxvalue:5,increment:.5,curvalue:currval});
	$('#rate2').rating('-', {maxvalue:5,increment:.5,curvalue:currval});
});

function evaluateTrainee(form){
	var comment = form.elements["comment"].value;
	if(($.trim(comment)).length==0)	{
		if(form.name=="IDS_TL"){
			$('#msg1').text("Please write the comment.");
		}else{
			$('#msg2').text("Please write the comment.");
		}
		return false;
	}else{
		disabledButtons();
		form.elements["type"].value = form.name;
		if(currentVal<0){
			form.elements["rating"].value = "${traineeEvaluate.rating}";
		}else{
			form.elements["rating"].value = currentVal;
		}
		form.submit();
	}
}

;(function($) {
    // DOM Ready
   $(function() {
       // Binding a click event
       // From jQuery v.1.7.0 use .on() instead of .bind()
       $('#evaluateIdsArgus').bind('click', function(e) {
           // Prevents the default action to be triggered.
           e.preventDefault();
           // Triggering bPopup when click event is fired
           $('#popup_trainee_evaluation_ids_argus').bPopup({
               onOpen: function() {
            	   $('#msg1').text("");
            	   $('#msg2').text("");
            	   $('#rate1').rating('-', {maxvalue:5,increment:.5,curvalue:currval});

            	   traineeComment = '${fn:replace(fn:replace(traineeEvaluate.comment, newline, "<br />"), newline1,"")}';
	         	   $('#IDS_TL #comment').html(traineeComment.replace(/\<br\>/g, "\n").replace(/\<br \/\>/g, "\n"));
               }
           });
       });
       $('#evaluateArgus').bind('click', function(e) {
           // Prevents the default action to be triggered.
           e.preventDefault();
           // Triggering bPopup when click event is fired
           $('#popup_trainee_evaluation_argus').bPopup({
               onOpen: function() {
            	   $('#msg1').text("");
            	   $('#msg2').text("");
            	   $('#rate2').rating('-', {maxvalue:5,increment:.5,curvalue:currval});

            	   traineeComment = '${fn:replace(fn:replace(traineeEvaluate.comment, newline, "<br />"), newline1,"")}';
	         	   $('#Argus_TL #comment').html(traineeComment.replace(/\<br\>/g, "\n").replace(/\<br \/\>/g, "\n"));
               }
           });
       });
   });
})(jQuery);
</script>

<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/traineeevaluation' />">&nbsp;Trainee List</a> &raquo;</li>
			<li>&nbsp;Evaluate Trainee</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->

	<div class="form-container">
	<div class="form-container-inner">
			<span class="input-container">
				<h3>Process Manual Read Status(${adminSettings.proceesManaulReadStatusRatings}%)</h3>${totalProcessManualsReadByUser}/${totalProcessManuals} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${processManulReadPercent}%
			</span>
			<span class="input-container">
				<h3>Evaluation By IDS-Argus(${adminSettings.idsArgusRatings}%)</h3> ${avgIDSRating}/5 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${idsArgusPercent}%
			</span><br/>

			<c:forEach var="comments" items="${traineeIDSArgusEvaluationList}">

				<div class="section-content" id="div_section" >
					<p>${fn:replace(fn:replace(comments.comment, newline, "<br />"), newline1,"")}</p>					
					<p>${comments.rating}/5  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>By:</strong>${comments.ratedBy.firstName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong> On:</strong><fmt:formatDate value="${comments.modifiedOn}" pattern="MM/dd/yyyy"/></p>
				</div>
			</c:forEach>
			<br/>
			<button id="evaluateIdsArgus" class="login-btn">${operation}</button><br/>

			<div class="popup2" id="popup_trainee_evaluation_ids_argus" style="display:none;">
			   <span class="button bClose"><span>X</span></span>
			   <div class="popup-container">
			   		<div class="popup-container-inner" id="modificationSummaryContent">
			   			<h3>Evaluation By IDS-Argus</h3>
						<form:form commandName="traineeEvaluate" id="IDS_TL" name="IDS_TL">
							<span class="button bClose"><span>X</span></span>
							<span class="input-container">
								<label>Comment</label>
							</span>
							<span class="input-container">
								<form:textarea rows="4" cols="100" path="comment" style="width: auto" />
								<span id="msg1" class="error"></span>
								<form:errors class="invalid" path="comment" />
							</span>
							<br/>
							<div class="input-container">
								<label>Trainee Rating</label>
								<div id="rate1" class="rating">&nbsp;</div>
								<form:hidden path="id"/>
								<form:hidden path="type"/>
								<form:hidden path="rating"/>
							</div>
							<input type="button" title="Submit" value="Submit" class="submitButton" onclick="evaluateTrainee(this.form)"/>
						</form:form>
			   		</div>
			   </div>
			</div>


			<span class="input-container">
				<h3>Evaluation By Argus(${adminSettings.argusRatings}%)</h3> ${avgArgusRating}/5 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${argusPercent}%
			</span><br/>

			<c:forEach var="comments" items="${traineeArgusEvaluationList}">

				<div class="section-content" id="div_section" >
					<p>${fn:replace(fn:replace(comments.comment, newline, "<br />"), newline1,"")}</p>
					<p>${comments.rating}/5  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>By:</strong>${comments.ratedBy.firstName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong> On:</strong><fmt:formatDate value="${comments.modifiedOn}" pattern="MM/dd/yyyy"/></p>
				</div>
			</c:forEach>

			<br/>
			<button id="evaluateArgus" class="login-btn">${operation}</button>

			<div class="popup2" id="popup_trainee_evaluation_argus" style="display:none;">
			   <span class="button bClose"><span>X</span></span>
			   <div class="popup-container">
			   		<div class="popup-container-inner" id="modificationSummaryContent">
			   			<h3>Evaluation By Argus</h3>
						<form:form commandName="traineeEvaluate" id="Argus_TL" name="Argus_TL">
							<span class="button bClose"><span>X</span></span>
							<span class="input-container">
							 	<label>Comment</label>
							</span>
							<span class="input-container">
								<form:textarea rows="4" cols="100" path="comment" style="width: auto"/>
								<span id="msg2" class="error"></span>
								<form:errors class="invalid" path="comment" />
							</span>
							<br/>
							<div class="input-container">
								<label>Trainee Rating</label>
								<div id="rate2" class="rating">&nbsp;</div>
								<form:hidden path="id"/>
								<form:hidden path="type"/>
								<form:hidden path="rating"/>
							</div>
							<input type="button" title="Submit" value="Submit" class="submitButton" onclick="evaluateTrainee(this.form)"/>
						</form:form>
			   		</div>
			   </div>
			</div>

		</div>
	</div>
</div>


