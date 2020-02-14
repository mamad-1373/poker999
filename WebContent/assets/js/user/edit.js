"use strict";

$("#menu-user-search").addClass("active");

var app = new Vue({
	el: '#app',
	data: {
		loading: false,
		money: userMoney,
		amount: 0,
		isCharge: true,
		msg: false
	},
	methods: {
		keypress: function(e){
			var keyCode = e.which;
			if (keyCode < 48 || keyCode > 57)e.preventDefault();
		},
		confirm: function (isCharge) {
			this.isCharge=isCharge;
			if(this.amount>0){
				$("#modal-confirm-charge").modal();
			}else{
				modalAlert("Wrong input.");
			}
		},
		request: function(){
			var vm = this;
			vm.loading = true;
			var param = {
				userid: USER_UID,
				amount: this.amount
			}
			vm.msg=false;
			$.getJSON(this.isCharge?"charge":"discharge", param, function(data, status, xhr){
				if (data.success) {
					vm.money = data.money;
					vm.msg = data.msg;
				} else {
					vm.msg = data.msg;
				}
				vm.loading = false;
			}).fail((xhr, status, err) => {
				vm.msg = "Can not connect server. " + err;
				vm.loading = false;
			});
			$("#modal-confirm-charge").modal("hide");
		}
	}
});

var appHistoryMoney = new VueTable({
	el: '#tab-history-money',
	data: {
		URL: "history-money.json?userid="+USER_UID,
		orderBy: "time",
		orderByDesc: true
	},
	methods: {
	}
});

var appHistoryGame = new VueTable({
	el: '#tab-history-game',
	data: {
		URL: "history-game.json?userid="+USER_UID,
		orderBy: "time",
		orderByDesc: true
	},
	methods: {
	}
});

var appHistoryDeposit = new VueTable({
	el: '#tab-history-deposit',
	data: {
		URL: "history-deposit.json?userid="+USER_UID,
		orderBy: "time",
		orderByDesc: true
	},
	methods: {
	}
});

var appHistoryWithdrawal = new VueTable({
	el: '#tab-history-withdrawal',
	data: {
		URL: "history-withdrawal.json",
		orderBy: "time",
		orderByDesc: true
	},
	methods: {
	}
});

