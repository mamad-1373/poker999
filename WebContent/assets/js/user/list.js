"use strict";

$("#menu-user-list").addClass("active");

var app = new VueTable({
	el: '#app',
	data: {
		URL: "list.json?permit=1",
		connectingID: -1,
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
		}
	}
})
