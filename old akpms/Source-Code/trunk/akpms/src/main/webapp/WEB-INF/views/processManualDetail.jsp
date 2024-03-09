<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="downloadAction">
	<c:url value='/processmanual/fileDownload' />
</c:set>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/processmanual' />">&nbsp;Process Manual List</a> &raquo;</li>
			<li>&nbsp; Process Manual Details</li>
		</ul>
	</div>

	<sec:authorize ifAnyGranted="P-14">
		<div class="add-sub-btn">
			<input type="button" title="Add Sub - Section" value="Add Sub - Section" id="btnAddSubSection" class="btn" />
		</div>
	</sec:authorize>
	<div class="msg" style="padding-top: 25px;">
		<p class="success" id="msgP">${success}</p>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<!-- Section Content Start -->
	<div class="section-content" id="div_section">
		<h3>${section.title}</h3>
		<div class="keepDecoration">${section.content}</div>
		<div class="download-container">
			<ul>
				<c:forEach var="file" items="${section.files}">
					<c:if test="${file.deleted == false}">
						<li id="liFile_${file.id}" >
						<sec:authorize ifAnyGranted="P-14">
							<a href="javascript:void(0);" onclick='javascript:deleteFile(${file.id})' title="Delete File"><img title="Delete File" alt="Delete File" style="vertical-align: middle;"  src="<c:url value="/static/resources/img/cross.png"/>" ></a>  &nbsp;
						</sec:authorize>
						${file.name}<a class="download" href="${downloadAction}?id=${file.id}" title="Download File"></a>
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		<div class="section-links">
			<sec:authorize ifAnyGranted="P-14">
				<a href="<c:url value='/processmanual/add?id=${section.id}'/>">Edit</a>
				<c:choose>
					<c:when test="${section.status == true}">
						<c:set var="statusLabel" value="Inactivate"></c:set>
						<c:set var="parentStatusValue" value="0"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="statusLabel" value="Activate"></c:set>
						<c:set var="parentStatusValue" value="1"></c:set>
					</c:otherwise>
				</c:choose>
				<a
					id="aStatus_${section.id}_0_${parentStatusValue}_${parentStatusValue}"
					href="javascript:void(0)" onclick="javascript:changeStatus(this);">${statusLabel}</a>
				<a href='javascript:void(0);'
					onclick='javascript:deleteSection(${section.id},0)'>Delete</a>
				<a href="javascript:void(0);" id="aFileAttachment_${section.id}_0">Attach
					File</a>
			</sec:authorize>
			<a href="javascript:void(0);"
				id="aModificationHistory_${section.id}_0">Modification History</a>
		</div>
		
		<sec:authorize ifAnyGranted="Trainee">
		<c:if test="${section.showReadAndUnderstood}">
		   	<c:choose>
			<c:when test="${not section.readAndUnderstood}">
		     	<div class="read-container" id="readAndUnderstood_${section.id}_0">
		       		<input type="button" id="btnReadUnderstand_${section.id}_0" value="Read and Understood" title="Read and Understood" class="btn"/>
		     	</div>
		     </c:when>
				<c:otherwise>
					<input type="button" id="btnReadUnderstand_${section.id}_0" value="Read and Understood" title="Read and Understood" disabled="disabled"/>
				 </c:otherwise>
			</c:choose>
		</c:if>
		</sec:authorize>
	</div>
	<!-- Section Content End -->
	<!-- SubSection Content Start -->
	<div class="sub-section-content" id="div_subsection">
		<c:forEach var="subSection" items="${section.processManualList}">
			<c:if test="${subSection.deleted eq false}">
				<div id="div_subsection_${subSection.id}">
					<h3><a name="${subSection.id}">${subSection.title}</a></h3>
					<div class="sub-section-inner">
						<div class="keepDecoration">${subSection.content}</div>

						<div class="section-links">
							<sec:authorize ifAnyGranted="P-14">
								<a id="div_subsection_end_${subSection.id}" class="scrollOffset"></a>
								<a href="javascript:void(0);"
									id="aEdit_${section.id}_${subSection.id}">Edit</a>
								<c:choose>
									<c:when test="${subSection.status == true}">
										<c:set var="statusLabel" value="Inactivate"></c:set>
										<c:set var="statusValue" value="0"></c:set>
									</c:when>
									<c:otherwise>
										<c:set var="statusLabel" value="Activate"></c:set>
										<c:set var="statusValue" value="1"></c:set>
									</c:otherwise>
								</c:choose>
								<a id="aStatus_${section.id}_${subSection.id}_${statusValue}_${parentStatusValue}"
									href="javascript:void(0)"
									onclick="javascript:changeStatus(this);">${statusLabel}</a>
								<a href='javascript:void(0);' onclick='javascript:deleteSection(${section.id},${subSection.id})'>Delete</a>
								<a href="javascript:void(0);"
									id="aFileAttachment_${section.id}_${subSection.id}">Attach
									File</a>
							</sec:authorize>
							<a href="javascript:void(0);"
								id="aModificationHistory_${section.id}_${subSection.id}">Modification
								History</a>
						</div>

						<div class="download-container">
							<ul>
								<c:forEach var="file" items="${subSection.files}">
								<c:if test="${file.deleted == false}">
									<li id="liFile_${file.id}">
										<sec:authorize ifAnyGranted="P-14">
											<a href="javascript:void(0);" onclick='javascript:deleteFile(${file.id})' title="Delete File"><img title="Delete File" alt="Delete File" style="vertical-align: middle;" src="<c:url value="/static/resources/img/cross.png"/>" ></a> &nbsp;
										</sec:authorize>
										${file.name}<a class="download" href="${downloadAction}?id=${file.id}" title="Download File"></a>
									</li>
									</c:if>
								</c:forEach>
							</ul>
						</div>
						<sec:authorize ifAnyGranted="Trainee">
						<c:if test="${section.showReadAndUnderstood}">
				     	<c:choose>
				     	<c:when test="${not subSection.readAndUnderstood}">
					     	<div class="read-container">
					       		<input type="button" id="btnReadUnderstand_${section.id}_${subSection.id}" value="Read and Understood" title="Read and Understood" class="btn"/>
					     	</div>
					    </c:when>
					    <c:otherwise>
							<input type="button" id="btnReadUnderstand_${section.id}_${subSection.id}" value="Read and Understood" title="Read and Understood" disabled="disabled"/>
					    </c:otherwise>
					     	</c:choose>
					     	</c:if>
				     	</sec:authorize>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</div>
