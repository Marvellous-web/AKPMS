<div class="dashboard-container" >
	<!-- Bread Crumb Start-->
	<div id="bread-crumb">
		<ul>
			<li><a href="#">Home &raquo; </a></li>
			<li>Dashboard</li>
		</ul>
	</div>
	<div class="clr"></div>
	<!-- Bread Crumb End-->
	<!-- Notification Start-->
	<!-- <div class="notification">
           <h3>Notifications</h3>
          <ul>
            <li> Lorem ipsum dolor sit amet, consectetuer adipiscing elit.</li>
            <li> Nam liber tempor cum soluta nobis eleifend option congue nihil.</li>
            <li> Eodem modo typi, qui nunc nobis</li>
          </ul>
        </div> -->
	<!-- Notification End-->
	<div class="content-box">
		<!-- Statistics Start -->
		<div class="stat-box">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th colspan="2"><h3>Users</h3></th>
				</tr>
				<tr class="odd">
					<td align="left" class="stat-left-txt"><a href="admin/user">Total Users</a></td>
					<td align="right" class="stat-right-txt">${totalUsers }</td>
				</tr>
				<tr class="even">
					<td align="left" class="stat-left-txt"><a href="admin/user?status=1">Active Users</a></td>
					<td align="right" class="stat-right-txt">${activeUsers}</td>
				</tr>
				<tr class="odd">
					<td align="left" class="stat-left-txt left-round"><a href="admin/user?status=0">Inactive Users</a></td>
					<td align="right" class="stat-right-txt right-round" >${inactiveUsers}</td>
				</tr>

			</table>
		</div>
		<!-- Statistics End -->
		<!-- Activities Start -->
		<div class="activity-box">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th colspan="2"><h3>Departments</h3></th>
				</tr>
				<tr class="odd">
					<td align="left" class="stat-left-txt"><a href="${contextPath}admin/department">Total Departments</a></td>
					<td align="right" class="stat-right-txt"><a href="${contextPath}admin/department">${totalDepts}</a></td>
				</tr>
				<tr class="even">
					<td align="left" class="stat-left-txt"><a href="${contextPath}admin/department?status=1">Active Departments</a></td>
					<td align="right" class="stat-right-txt"><a href="${contextPath}admin/department?status=1">${activeDepts}</a></td>
				</tr>
				<tr class="odd">
					<td align="left" class="stat-left-txt left-round"><a href="${contextPath}admin/department?status=0">Inactive Departments</a></td>
					<td align="right" class="stat-right-txt right-round"><a href="${contextPath}admin/department?status=0">${inactiveDepts}</a></td>
				</tr>

			</table>
		</div>
		<div class="clr"></div>
		<!-- Activities End -->
	</div>

	<div class="content-box">
		<!-- Statistics Start -->
		<div class="stat-box">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th colspan="2"><h3>Doctors</h3></th>
				</tr>
				<tr class="odd">
					<td align="left" class="stat-left-txt"><a href="${contextPath}admin/doctor">Total Doctors</a></td>
					<td align="right" class="stat-right-txt"><a href="${contextPath}admin/doctor">${totalDoctors}</a></td>
				</tr>
				<tr class="even">
					<td align="left" class="stat-left-txt"><a href="${contextPath}admin/doctor?status=1">Active Doctors</a></td>
					<td align="right" class="stat-right-txt"><a href="${contextPath}admin/doctor?status=1">${activeDoctors}</a></td>
				</tr>
				<tr class="odd">
					<td align="left" class="stat-left-txt left-round"><a href="${contextPath}admin/doctor?status=0">Inactive Doctors</a></td>
					<td align="right" class="stat-right-txt right-round"><a href="${contextPath}admin/doctor?status=0">${inactiveDoctors}</a></td>
				</tr>

			</table>
		</div>
		<!-- Statistics End -->
		<!-- Activities Start -->
		<div class="activity-box">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th colspan="2"><h3>Insurances</h3></th>
				</tr>
				<tr class="odd">
					<td align="left" class="stat-left-txt"><a href="${contextPath}admin/insurance">Total Insurances</a></td>
					<td align="right" class="stat-right-txt"><a href="${contextPath}admin/insurane">${totalInsurances}</a></td>
				</tr>
				<tr class="even">
					<td align="left" class="stat-left-txt"><a href="${contextPath}admin/insurance?status=1">Active Insurances</a></td>
					<td align="right" class="stat-right-txt"><a href="${contextPath}admin/insurance?status=1">${activeInsurances}</a></td>
				</tr>
				<tr class="odd">
					<td align="left" class="stat-left-txt left-round"><a href="${contextPath}admin/insurance?status=0">Inactive Insurances</a></td>
					<td align="right" class="stat-right-txt right-round"><a href="${contextPath}admin/insurance?status=0">${inactiveInsurances}</a></td>
				</tr>

			</table>
		</div>
		<div class="clr"></div>
		<!-- Activities End -->
	</div>
	<div class="content-box">
		<!-- Statistics Start -->
		<div class="stat-box">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th colspan="2"><h3>Payment Types</h3></th>
				</tr>
				<tr class="odd">
					<td align="left" class="stat-left-txt"><a href="${contextPath}admin/payment">Total Payment Types</a></td>
					<td align="right" class="stat-right-txt"><a href="${contextPath}admin/payment">${totalPaymentTypes }</a></td>
				</tr>
				<tr class="even">
					<td align="left" class="stat-left-txt"><a href="${contextPath}admin/payment?status=1">Active Payment Types</a></td>
					<td align="right" class="stat-right-txt"><a href="${contextPath}admin/payment?status=1">${activePaymentTypes}</a></td>
				</tr>
				<tr class="odd">
					<td align="left" class="stat-left-txt left-round"><a href="${contextPath}admin/payment?status=0">Inactive Payment Types</a></td>
					<td align="right" class="stat-right-txt right-round"><a href="${contextPath}admin/payment?status=0">${inactivePaymentTypes}</a></td>
				</tr>

			</table>
		</div>
		<!-- Statistics End -->

		<div class="clr"></div>

	</div>
</div>
