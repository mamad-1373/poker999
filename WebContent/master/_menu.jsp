<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<aside>
			<nav class="navbar">
				<%--
				<ul class="navbar-nav">
					<li class="nav-item">
						<a id="menu-dashboard" class="nav-link" href="${URL_CONTEXT}/master/">
							<i class="nav-link-icon fa fa-home"></i>
							<span class="nav-link-title">Dashboard</span>
						</a>
					</li>
				</ul> --%>
				<p class="nav-section-title">USER MANAGEMENT</p>
				<ul class="navbar-nav">
					<li class="nav-item">
						<a id="menu-user-list" class="nav-link" href="${URL_CONTEXT}/master/user/list">
							<i class="nav-link-icon fa fa-user"></i>
							<span class="nav-link-title">User List</span>
						</a>
					</li>
					<li class="nav-item">
						<a id="menu-user-search" class="nav-link" href="${URL_CONTEXT}/master/user/search">
							<i class="nav-link-icon fa fa-search"></i>
							<span class="nav-link-title">Search User</span>
						</a>
					</li>
					<li class="nav-item">
						<a id="menu-user-blocked" class="nav-link" href="${URL_CONTEXT}/master/user/blocked">
							<i class="nav-link-icon fa fa-user-times"></i>
							<span class="nav-link-title">Blocked User</span>
						</a>
					</li>
				</ul>
				<p class="nav-section-title">SYSTEM</p>
				<ul class="navbar-nav">
					<li class="nav-item">
						<a id="menu-system-commission" class="nav-link" href="#">
							<i class="nav-link-icon fa fa-info-circle"></i>
							<span class="nav-link-title">Commission</span>
						</a>
					</li>
					<li class="nav-item">
						<a id="menu-system-calculation" class="nav-link" href="#">
							<i class="nav-link-icon fa fa-plus-circle"></i>
							<span class="nav-link-title">Calculation</span>
						</a>
					</li>
				</ul>
				<p class="nav-section-title">GAME</p>
				<ul class="navbar-nav">
					<li class="nav-item">
						<a id="menu-game-bots" class="nav-link" href="#">
							<i class="nav-link-icon fa fa-cubes"></i>
							<span class="nav-link-title">Manage Bots</span>
						</a>
					</li>
					<li class="nav-item">
						<a id="menu-game-log" class="nav-link" href="#">
							<i class="nav-link-icon fa fa-history"></i>
							<span class="nav-link-title">Game Log</span>
						</a>
					</li>
				</ul>
			</nav>
		</aside>