</div>
<div class="popup2" id="popup_modification_summary"
	style="display: none;">
	<span class="button bClose"><span>X</span></span>
	<div class="popup-container">
		<div class="popup-container-inner" id="modificationSummaryContent">
		<!--  modification records will appear here -->
		</div>
	</div>
</div>
<sec:authorize ifAnyGranted="P-14">
	<div class="popup2" id="popup_file_attachment" style="display: none;">
		<span class="button bClose"><span>X</span></span>
		<div class="popup-container">
			<div class="popup-container-inner">
				<span class="input-container"> <c:set var="formAction">
						<c:url value='/processmanual/fileupload' />
					</c:set> <form:form commandName="newFile" method="post"
						enctype="multipart/form-data" action="${formAction}">
						<form:hidden path="subProcessManualId" id="subProcessManualId" />
						<form:hidden path="processManual" id="processManualId" />
						<form:input type="file" path="attachedFile" name="upload"
							id="upload" size="44" />
						<span id="fileUploadError" class="error"></span>
						<input type="button" value="Save" class="submitButton" onclick="uploadFile(this.form)"/>

					</form:form>
				</span>
			</div>
		</div>
	</div>
	<div class="popup2" id="popup_edit_subsection" style="display: none;">
		<span class="button bClose"><span>X</span></span>
		<div class="popup-container">
			<div class="popup-container-inner">
				<c:set var="addSubSectionAction">
					<c:url value='/processmanual/add' />
				</c:set>
				<form:form commandName="processManual" action="${addSubSectionAction}">
					<h3>Add / Edit Sub Section</h3>
					<span class="input-container">
						<label>After </label>
						<form:select path="position">
							<form:option  id="${section.id}" value="1">${section.title}</form:option>
							<c:forEach var="subSection" items="${section.processManualList}">
								<c:if test="${subSection.deleted eq false}">
									<form:option id="${subSection.id}"  value="${subSection.position}">${subSection.title}</form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</span>
					<span class="input-container">
						<label>Title<em class="mandatory">*</em>:</label>
						<span class="input_text">
							<span class="left_curve"></span>
							<form:input path="title" class="mid"  maxlength="254" style="width: 373px;" onkeypress="return onEnterPress(event, this.form);" />
							<span class="right_curve"></span>
						</span>
					</span>
					<div id="titleError" class="error" style="padding: 1px 162px 0;"></div>
					<input type="hidden" name="parentId" id="parentId" value="${section.id}" />
					<input type="hidden" name="id" id="id" value="" />
					<input type="hidden" name="createdBy.id" id="createdBy" value="" />
					<span class="input-container">
						<form:label path="content">Content<em class="mandatory">*</em>:</form:label>
						<label id="hidSubSectionId" style="display: none;"></label>
					</span>
					<div class="input-container">
						<FCK:editor toolbarSet="Akpms" instanceName="content" width="550" height="200"  >
						    <jsp:attribute name="value">
						    ${processmanual.content}
						    </jsp:attribute>
						</FCK:editor>
						<div id="contentError" class="error"></div>
					</div>
					<span class="input-container">
						<form:label path="status">Status:</form:label>
						<form:radiobutton path="status" value="1" />Active <form:radiobutton
							path="status" value="0" />Inactive
					</span>
					<span class="input-container"> <form:label
							path="notification">Send Notification:</form:label> <form:checkbox
							path="notification" value="1" cssClass="notificaton" />
					</span>
					<span class="input-container" id="modificationSummaryContainer" style="display:none;">
						<form:label path="modificationSummary">Modification Summary:</form:label>
						<form:textarea rows="5" cols="20" path="modificationSummary" />
						<span id="modificationSummaryError" class="error" style="padding: 1px 162px 0;"></span>
						<form:errors class="invalid" path="modificationSummary" />
					</span>
					<span class="input-container"> <label>&nbsp;</label>
					<input type="button" title="${buttonName} ProcessManual" id="add_editSubsection"
						value="${buttonName}" class="submitButton" onclick="addSubsection(this.form)"/>
					<input type="button" title="Cancel" value="Cancel" class="login-btn"
						onclick="javascript:resetForm(this.form);callbackLoadSubsectionPopup();" />
						<c:if test="${operationType == 'Edit'}">
							<input type="button" title="Delete" value="Delete" class="login-btn" onclick="deleteProcessManual(${processManual.id})" />
						</c:if>
					</span>
				</form:form>
			</div>
		</div>
	</div>
