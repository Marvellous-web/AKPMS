	var moduleName = "qaworksheetlayout";

	if($.cookie('show_tr_cookie') == "true"){
		$(".hide_tr").addClass('show_tr');
		$('#chkShowAll').attr('checked', true);
	}else{
		$(".hide_tr").removeClass('show_tr');
		$('#chkShowAll').attr('checked', false);
	}

//	function renderCheckboxes(sampleId, qaPatientInfoId, userId) {
//		var chkStr = "";
//
//		for (var i = 0; i < QC_POINTS.length; i++) {
//
//			chkStr += "<td class='chktd' onMouseOver='javascript:highlightBG(this, "+'"over"'+", " + QC_POINTS[i] + ");javascript:highlightNext(this, "+'"over"'+", " + QC_POINTS[i] + ")' onMouseOut='javascript:highlightBG(this, "+'"out"'+", " + QC_POINTS[i] + ");javascript:highlightNext(this, "+'"out"'+", " + QC_POINTS[i] + ")'>"+(parseInt(i)+parseInt(1))+"<input type='checkbox' id='qcpoint_"+ QC_POINTS[i] + "_" + sampleId + "_" + qaPatientInfoId + "_"+ userId + "' onclick='javascript:insertQCPoint(this);' ></td>";
//		}
//
//		return chkStr;
//	}

	/*function insertQCPoint_old(chk) {
		//alert("chk id :: "+chk.id);
		var chkId = chk.id;
		var chkInfo = chkId.split("_");
		var action = "";

		if (chk.checked) {
			action = "saveqcpoint";
		} else {
			action = "deleteqcpoint";
		}

		var qcPointId = chkInfo[1];
		var sampleId = chkInfo[2];
		var qaPatientInfoId = null;
		var userId;

		if (chkInfo[0] == "arqcpoint") {
			userId = chkInfo[3];
		} else {
			qaPatientInfoId = chkInfo[3];
			userId = chkInfo[4];
		}

		$.ajax({
			type : "POST",
			dataType : "json",
			url : contextPath + "/" + moduleName + "/" + action,
			data : {
				'sampleid' : sampleId,
				'qaPatientInfoId' : qaPatientInfoId,
				'qcPointId' : qcPointId,
				'userid': userId
			},
			success : function(qaPatientInfoId) {
				//alert('success: ' + qaPatientInfoId);
				$("#msgSuccess").html("Saved..");
				$("#msgSuccess").show().delay(1000).fadeOut();

				countErrors (chk);
			},
			error : function(error) {
				//alert('error: ' + eval(error));
			}
		});
	}*/
	function insertParam(key, value) {
        key = escape(key); value = escape(value);

        var kvp = document.location.search.substr(1).split('&');
        if (kvp == '') {
            document.location.search = '?' + key + '=' + value;
        }
        else {

            var i = kvp.length; var x; while (i--) {
                x = kvp[i].split('=');

                if (x[0] == key) {
                    x[1] = value;
                    kvp[i] = x.join('=');
                    break;
                }
            }

            if (i < 0) { kvp[kvp.length] = [key, value].join('='); }

            //this will reload the page, it's likely better to store this until finished
            document.location.search = kvp.join('&');
        }
    }

	$(document).ready(function() {

		$("#btnOrder").click(function() {
			var orderBy = $("#orderby").val();
			insertParam("orderby", orderBy);
		});

		$("#chkShowAll").click(function() {

			if(this.checked){
				$(".hide_tr").addClass('show_tr');

				// create cookie
				$.cookie('show_tr_cookie', 'true', { expires: 7 });

			}else{
				// delete cookie
				$.cookie('show_tr_cookie', null);

				$(".hide_tr").removeClass('show_tr');
			}
		});

		$(".addAccBtn").click(function() {
			var rowId = $(this).parent().parent().get(0).id;

			var rowInfo = rowId.split("_");

			var sampleId = rowInfo[1];

			var userId = rowInfo[2];
			$("#popupSampleId").val(sampleId);
			$("#popupUserId").val(userId);

			ajaxInsertBlankPatientInfo(sampleId, userId);
		});

		$(".deleteAccBtn").click(function() {
			var rowId = $(this).parent().parent().get(0).id;
			//var sampleId = rowId.replace(/[^0-9]+/ig, "");
			var rowInfo = rowId.split("_");

			var sampleId = rowInfo[1];

			var userId = rowInfo[2];

			ajaxDeletePatientInfo(sampleId, userId);
		});

//		$("input[name=deleteAccBtn]").click(function() {
//			var rowId = $(this).parent().parent().get(0).id;
//			//var sampleId = rowId.replace(/[^0-9]+/ig, "");
//			var rowInfo = rowId.split("_");
//
//			var sampleId = rowInfo[1];
//
//			var userId = rowInfo[2];
//
//			ajaxDeletePatientInfo(sampleId, userId);
//		});

		$(".hideAccBtn").live("click", function() {
			var rowId = $(this).parent().parent().get(0).id;
			//var sampleId = rowId.replace(/[^0-9]+/ig, "");
			var rowInfo = rowId.split("_");
			// code to hide patientinfo incase of payment or charge layout it not in use now since
			//we dont need to hide patientinfo just delete it
			var sampleId = '';
			var userId = '';
			var qaPatientInfoId = '';

			if (rowInfo[0] == 'patientInfo') {
				qaPatientInfoId = rowInfo[1];

				sampleId = $("#popupSampleId").val();
				if (sampleId == null || sampleId == '') {
					$("#popupSampleId").val($('#sampleId_' + qaPatientInfoId).val());
					sampleId = $('#sampleId_' + qaPatientInfoId).val();
				}

				userId = $("#popupUserId").val();
				if (userId == null || userId == '') {
					$("#popupUserId").val($('#userId_' + qaPatientInfoId).val())
					userId = $('#userId_' + qaPatientInfoId).val();
				}
				$("#popupQaPatientInfoId").val(qaPatientInfoId);

				ajaxHidePatientInfo(sampleId, userId, qaPatientInfoId);

			} else {
				sampleId = rowInfo[1];
				userId = rowInfo[2];
				ajaxHideSampleWithPatientInfo(sampleId, userId, $(this) );
			}
			// code run in case of arlayout
			//sampleId = rowInfo[1];
			//userId = rowInfo[2];
			//ajaxHideSampleWithPatientInfo(sampleId, userId);
		});


		$(".unHideAccBtn").live("click", function() {
			var rowId = $(this).parent().parent().get(0).id;
			//var sampleId = rowId.replace(/[^0-9]+/ig, "");
			var rowInfo = rowId.split("_");
			// code to hide patientinfo incase of payment or charge layout it not in use now since
			//we dont need to hide patientinfo just delete it
			var sampleId = '';
			var userId = '';
			var qaPatientInfoId = '';

			if (rowInfo[0] == 'patientInfo') {
				qaPatientInfoId = rowInfo[1];

				sampleId = $("#popupSampleId").val();
				if (sampleId == null || sampleId == '') {
					$("#popupSampleId").val($('#sampleId_' + qaPatientInfoId).val());
					sampleId = $('#sampleId_' + qaPatientInfoId).val();
				}

				userId = $("#popupUserId").val();
				if (userId == null || userId == '') {
					$("#popupUserId").val($('#userId_' + qaPatientInfoId).val())
					userId = $('#userId_' + qaPatientInfoId).val();
				}
				$("#popupQaPatientInfoId").val(qaPatientInfoId);

				ajaxUnHidePatientInfo(sampleId, userId, qaPatientInfoId);

			} else {
				sampleId = rowInfo[1];
				userId = rowInfo[2];
				ajaxUnHideSampleWithPatientInfo(sampleId, userId, $(this) );
			}
			// code run in case of arlayout
			//sampleId = rowInfo[1];
			//userId = rowInfo[2];
			//ajaxHideSampleWithPatientInfo(sampleId, userId);
		});

		$(".qcPopAccBtn").live("click", function() {
			var rowId = $(this).parent().parent().get(0).id;
			//var sampleId = rowId.replace(/[^0-9]+/ig, "");
			var rowInfo = rowId.split("_");

			var sampleId = '';
			var userId = '';
			var qaPatientInfoId = '';

			if (rowInfo[0] == 'patientInfo') {
				qaPatientInfoId = rowInfo[1];
				$("#popupQaPatientInfoId").val(qaPatientInfoId);

				sampleId = $("#popupSampleId").val();
				if (sampleId == null || sampleId == '') {
					$("#popupSampleId").val($('#sampleId_' + qaPatientInfoId).val());
					sampleId = $('#sampleId_' + qaPatientInfoId).val();
				}

				userId = $("#popupUserId").val();
				if (userId == null || userId == '') {
					$("#popupUserId").val($('#userId_' + qaPatientInfoId).val())
					userId = $('#userId_' + qaPatientInfoId).val();
				}

			} else {
				sampleId = rowInfo[1];
				userId = rowInfo[2];
				$("#popupSampleId").val(sampleId);
				$("#popupUserId").val(userId);
			}

			$.ajax({
				type : "POST",
				dataType : "json",
				url : contextPath + "/" + moduleName + "/fetchqcpointchecklist" ,
				data : {
					'sampleid' : sampleId,
					'qaPatientInfoId' : qaPatientInfoId
				},
				success : function(qcPointIds) {
					$("#qcPointPopup").modal('show');
					//$('input:checkbox').removeAttr('checked');
					$('.chkQcpoint').removeAttr('checked');

					for(var i = 0; i< qcPointIds.length; i++){
						$("#chk_qcpoint_"+qcPointIds[i]).attr('checked','checked');
					}
				},
				error : function(error) {
					//alert('error: ' + eval(error));
				}
			});

		});
	});

	function send_delete(patientInfoId) {
		var conf = confirm("Are you sure to delete this record?");

		if(!conf){
			return false;
		}

		$.ajax({
			type : "POST",
			dataType : "JSON",
			url : contextPath + "/" + moduleName + "/deletepatientinfo",
			data : {'id' : patientInfoId},
			success : function (data) {
				//alert("Patient Info deleted successfully");
				$('#patientInfo_' + patientInfoId).hide("slow");
				$('#patientInfo_' + patientInfoId).html("");

				$("#msgDelete").html("Deleted..");
				$("#msgDelete").show().delay(1000).fadeOut();

				totalErrors();
			},
			error : function(error) {
				//alert('error: ' + eval(error));
			}
		});
	}


	function ajaxUnHideSampleWithPatientInfo(sampleId, userId, element) {
		//var conf = confirm("");

		bootbox.confirm("Are you sure to Un-hide this sample record with its patient accounts (If any)?", function(result) {
			//Example.show("Confirm result: "+result);
			if(!result){
				//return false;
			}else{
				$.ajax({
					type : "POST",
					dataType : "json",
					url : contextPath + "/" + moduleName + "/hidesamplerecord",
					data : {
						'id' : sampleId,
						'hide': "false"
					},
					success : function(patientInfoIds) {
						//console.log("IDS:: "+ patientInfoIds);

						if(patientInfoIds != "" && patientInfoIds.length > 0){

							for (var i=0; i< patientInfoIds.length; i++) {
								//console.log("ID:: "+ patientInfoIds[i]);

								//$('#patientInfo_' + patientInfoIds[i]).show("slow");
								//$('#patientInfo_' + patientInfoIds[i]).html("");

								$('#patientInfo_' + patientInfoIds[i]).removeClass("hide_tr");

								if($("#chkShowAll").prop('checked') == true){
									$('#patientInfo_' + patientInfoIds[i]).removeClass("show_tr");
								}
							}
						}

						$('#sample_' + sampleId+"_"+userId).removeClass("hide_tr");
						//$('#sample_' + sampleId+"_"+userId).html("");

						if($("#chkShowAll").prop('checked') == true){
							$('#sample_' + sampleId+"_"+userId).removeClass("show_tr");
						}

						if($('#sample_' + sampleId+"_"+userId + '_other1').length > 0) {
							$('#sample_' + sampleId+"_"+userId + '_other1').removeClass("hide_tr");
							//$('#sample_' + sampleId+"_"+userId + '_other1').html("");

							if($("#chkShowAll").prop('checked') == true){
								$('#sample_' + sampleId+"_"+userId + '_other1').removeClass("show_tr");
							}
						}

						$("#msgDelete").html("Un-Hide Successfully..");
						$("#msgDelete").show().delay(1000).fadeOut();

						//totalErrors();


						element.removeClass("unHideAccBtn");
						element.addClass("hideAccBtn");

						element.attr("title","Un-Hide");
						element.children().first().removeClass("fa fa-eye");
						element.children().first().addClass("fa fa-eye-slash");

					},
					error : function(error) {
						//alert('error; ' + eval(error));
					}
				});
			}
		});
	}

	function ajaxHideSampleWithPatientInfo(sampleId, userId, element) {
		//var conf = confirm("");

		bootbox.confirm("Are you sure to hide this sample record with its patient accounts (If any)?", function(result) {
			//Example.show("Confirm result: "+result);
			if(!result){
				//return false;
			}else{
				$.ajax({
					type : "POST",
					dataType : "json",
					url : contextPath + "/" + moduleName + "/hidesamplerecord",
					data : {
						'id' : sampleId,
						'hide': "true"
					},
					success : function(patientInfoIds) {
						//console.log("IDS:: "+ patientInfoIds);

						if(patientInfoIds != "" && patientInfoIds.length > 0){

							for (var i=0; i< patientInfoIds.length; i++) {
								//console.log("ID:: "+ patientInfoIds[i]);

								$('#patientInfo_' + patientInfoIds[i]).addClass("hide_tr");

								if($("#chkShowAll").prop('checked') == true){
									$('#patientInfo_' + patientInfoIds[i]).addClass("show_tr");
								}
								//$('#patientInfo_' + patientInfoIds[i]).html("");
							}
						}

						$('#sample_' + sampleId+"_"+userId).addClass("hide_tr");
						//$('#sample_' + sampleId+"_"+userId).html("");

						if($("#chkShowAll").prop('checked') == true){
							$('#sample_' + sampleId+"_"+userId).addClass("show_tr");
						}

						if($('#sample_' + sampleId+"_"+userId + '_other1').length > 0) {
							$('#sample_' + sampleId+"_"+userId + '_other1').addClass("hide_tr");
							//$('#sample_' + sampleId+"_"+userId + '_other1').html("");

							if($("#chkShowAll").prop('checked') == true){
								$('#sample_' + sampleId+"_"+userId + '_other1').addClass("show_tr");
							}
						}

						$("#msgDelete").html("Hide Successfully..");
						$("#msgDelete").show().delay(1000).fadeOut();

						//totalErrors();

						element.removeClass("hideAccBtn");
						element.addClass("unHideAccBtn");

						element.attr("title","Un-Hide");
						//$(this).html("<i class='fa fa-eye'></i>");

						element.children().first().removeClass("fa fa-eye-slash");
						element.children().first().addClass("fa fa-eye");
					},
					error : function(error) {
						//alert('error; ' + eval(error));
					}
				});
			}
		});
	}
	//not in use we are not hiding patient info we just have to delete if user added it wrongly
	/*function ajaxHidePatientInfo(sampleId, userId, patientId) {
		//var conf = confirm("");

		bootbox.confirm("Are you sure to hide this sample record with its patient accounts (If any)?", function(result) {
			//Example.show("Confirm result: "+result);
			if(!result){
				return false;
			}else{
				$.ajax({
					type : "POST",
					dataType : "json",
					url : contextPath + "/" + moduleName + "/hidepatientrecord",
					data : {
						'id' : patientId
					},
					success : function(isHidden) {
						//console.log("IDS:: "+ patientInfoIds);
						if (isHidden) {
							$('#patientInfo_' + sampleId + '_' + userId + '_' + patientId).hide("slow");
							$('#patientInfo_' + patientId).html("");

							$("#msgDelete").html("Hide Successfully..");
							$("#msgDelete").show().delay(1000).fadeOut();
						} else {
							$('#patientInfo_' + sampleId + '_' + userId + '_' + patientId);
							$("#msgDelete").html("Failed to hide ..");
							$("#msgDelete").show().delay(1000).fadeOut();
						}

						totalErrors();
					},
					error : function(error) {
						//alert('error; ' + eval(error));
					}
				});
			}
		});
	}*/


	function ajaxDeletePatientInfo(sampleId, userId) {
		//var conf = confirm("");

		bootbox.confirm("Are you sure to delete this record?", function(result) {
			//Example.show("Confirm result: "+result);
			if(!result){
				//return false;
			}else{
				$.ajax({
					type : "POST",
					dataType : "json",
					url : contextPath + "/" + moduleName + "/deletesamplerecord",
					data : {
						'id' : sampleId
					},
					success : function(patientInfoIds) {
						//console.log("IDS:: "+ patientInfoIds);

						if(patientInfoIds != "" && patientInfoIds.length > 0){

							for (var i=0; i< patientInfoIds.length; i++) {
								//console.log("ID:: "+ patientInfoIds[i]);

								$('#patientInfo_' + patientInfoIds[i]).hide("slow");
								$('#patientInfo_' + patientInfoIds[i]).html("");
							}
						}

						$('#sample_' + sampleId+"_"+userId).hide("slow");
						$('#sample_' + sampleId+"_"+userId).html("");


						if($('#sample_' + sampleId+"_"+userId + '_other1').length > 0) {
							$('#sample_' + sampleId+"_"+userId + '_other1').hide("slow");
							$('#sample_' + sampleId+"_"+userId + '_other1').html("");
						}

						$("#msgDelete").html("Deleted..");
						$("#msgDelete").show().delay(1000).fadeOut();

						//totalErrors();
					},
					error : function(error) {
						//alert('error; ' + eval(error));
					}
				});
			}
		});

	}

	function countErrors(curObj) {
		var chkId = $(curObj).attr('id');

		var chkInfo = chkId.split("_");

		var action = "";

		if (chkInfo[0] == "arqcpoint") {
			sampleId = chkInfo[2];
			action = "error_count_per_sample";

			$.ajax({
				type : 'POST',
				dataType : 'JSON',
				url : contextPath + "/" + moduleName + "/"+action,
				data : {
					'qaWorksheetSampleId' : sampleId
				},
				success : function (totalCount) {
					$('#error_count_' + sampleId).html((totalCount));

					//totalErrors();
				}
			});

		} else {
			infoId = chkInfo[3];
			action = "error_count_per_account";

			$.ajax({
				type : 'POST',
				dataType : 'JSON',
				url : contextPath + "/" + moduleName + "/"+action,
				data : {
					'qaPatientInfoId' : infoId
				},
				success : function (totalCount) {
					$('#error_count_' + infoId).html((totalCount));

					//totalErrors();
				}
			});
		}
	}

	//function totalErrors () {
		//var count = $("[type='checkbox']:checked").length;
		//$('#totalErrorsMsg').html("Total Errors : " + count);
	//}

	//window.onload = totalErrors;


	// delete patient info in case of payment or charge
	$(".deletePatientAccBtn").live("click", function() {
		var rowId = $(this).parent().parent().get(0).id;

		var rowInfo = rowId.split("_");
		var qaPatientInfoId = rowInfo[1];

		bootbox.confirm("Are you sure to delete this patient account?", function(result) {
			//Example.show("Confirm result: "+result);
			if(!result){
				//return false;
			}else{
				$.ajax({
					type : "POST",
					dataType : "json",
					url : contextPath + "/" + moduleName + "/deletepatientinfo",
					data : {
						'id' : qaPatientInfoId
					},
					success : function(isHidden) {
						//console.log("IDS:: "+ patientInfoIds);
						if (isHidden) {
							$('#patientInfo_' + qaPatientInfoId).hide("slow");
							$('#patientInfo_' + qaPatientInfoId).html("");

							$("#msgDelete").html("Deleted..");
							$("#msgDelete").show().delay(1000).fadeOut();

							//totalErrors();
						} else {
							$("#msgDelete").html("Failed to hide ..");
							$("#msgDelete").show().delay(1000).fadeOut();
						}

						//totalErrors();
					},
					error : function(error) {
						//alert('error; ' + eval(error));
					}
				});
			}
		});
	});
