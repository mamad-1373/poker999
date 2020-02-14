<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="en-US">

<head>
	<title>Users List | Poker 999</title>
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
			<div  id="app" class="div-panel" v-cloak>
				<div class="div-panel-heading">
					<i class="fa fa-user"></i>
					User List&ensp;({{count|filterComma}})
					<div class="float-right">
						<a href="search" class="btn btn-pure fa fa-search" alt="Search"></a>
						<a href="blocked" class="btn btn-pure fa fa-user-times" alt="Blocked"></a>
						<button class="btn btn-pure fa fa-sync-alt" type="button" @click="refresh" alt="Refresh"></button>
					</div>
				</div>
				<div class="div-panel-body">
					<ul class="nav nav-query">
						<li class="nav-item div-select-limit">
							<label class="mb-0 mr-2">Rows</label>
							<select class="form-control form-control-sm" v-model="limit" @change="refresh">
								<option value="5">5</option>
								<option value="10">10</option>
								<option value="20">20</option>
								<option value="50">50</option>
							</select>
						</li>
					</ul>
					<div :class="['table-container data-loading',loading&&'loading']">
						<div>
							<table class="table table-striped table-hover table-bordered th-primary table-sm text-center">
								<thead>
									<tr>
										<th>#</th>
										<th :class="['th-order',orderBy==='loginid'&&(orderByDesc?'desc':'asc')]" @click="sort('loginid')">User ID</th>
										<th :class="['th-order',orderBy==='tel'&&(orderByDesc?'desc':'asc')]" @click="sort('tel')">Telephone Number</th>
										<th :class="['th-order',orderBy==='money'&&(orderByDesc?'desc':'asc')]" @click="sort('money')">Money</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="item in list">
										<td>{{ item.no }}</td>
										<td><a :href="'edit?uid='+item.uid">{{ item.loginid }}</a></td>
										<td>{{ item.tel }}</td>
										<td>{{ item.money }}</td>
										<td>
											<div v-if="item.id===connectingID" class="spinner m-auto"></div>
											<a v-else-if="item.permit===1" class="text-danger" href="#" @click.prevent="changePermit(item,2)">
												<i class="fa fa-ban"></i>Block
											</a>
											<span v-else>
												<a class="text-success" href="#" @click.prevent="changePermit(item,1)">
													<i class="fa fa-check"></i>Unblock
												</a>&nbsp;&nbsp;&nbsp;
												<a class="text-danger" href="#" @click.prevent="delete(item.id)">
													<i class="fa fa-times"></i>Delete
												</a>
											</span>
										</td>
									</tr>
									<tr v-if="!loading&&msg" class="tr-msg">
										<td colspan="12">{{msg}}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="clearfix">
						<page-btn-group :page-count="pageCount" :page-now="pageNow" :page-array="pageArray" :go-page="goPage"></page-btn-group>
						<p v-if="loading">Loading...</p>
						<p v-else-if="countShow>0">Showing {{offset+1}} to {{offset+countShow}} of {{count}} users</p>
					</div>
				</div>
			</div>
		</section>
	</div>
</body>

<jsp:include page="/_script.jsp" />
<script type="text/javascript" src="${URL_CONTEXT}/assets/js/_vue.table.js"></script>
<script type="text/javascript" src="${URL_CONTEXT}/assets/js/user/list.js"></script>

</html>