</sec:authorize>

<!-- SubSection Content End -->
<script type="text/javascript">
	var parentStatus = ${section.status};
	var deletedSectionOnPage = new Array();

 	;(function($) {
	    // DOM Ready
	   $(function() {
	       // Binding a click event
	       $('a[id^=aModificationHistory]').bind('click', function(e) {
	           // Prevents the default action to be triggered.
	           e.preventDefault();
	           var elementInfoArray = this.id.split("_");
			   var sectionId = elementInfoArray[1];
	           if(elementInfoArray[2] > 0){
	        	   sectionId = elementInfoArray[2];
		       }

	           // Triggering bPopup when click event is fired
		           	$('#popup_modification_summary').bPopup(
			    	{
	                	onOpen: function() {
		                	var returnHTML = "";
		                	$.ajax({
		    					type : "POST",
		    					dataType : "json",
		    					url : contextPath+"/processmanual/modificationsummary/json",
		    					data : "id="+sectionId,
		    					success : function(data) {
		    						for (var i = 0, len = data.length; i < len; ++i) {
			    						var summary = data[i];
			    						returnHTML += "<div class='section-content' id='div_section' ><p>"+summary.modificationSummary+"</p><br /><strong>Modified By: </strong>"+summary.modifiedBy+" <strong>On: </strong> "+summary.modifiedOn+"</div>";
		    						}
		    						if(returnHTML == ""){
		    							$("#modificationSummaryContent").html("No record found.");
			    					}else{
			    						$("#modificationSummaryContent").html(returnHTML);
				    				}
		    						//alert("Query: " + data.query + " - Total affected rows: " + data.total);
		    					},
		    					error: function(data){
		    						//alert (data);
		    						$("#modificationSummaryContent").html("Error:"+data);
		    					}
		    				});
		                },follow: [true, false], //x, y
		                position: ['auto', 10] //x, y
	            	},function() {
	                	//window.scrollTo(0,0);
	            		$('html, body').animate({scrollTop:0}, 'slow');
	                }
	    	    );
	       });
	   });
	})(jQuery);
