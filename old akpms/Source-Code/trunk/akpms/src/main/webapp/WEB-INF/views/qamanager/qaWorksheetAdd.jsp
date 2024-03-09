<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="dashboard-container">
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="<c:url value='/' />">Home</a> &raquo;</li>
			<li><a href="<c:url value='/qamanager' />">&nbsp;QA Worksheet List</a>&raquo;</li>
			<li>&nbsp;${operationMode} QA Worksheet</li>
		</ul>
	</div>
	<div class="clr"></div>
	
	<form:form modelAttribute="qaworksheet" id="form1" name="form1" cssClass="form-container" >
		<div class="form-container-inner">
			<div class="row-container">
				<div class="element-block">
					<form:label path="department.id">Department:<em class="mandatory">*</em></form:label>
					<span class="select-container2" style="display:inline;">
						<form:select path="department.id" id="departmentId" cssClass="selectbox">
							<form:option value="">-:Select Department:-</form:option>
							<c:forEach items="${parentDepartments}" var="parent">
								<form:option value="${parent.id}">${parent.name}</form:option>
							</c:forEach>
						</form:select>
					</span>
					<form:errors path="department.id" cssClass="invalid"/>
				</div>
				<div class="element-block">
					<form:label path="subDepartment.id">Sub Department:</form:label>
					<span class="select-container2" style="display:inline;">
						<form:select path="subDepartment.id" id="subDepartmentId" cssClass="selectbox" >
							<form:option value="-1">All</form:option>
						</form:select>
					</span>
					<form:errors path="subDepartment.id" cssClass="invalid"/>
				</div>
				<div class="clr"></div>
			</div>
			<div class="row-container" style="display: none" id="arstatusdiv">
				<div class="element-block" >
					<form:label path="arStatusCode">AR Status Codes: </form:label>
					<span class="select-container2" style="display:inline;">
						<form:select path="arStatusCode" id="arstatus" cssClass="selectbox">
							<form:option value="">-:Select Status Code:-</form:option>
							<c:forEach items="${arStatusCodes}" var="code">
								<form:option value="${code.key}">${code.value}</form:option>
							</c:forEach>
						</form:select>
					</span>
					<form:errors path="arStatusCode" cssClass="invalid"/>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container" style="display: none" id="accPercentageDiv">
				<div class="element-block" >
					<form:label path="arStatusCode">Account Percentage:</form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="accountPercentage" type="text" cssClass="mid numbersOnly"
							cssStyle="width:32px" maxlength="3" size="2" /><Strong>%</Strong>
						<span class="right_curve"></span>
					</span>
					<form:errors path="accountPercentage" cssClass="invalid"/>
				</div>
				<div class="clr"></div>			
			</div>
			
			<div class="row-container">
				<div class="element-block">
					<form:label path="billingMonth">Billing Month:<em class="mandatory">*</em></form:label>
					<span class="select-container2">
						<form:select path="billingMonth" cssClass="selectbox">
							<form:option value="">-:Select month:-</form:option>
							<c:forEach var="month" items="${months}">
								<form:option value="${month.key}"> ${month.value} </form:option>
							</c:forEach>
						</form:select>
					</span>
					<form:errors path="billingMonth" cssClass="invalid"/>
					<p class="invalid" id="errorMsgMonth"></p>
				</div>
				<div class="element-block">
					<form:label path="billingYear">Billing Year:<em class="mandatory">*</em></form:label>
					<span class="select-container2">
						<form:select path="billingYear" cssClass="selectbox">
							<form:option value="">-:Select Year:-</form:option>
							<c:forEach var="year" items="${years}">
								<form:option value="${year}">${year}</form:option>
					    	</c:forEach>
						</form:select>
					</span>
					<form:errors path="billingYear" cssClass="invalid"/>
					<p class="invalid" id="errorMsgYear"></p>
				</div>
				<div class="clr"></div>
			</div>
		
			<div class="row-container">
				<div class="element-block">
					<form:label path="postingDateFrom">Posting Date From:</form:label>
					<form:errors path="postingDateFrom" />
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="postingDateFrom" type="text" cssClass="mid" cssStyle="width:100px;"  readonly="true"/>
						<span class="right_curve"></span>
					</span>
					<div class="msg">
						<p class="invalid" id="errorMsgFrom"></p>
					</div>
				</div>
				<div class="element-block" >
					<form:label path="postingDateTo">Posting Date To:</form:label>
					<form:errors path="postingDateTo" />
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="postingDateTo" type="text" cssClass="mid" cssStyle="width:100px;" readOnly="true"/>
						<span class="right_curve"></span>
					</span>
					<div class="msg">
						<p class="invalid" id="errorMsgTo"></p>
					</div>
				</div>
				<div class="clr"></div>
			</div>
			
			<div class="row-container">
				<div class="element-block">
					<form:label path="type">Type:<em class="mandatory">*</em></form:label>
					<span>General:</span> <form:radiobutton path="type" value="2"/>
					<span>By Staff:</span> <form:radiobutton path="type" value="1"/>
					<span>By Doctor:</span> <form:radiobutton path="type" value="3"/>
					<form:errors path="type" cssClass="invalid"/>
				</div>
				<div class="element-block">
					<form:label path="name">Worksheet Name:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="name" id="worksheetName" type="text" cssClass="mid" cssStyle="width:150px;"/>
						<span class="right_curve"></span>
					</span>
					<form:errors path="name" cssClass="invalid"/>
				</div>
				<div class="clr"></div>
			</div>
				
			<div class="row-container">
				<div class="element-block" id="divGeneralBox" style="visibility:hidden;">
					<form:label path="name">General Percentage:<em class="mandatory">*</em></form:label>
					<span class="input_text">
						<span class="left_curve"></span>
						<form:input path="generalPercentage" type="text" name="generalBox" id="generalBox" cssClass="mid numbersOnly"
							cssStyle="width:32px" maxlength="3" size="2" /><Strong>%</Strong>
						<span class="right_curve"></span>
					</span>
					<form:errors path="generalPercentage" cssClass="invalid"/>
				</div>
				<div class="element-block">
						<input type="submit" id="saveQAWorksheet" class="login-btn" value="${buttonName}" onclick="return validation();" />
						<input type="reset" id="cancel" class="login-btn" value="Reset"/>
					<!-- <div class="element-block" style="margin-left: 10px;">
						<input type="button" title="Run QA Worksheet" value="Run QA Worksheet" class="login-btn" />
					</div> -->
				</div>
				<div class="clr"></div>
			</div>
		</div>
	</form:form>
