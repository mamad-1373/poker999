"use strict";

$("#menu-user-blocked").addClass("active");

var app = new VueTable({
	el: '#app',
	data: {
		URL: "list.json?permit=3",
		connectingID: -1,
		loginID: "",
		tel: ""
	},
	methods:{
		changePermit: function(user,val){
			var vm=this;
			vm.connectingID = user.id;
			var param = {
				id: user.id,
				p: val
			}
			$.getJSON("permit", param, (data, status, xhr)=>{
				if(data.success){
					user.permit=val;
				}else{
					printNotification(data.msg);
				}
				this.refreshSearch();
				vm.connectingID=-1;
			}).fail((xhr, status, err)=>{
				printNotification("Server connection failed. "+err);
				vm.connectingID=-1;
			});
		},
		refreshSearch: function(){
			var vm = this;
			vm.refresh({
				loginid: vm.loginID,
				tel: vm.tel
			},function(data){
				vm.loginID=data.loginid || "";
				vm.tel=data.tel || "";
			});
		}
	}
})