</script>
<sec:authorize ifAnyGranted="Trainee">
	<script type="text/javascript">
	;(function($) {
	    // DOM Ready
	   $(function() {
	       // Binding a click event
	       $('input[id^=btnReadUnderstand]').bind('click', function(e) {
	           // Prevents the default action to be triggered.
	           e.preventDefault();
	           // alert(this.id);
	           // 0: name, ,1: sectionId, 2: subSectionId
			   var buttonId = this.id;
	           var elementInfoArray = this.id.split("_");
			   var sectionId = elementInfoArray[1];
	           if(elementInfoArray[2] > 0){
	        	   sectionId = elementInfoArray[2];
		       }

	           // Triggering bPopup when click event is fired
	           	//$('#popup_file_attachment').bPopup();
	           	var isRead =  confirm("are you sure to confirm read and confirm the section?");
	           	if(isRead)
				{
	           		$.ajax({
						type : "POST",
						dataType : "json",
						url : contextPath + "/processmanual/readUnderstand/json",
						data : "id=" + sectionId,
						success : function(data) {
							$("#"+buttonId).removeClass("btn");
							document.getElementById(buttonId).disabled = true;
						},
						error: function (data){
							alert(data);
						}
					});
		        }
	       });
	   });
	})(jQuery);
	</script>