</div>
<script type="text/javascript" src="<c:url value='/static/resources/js/common.js'/>"></script>

<script type="text/javascript">
    function getSubDept(){
        var subDepartmentId = "${subDepartmentId}";
    	 $.ajax({
	    		type : "Get",
	    		datatype:"json",
	     		url : contextPath + "/qamanager/subdepartment",
	     		data :"parentDepartmentId=" + $('#departmentId').val(),
	     		success : function(data) {
	     			//alert('success ' + data);
                  var html = '<option value="-1">All</option>';
                  var len = data.length;
                  for(var i=0; i<len; i++){
                		if(data[i].id == subDepartmentId){
                			html += '<option value="' + data[i].id + '" selected >' + data[i].name + '</option>';
                    	}else{
                    		html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                        }    
                   }
                  $('#subDepartmentId').empty().append(html);
                  $.uniform.update();
             },
 		});

     }
     
      $(document).ready(function() {
         $('select#departmentId').change(function(){
        	 getSubDept();
        	 showHide();
         });
         
         //populate subdepartment when editing qaworksheet
         getSubDept();
         $(function() {
     	    $( "#postingDateFrom" ).datepicker();
     	    $( "#postingDateTo" ).datepicker();
     	 });

         $('input[name=type]:radio').change(showHide);
      });


    	function showHide() {
    	    var val = $("input[name=type]:checked").val();
    	    
    	    if (val == '2') {
    	        $('#divGeneralBox').visible();
    	    } else {
    	    	$('#divGeneralBox').invisible();
    	    }

    	    var selectedKey = $('select#departmentId').val();
        	 //3 is the Id of ardepartment
        	 $('#arstatusdiv').css("display","none");
        	 $('#accPercentageDiv').css("display","none");
        	 
        	 if(selectedKey == '1') {
        		 $('#accPercentageDiv').css("display","block");
        	 }else if (selectedKey == '3') {
        		 $('#arstatusdiv').css("display","block");
        	 }
    	}
    	
    	showHide();
     
        function validation(){
            var flag = true;
            $("#errorMsgFrom").html("");
            $("#errorMsgTo").html("");
            $('#errorMsgMonth').html("");
            $('#errorMsgYear').html("");
            
            if($.trim($('#postingDateFrom').val()) == '' && $.trim($('#postingDateTo').val()) != ''){
            	$("#errorMsgFrom").html("Select Posting Date From.");
            	flag = false;
            }else if($.trim($('#postingDateFrom').val()) != '' && $.trim($('#postingDateTo').val()) == ''){
            	$("#errorMsgTo").html("Select Posting Date To.");
            	flag = false;
            }else if($.trim($('#postingDateFrom').val()) != "" && $.trim($('#postingDateTo').val()) != ""){
            	if(Date.parse($('#postingDateFrom').val()) > Date.parse($('#postingDateTo').val()) ){
            		$('#errorMsgTo').html("Posting date 'To' date must be after or equal to Posting date 'From' date. Please try again.");
            		flag = false;
      			}
            }

        	//null != $('#month') && null != $('#year')
        	
        	if ($('#billingMonth').val() != "" && $('#billingYear').val() != ""){
        		if($.trim( $('#postingDateFrom').val()) != ""){
    	  			if ($('#billingMonth').val() != parseInt($('#postingDateFrom').val().substring(0, 2))) {
    	  				$('#errorMsgMonth').html('Selected month and date of month must be same or earlier');
    	  				flag = false;
    	  			} else if ($('#billingYear').val() != $('#postingDateFrom').val().substring(6, 10)) {
    	  				$('#errorMsgYear').html('Selected year and date of year must be same or earlier');
    	  				flag = false;
    	  			}
    			}

    			if($.trim($('#postingDateTo').val()) != ""){
    	  			if ($('#billingMonth').val() != parseInt($('#postingDateTo').val().substring(0, 2))) {
    	  				$('#errorMsgMonth').html('Selected month and date of month must be same or earlier.');
    	  				flag = false;
    	  			} else if ($('#billingYear').val() != $('#postingDateTo').val().substring(6, 10)) {
    	  				$('#errorMsgYear').html('Selected year and date of year must be same or earlier');
    	  				flag = false;
    	  			}
    	  		}
            }
	  		
	  		return flag;
        }
</script>
