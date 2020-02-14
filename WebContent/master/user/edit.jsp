<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!doctype html>
<html lang="ko">

<head>
	<title>${user.loginID} | Poker 999</title>
	<meta name="description" content="" />
	<jsp:include page="/_head.jsp" />
</head>

<body>
	<jsp:include page="/_header.jsp" />
	<div class="div-root">
		<jsp:include page="/master/_menu.jsp" />
		<section>
			<div class="section-header d-flex align-items-center">
				<div class="section-title">User List</div>
			</div>
			<div class="div-panel">
				<div class="div-panel-heading">
					<i class="fa fa-user"></i>
					${user.loginID}'s details
				</div>
				<div class="div-panel-body">
					<form method="POST">
						<div class="table-container">
							<div>
								<table class="table table-bordered table-details">
									<colgroup>
										<col style="width: 20%;" />
										<col style="width: 30%;" />
										<col style="width: 20%;" />
										<col />
									</colgroup>
									<tbody>
										<tr>
											<th>No</th>
											<td colspan="3">${user.uid}</td>
										</tr>
										<tr>
											<th class="align-middle">User ID&emsp;<wbr/><span class="text-danger">* Read Only</span></th>
											<td><input type="text" class="form-control" readonly="readonly" value="${user.loginID}" /></td>
											<th class="align-middle">Password</th>
											<td><input type="text" class="form-control" name="password" required="required" value="${user.password}" /></td>
										</tr>
										<tr>
											<th class="align-middle">tel</th>
											<td><input type="text" class="form-control" name="tel" required="required" value="${user.tel}" /></td>
											<th class="align-middle">Registration date</th>
											<td class="align-middle">
												<fmt:formatDate value="${user.regdate}" pattern="yyyy-MM-dd HH:mm" />
											</td>
										</tr>
										<tr>
											<th>Status</th>
											<td>
												<div class="custom-control custom-radio custom-control-inline">
													<input id="radio-status-allow" class="custom-control-input" type="radio" name="permit" required="required" value="1"<c:if test="${user.permit==1}"> checked="checked"</c:if>>
													<label class="custom-control-label" for="radio-status-allow">Allow</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<input id="radio-status-deny" class="custom-control-input" type="radio" name="permit" required="required" value="2"<c:if test="${user.permit==2}"> checked="checked"</c:if>>
													<label class="custom-control-label" for="radio-status-deny">Block</label>
												</div>
											</td>
											
										</tr>
										<tr>
											<th class="align-middle">Money</th>
											<td class="pb-1" colspan="3">
												<div id="app" class="d-flex flex-wrap align-items-center" v-cloak>
													<div class="mr-3 mb-2">
														<input type="text" class="form-control" readonly="readonly" v-model="money"/>
													</div>
													<div class="d-flex mr-3 mb-2">
														<input type="number" class="form-control mr-2" min="0" v-model.number="amount" @keypress="keypress" style="width: 90px;"/>
														<button v-if="!loading" type="button" class="btn btn-primary mr-2" @click="confirm(true)"><i class="fa fa-plus"></i> Deposit</button>
														<button v-if="!loading" type="button" class="btn btn-danger" @click="confirm(false)"><i class="fa fa-minus"></i> Withdrawal</button>
													</div>
													<div v-if="loading" class="spinner mb-2 mr-2"></div>
													<div v-if="msg" class="text-danger mb-2">{{ msg }}</div>
													<div class="modal fade" id="modal-confirm-charge" tabindex="-1">
														<div class="modal-dialog">
															<div class="modal-content">
																<div class="modal-header">
																	<h4 class="modal-title"></h4>
																	<button type="button" class="close" data-dismiss="modal">&times;</button>
																</div>
																<div class="modal-body">
																	{{ isCharge?"Deposit":"Withdrawal" }} {{ amount }} with ${user.loginID}. OK?
																</div>
																<div class="modal-footer">
																	<button v-if="isCharge" type="button" class="btn btn-primary" autofocus="autofocus" @click="request">Deposit</button>
																	<button v-else type="button" class="btn btn-danger" autofocus="autofocus" @click="request">Withdrawal</button>
																	<button type="button" class="btn btn-success" data-dismiss="modal">Cancel</button>
																</div>
															</div>
														</div>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<th>Memo</th>
											<td colspan="3">
												<textarea class="form-control" name="memo" style="min-height: 64px;">${user.memo}</textarea>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
						<div>
							<c:if test="${msg!=null}">
								<p class="text-danger">${msg}</p>
							</c:if>
							<button type="submit" class="btn btn-info"><i class="fa fa-save"></i>&ensp;Save</button>&ensp;
							<a href="Search" class="btn btn-success"><i class="fa fa-arrow-circle-left"></i>&ensp;Back</a>
						</div>
					</form>
					<br/>
					<ul class="nav nav-tabs" role="tablist">
						<li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#tab-history-money">User Money</a></li>
						<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tab-history-game">Game status</a></li>
						<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tab-history-deposit">Deposit</a></li>
						<li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tab-history-withdrawal">Withdrawal</a></li>
					</ul>
					<div class="tab-content">
						<div id="tab-history-money" class="tab-pane">
							<div :class="['table-container data-loading',loading&&'loading']">
								<div>
									<table class="table table-striped table-hover table-bordered table-sm text-center">
										<thead>
											<tr>
												<th>#</th>
												<th :class="['th-order',orderBy==='loginip'&&(orderByDesc?'desc':'asc')]" @click="sort('loginip')">Time</th>
												<th :class="['th-order',orderBy==='sessionid'&&(orderByDesc?'desc':'asc')]" @click="sort('sessionid')">Amount</th>
												<th :class="['th-order',orderBy==='logintime'&&(orderByDesc?'desc':'asc')]" @click="sort('logintime')">Type</th>
												<th :class="['th-order',orderBy==='logouttime'&&(orderByDesc?'desc':'asc')]" @click="sort('logouttime')">Memo</th>
											</tr>
										</thead>
										<tbody>
											<tr v-for="item in list">
												<td>{{ item.no }}</td>
												<td>{{ item.loginip }}</td>
												<td>{{ item.sessionid }}</td>
												<td>{{ item.logintime | YYYYMMDD_HHMM }}</td>
												<td>{{ item.logouttime | YYYYMMDD_HHMM }}</td>
											</tr>
											<tr v-if="!loading&&msg" class="tr-msg">
												<td colspan="5">{{msg}}</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="clearfix">
								<page-btn-group :page-count="pageCount" :page-now="pageNow" :page-array="pageArray" :go-page="goPage"></page-btn-group>
								<p v-if="loading">Loading...</p>
								<p v-else-if="countShow>0">Showing {{offset+1}} to {{offset+countShow}} of {{count}}</p>
							</div>
						</div>
						<div id="tab-history-game" class="tab-pane active" v-cloak="v-cloak">
							<div :class="['table-container data-loading',loading&&'loading']">
								<div>
									<table class="table table-striped table-hover table-bordered table-sm text-center">
										<thead>
											<tr>
												<th>#</th>
												<th :class="['th-order',orderBy==='loginip'&&(orderByDesc?'desc':'asc')]" @click="sort('loginip')">IP Address</th>
												<th :class="['th-order',orderBy==='sessionid'&&(orderByDesc?'desc':'asc')]" @click="sort('sessionid')">Session ID</th>
												<th :class="['th-order',orderBy==='logintime'&&(orderByDesc?'desc':'asc')]" @click="sort('logintime')">Connected</th>
												<th :class="['th-order',orderBy==='logouttime'&&(orderByDesc?'desc':'asc')]" @click="sort('logouttime')">Disconnected</th>
											</tr>
										</thead>
										<tbody>
											<tr v-for="item in list">
												<td>{{ item.no }}</td>
												<td>{{ item.loginip }}</td>
												<td>{{ item.sessionid }}</td>
												<td>{{ item.logintime | YYYYMMDD_HHMM }}</td>
												<td>{{ item.logouttime | YYYYMMDD_HHMM }}</td>
											</tr>
											<tr v-if="!loading&&msg" class="tr-msg">
												<td colspan="5">{{msg}}</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="clearfix">
								<page-btn-group :page-count="pageCount" :page-now="pageNow" :page-array="pageArray" :go-page="goPage"></page-btn-group>
								<p v-if="loading">Loading...</p>
								<p v-else-if="countShow>0">Showing {{offset+1}} to {{offset+countShow}} of {{count}}</p>
							</div>
						</div>
						<div id="tab-history-deposit" class="tab-pane" v-cloak="v-cloak">
							<div :class="['table-container data-loading',loading&&'loading']">
								<div>
									<table class="table table-striped table-hover table-bordered table-sm text-center">
										<thead>
											<tr>
												<th>#</th>
												<th :class="['th-order',orderBy==='bankinfo'&&(orderByDesc?'desc':'asc')]" @click="sort('bankinfo')">Account</th>
												<th :class="['th-order',orderBy==='tel'&&(orderByDesc?'desc':'asc')]" @click="sort('tel')">Tel</th>
												<th :class="['th-order',orderBy==='money'&&(orderByDesc?'desc':'asc')]" @click="sort('money')">Amount</th>
												<th :class="['th-order',orderBy==='time'&&(orderByDesc?'desc':'asc')]" @click="sort('time')">Registered Time</th>
												<th :class="['th-order',orderBy==='type'&&(orderByDesc?'desc':'asc')]" @click="sort('type')">Deposit Type</th>
												<th :class="['th-order',orderBy==='status'&&(orderByDesc?'desc':'asc')]" @click="sort('status')">Status</th>
												<th :class="['th-order',orderBy==='memo'&&(orderByDesc?'desc':'asc')]" @click="sort('memo')">Memo</th>
											</tr>
										</thead>
										<tbody>
											<tr v-for="item in list">
												<td>{{ item.no }}</td>
												<td>{{ item.bankinfo }}</td>
												<td>{{ item.tel }}</td>
												<td>{{ item.money | filterComma }}</td>
												<td>{{ item.time | YYYYMMDD_HHMM }}</td>
												<td>{{ item.type===0?'Self':'Admin' }}</td>
												<td>{{ item.status===0?'Request':item.status===1?'Finish':'Cancel' }}</td>
												<td>{{ item.memo }}</td>
											</tr>
											<tr v-if="!loading&&msg" class="tr-msg">
												<td colspan="8">{{msg}}</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="clearfix">
								<page-btn-group :page-count="pageCount" :page-now="pageNow" :page-array="pageArray" :go-page="goPage"></page-btn-group>
								<p v-if="loading">Loading...</p>
								<p v-else-if="countShow>0">Showing {{offset+1}} to {{offset+countShow}} of {{count}}</p>
							</div>
						</div>
						<div id="tab-history-withdrawal" class="tab-pane" v-cloak="v-cloak">
							<div :class="['table-container data-loading',loading&&'loading']">
								<div>
									<table class="table table-striped table-hover table-bordered table-sm text-center">
										<thead>
											<tr>
												<th>#</th>
												<th :class="['th-order',orderBy==='bankinfo'&&(orderByDesc?'desc':'asc')]" @click="sort('bankinfo')">Account</th>
												<th :class="['th-order',orderBy==='tel'&&(orderByDesc?'desc':'asc')]" @click="sort('tel')">Tel</th>
												<th :class="['th-order',orderBy==='money'&&(orderByDesc?'desc':'asc')]" @click="sort('money')">Amount</th>
												<th :class="['th-order',orderBy==='time'&&(orderByDesc?'desc':'asc')]" @click="sort('time')">Registered Time</th>
												<th :class="['th-order',orderBy==='type'&&(orderByDesc?'desc':'asc')]" @click="sort('type')">Withdrawal Type</th>
												<th :class="['th-order',orderBy==='status'&&(orderByDesc?'desc':'asc')]" @click="sort('status')">Status</th>
												<th :class="['th-order',orderBy==='memo'&&(orderByDesc?'desc':'asc')]" @click="sort('memo')">Memo</th>
											</tr>
										</thead>
										<tbody>
											<tr v-for="item in list">
												<td>{{ item.no }}</td>
												<td>{{ item.bankinfo }}</td>
												<td>{{ item.tel }}</td>
												<td>{{ item.money | filterComma }}</td>
												<td>{{ item.time | YYYYMMDD_HHMM }}</td>
												<td>{{ item.type===0?'Self':'Admin' }}</td>
												<td>{{ item.status===0?'Request':item.status===1?'Finish':'Cancel' }}</td>
												<td>{{ item.memo }}</td>
											</tr>
											<tr v-if="!loading&&msg" class="tr-msg">
												<td colspan="8">{{msg}}</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="clearfix">
								<page-btn-group :page-count="pageCount" :page-now="pageNow" :page-array="pageArray" :go-page="goPage"></page-btn-group>
								<p v-if="loading">Loading...</p>
								<p v-else-if="countShow>0">Showing {{offset+1}} to {{offset+countShow}} of {{count}}</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
	<jsp:include page="/_modal.jsp" />
</body>

<jsp:include page="/_script.jsp" />
<script type="text/javascript">
	const USER_UID=${user.uid};
	var userMoney=${user.money};
</script>
<script type="text/javascript" src="${URL_CONTEXT}/assets/js/_vue.table.js"></script>
<script type="text/javascript" src="${URL_CONTEXT}/assets/js/user/edit.js"></script>

</html>