</sec:authorize>
<sec:authorize ifAnyGranted="P-14">
	<script type="text/javascript">
	var fckBody;

    function FCKeditor_OnComplete(editorInstance) {
    	//window.status = editorInstance.Description;
    	fckBody = editorInstance;
    }

    function resetEditor(dataString){
    	if ( $.browser.msie ) {
			fckBody.SetData(dataString);
		}else{
			$('#content').attr("value",dataString);
		}
    }

    function onEnterPress(evt, form){
    	var nbr = (window.event) ? event.keyCode : evt.which;

    	if (nbr == 13){
    		addSubsection(form);
    		return false;
    	}
    }

 	;(function($) {
	    // DOM Ready
	   $(function() {
	       // Binding a click event
	       $('a[id^=aFileAttachment]').bind('click', function(e) {
	           // Prevents the default action to be triggered.
	           e.preventDefault();
	           // alert(this.id);
	           // 0: name, ,1: sectionId, 2: subSectionId

	           var elementInfoArray = this.id.split("_");
	           $('#processManualId').val(elementInfoArray[1]);
	           $('#subProcessManualId').val(elementInfoArray[2]);

	           // Triggering bPopup when click event is fired
	           	$('#popup_file_attachment').bPopup({
	           		onOpen: function() {

	          			$('.filename').text("No file selected");

	                },onClose:function(){
	                	$('#newFile').each(function(){
		            	   this.reset();
		            	});

	                	$('#fileUploadError').text("");
	                	$.uniform.update();
	                }
	           	});
	       });
	   });

	   	function findPropertyWithValue(obj, val) {
		    for (var i in obj) {
		        if (obj.hasOwnProperty(i) && obj[i] == val) {
		            return i;
		        }
		    }
		    return null;
		}

	   $('#btnAddSubSection').bind('click', function(e) {
           	// Prevents the default action to be triggered.
           	e.preventDefault();
			if(!parentStatus){
				alert("The process manual is inactive. Please activate it to add Sub Sections")
			}else{
	           // Triggering bPopup when click event is fired
	           	$('#popup_edit_subsection').bPopup({
	           		onOpen: function() {

						// remove the options from position drop down which has deleted
						var deletedSection = jQuery.makeArray(deletedSectionOnPage);
						$('#position option').each(function() {
							if(findPropertyWithValue(deletedSection, $(this).attr('id')) != null){
								$(this).remove();
							}
						});

						$('#hidSubSectionId').text("");
	          			$('#titleError').text("");
	          			$('#contentError').text("");
	          			$('#modificationSummaryError').text("");
	          			$('#add_editSubsection').attr("title","Add");
	          			$('#add_editSubsection').attr("value","Add");
	          			$('#id').attr("value","");
						$('#title').attr("value","");

						if ( $.browser.msie ) {
							fckBody.SetData("");
						}else{
							$('#content').attr("value","");
						}

						$('#status1').attr('checked', 'checked');

						$('#processManual h3').text("Add Sub Section");
						$('#modificationSummaryContainer').hide();

						$("#notification1").attr("checked", true);
					    $.uniform.update();
	                },onClose: function() {
	                	resetEditor("");
	                }
	           	});
			}
       });

	   var subSectionData = ""; //on edit popup

       $('a[id^=aEdit]').bind('click', function(e) {
           // Prevents the default action to be triggered.
           e.preventDefault();
           //alert(this.id);
          	var elementInfoArray = this.id.split("_");
		   	var sectionId = elementInfoArray[2];
    	   // Triggering bPopup when click event is fired
          	$('#popup_edit_subsection').bPopup({
          		onOpen: function() {
          			loadSubsectionPopup(sectionId);
          			$('body').css("overflow", "hidden");
          			$('#hidSubSectionId').text(sectionId);
          			$('#titleError').text("");
          			$('#contentError').text("");
          			$('#modificationSummaryError').text("");
          			$('#processManual h3').text("Edit Sub Section");
          			$('#modificationSummary').attr("value","");
                },onClose: function(){
                	$('body').removeAttr("style");
                },modalClose: false,
                opacity: 0.6,
                positionStyle: 'fixed' //'fixed' or 'absolute'

          	},function() {
            	//window.scrollTo(0,0);
            	//$('html, body').animate({scrollTop:0}, 'slow');
            });
       });
	})(jQuery);

 	function callbackLoadSubsectionPopup(){
 		if ( $.browser.msie ) {
			sectionId = $('#hidSubSectionId').text();
			if(sectionId != ""){
				loadSubsectionPopup(sectionId);
			}
 		}
 	}

 	function loadSubsectionPopup(sectionId){
 		$.ajax({
			type : "GET",
			dataType : "json",
			url : contextPath + "/processmanual/subSection/json",
			data : "subSectionId=" + sectionId,
			success : function(data) {
				$('#id').attr("value",data.id);
				$('#title').attr("value",data.title);
				
				if ( $.browser.msie ) {
					fckBody.SetData(data.content);
				}else{
					$('#content').attr("value",data.content);
				}

				if(data.status){
					$('#status1').attr('checked', 'checked');
				}else{
					$('#status2').attr('checked', 'checked');
				}

				$('#parentId').attr("value",data.parentId);
				$('#parentId').attr("value",data.parentId);
				$('#createdBy').attr("value",data.createdBy);
				$('#add_editSubsection').attr("title","Update");
				$('#add_editSubsection').attr("value","Update");
				$('#modificationSummaryContainer').show();

				$("#notification1").attr("checked", data.notification);
			 	// select position
			    $('#position').val((data.position).toFixed(1));
				$.uniform.update();
			},
			error: function (data){
				console.log(data);
			}
		});
 	}

 	function deleteSection(sectionId, subSectionId){
 		var confirmation = false;
 		var id = "";

 	 	if(subSectionId > 0){
 	 		confirmation = confirm("Are you sure you wish to delete this sub section?");
 	 		id = subSectionId;
 	 	}else{
 	 		confirmation = confirm("Deleting the main section will also delete its sub-sections. Are you sure you wish to proceed?");
 	 		id = sectionId;
	 	}

	 	if(confirmation){
	 		$.ajax({
				type : "POST",
				dataType : "json",
				url : contextPath + "/processmanual/delete",
				data : "item=" + id,
				success : function(data) {
					/*alert("Query: " + data.query
							+ " - Total affected rows: "
							+ data.total);*/
					// if parent has deleted
					if(subSectionId > 0){
						$("#msgP").text("Section has been successfully deleted.");
						$("#div_subsection_"+subSectionId).hide('slow');
						deletedSectionOnPage[deletedSectionOnPage.length] = subSectionId;
					}else{
						window.location.href = contextPath+"/processmanual?successUpdate=2";
					}
				},
				error: function (data){
					alert(data);
				}
			});
		}
 	}

 	function deleteFile(fileId)
 	{
 		if(confirm("Are you sure you wish to delete this Attachment?")){
	 		$.ajax({
				type : "POST",
				dataType : "json",
				url : contextPath + "/processmanual/fileDelete",
				data : "id=" + fileId,
				success : function(data){
					$("#liFile_"+fileId).hide('slow');
				},
				error: function (data){
					alert(data);
				}
			});
 		}
 	}
	function changeStatus(htmlElement)
	{
		var confirmation = false;
		var sectionId = "";
		var elementInfoArray = htmlElement.id.split('_');
		/*
		 0: a
		 1: SectionId
		 2: Sub-sectionId
		 3: 1:activate/0:inactivate (status)
		 4: parentStatus
		*/

		//check if action for activation
		if(elementInfoArray[3] == "1"){
			// check parent section clicked
			if(elementInfoArray[2] == "0"){
				sectionId = elementInfoArray[1];
			}else{
				sectionId = elementInfoArray[2];
				// check parent inactivated
				if(elementInfoArray[4] == "1"){
					alert ("Please activate the main section before activating this sub section.");
					return true;
				}
			}
			confirmation = confirm("Are you sure to activate this section?");
		}else{
			// check parent section clicked
			if(elementInfoArray[2] == "0"){
				confirmation = confirm("Inactivating this section will also inactivate its all sub-sections. Are you sure you wish to proceed?");
				sectionId = elementInfoArray[1];
			}else{
				confirmation = confirm("Are you sure to inactivate this section?");
				sectionId = elementInfoArray[2];
			}
		}

		if(confirmation){
			$.ajax({
				type : "POST",
				dataType : "json",
				url : contextPath + "/processmanual/changeStatus",
				data : "id="+ sectionId +"&status=" + elementInfoArray[3],
				success : function(data) {
					// check if action for activation
					if(elementInfoArray[3] == "1"){
						// in case of parent change parent status for every child
						if(elementInfoArray[2] == "0"){
							$('a[id^=aStatus]').each(function(i,el) {
								if(el.id == htmlElement.id){
									$("#"+htmlElement.id).text("Inactivate");
									$("#"+el.id).attr("id", "aStatus_"+sectionId+"_0_0_0");
								}else{
									tempArr = el.id.split('_');
									$("#"+el.id).attr("id", "aStatus_"+tempArr[1]+"_"+tempArr[2]+"_"+tempArr[3]+"_0");
								}
							});
							parentStatus = true;
						}else{
							$("#"+htmlElement.id).text("Inactivate");
							$("#"+htmlElement.id).attr("id", "aStatus_"+elementInfoArray[1]+"_"+elementInfoArray[2]+"_0_"+elementInfoArray[4]);
						}
						$("#msgP").text("Section has been successfully activated.");
					}else{
						// in case of parent inactivate all childs
						if(elementInfoArray[2] == "0"){
							$('a[id^=aStatus]').each(function(i,el) {
								tempArr = el.id.split('_');
								$(el).text("Activate");
								$("#"+el.id).attr("id", "aStatus_"+tempArr[1]+"_"+tempArr[2]+"_1_1");
							});

							$("#msgP").text("Section and its sub-sections have been successfully inactivated.");
							parentStatus = false;
						}else{
							$("#"+htmlElement.id).text("Activate");
							$("#"+htmlElement.id).attr("id", "aStatus_"+elementInfoArray[1]+"_"+elementInfoArray[2]+"_1_"+elementInfoArray[4]);
							$("#msgP").text("Section has been successfully inactivated.");
						}
					}
				}, error : function (data){
					alert(data);
				}
			});
		}
	}

	function addSubsection(form)
	{
		var isValid = true;
		var title = form.elements["title"].value;

		if(fckBody.GetHTML().length == 0){
			$('#contentError').text("Process manual content cannot be left blank. Please try again.");
			isValid = false;
		}else{
			$('#contentError').text("");
			isValid = true;
		}

		if(($.trim(title)).length==0){
			$('#titleError').text("Process manual title cannot be left blank. Please try again.");
			isValid = false;
		}

		if($('#id').val() > 0){
			var modificationSummary = form.elements["modificationSummary"].value;

			if(($.trim(modificationSummary)).length==0){
				$('#modificationSummaryError').text("Process manual ModificationSummary cannot be left blank. Please try again.");
				isValid = false;
			}
		}

		if(!parentStatus){
		  	if($('input[name=status]:checked').val()==1){
				isValid = false;
		  		alert("Please activate the main section before activating this sub section.");
		  	}
		}

		if(isValid){
			disabledButtons();
			form.submit();
		}
	}

	function uploadFile(form)
	{
		var isValid = true;
		var fileName = document.getElementById('upload').value;
		if(fileName==""||fileName==null)
			{
			$('#fileUploadError').text("Please select the file to be uploaded.");
			var isValid = false;
			}
		if(isValid)
			{
			form.submit();
			}
	}
 </script>
</sec:authorize>
