<div class="dashboard-container">
	<div class="form-container">
		<div class="form-container-inner">
			<div class="msg">
				<p class="errror">${error}</p>
				<p class="success">${success}</p>
			</div>
			<br/>
			&nbsp;&nbsp;<input type="button" value="Close" onclick="window.close();" class="btn"  />		
		</div>
	</div>
</div>

<script type="text/javascript">
function reloadParentFlexigrid() {
	if (window.opener && !window.opener.closed) {
		if( window.opener.reloadFlexigrid ) {
			window.opener.reloadFlexigrid();
		}
    }
}
window.onload = reloadParentFlexigrid;
</script>