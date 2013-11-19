var Views = { };
var accounts;
var AllAccView;
var AccView;

var Account = Backbone.Model.extend({
	urlRoot : '/bank/employee/accounts'			
});

var header = [
    "Warning",
];

var messages = [
    "Are you sure you want to log out from the application?",
];

var totalCount=0;
var totalPages;
var index = 1;
var paging = 10;

var AccList = Backbone.Collection.extend({
    baseUrl: 'employee/accounts',
    initialize: function() {
        _.bindAll(this, 'url');
        this.index = index;
    },
    url: function() {
        return this.baseUrl + '?' + $.param({
            index: this.index
        });
    }
});


/*        THE BEGINNING OF GREAT FUNCTION        */
$(function () {
    updatePaging();
    Backbone.emulateJSON = false;
    accounts = new AccList();
    //                            nameView : new NameView(),
    
    
    var MyRouter = Backbone.Router.extend({
       routes: {
    	   "/accounts/:id": 'informMe',
    	   "": 'start'
       },
        start: function() {
          closeModal();
          closeDetailedInfo();
        },
        informMe: function(id) {
        	this.id = id;
        	Views.detailedInfo.render(id);
        }
    });
    
    var myRouter = new MyRouter();
    
    Backbone.history.start({pushState: true, root: "/bank/employee"});
    
    var Start = Backbone.View.extend({
        el: $(".content"),
        events: {
            //"click #info": "info",
            "click #next": "next",
            "click #previous": "previous",
            "click #first": "first",
            "click #last": "last"
        },
        /*info: function(e) {
            e.preventDefault();
            myRouter.navigate("/accounts/:id", {trigger: true} );
        },*/
        next: function(e) {
            e.preventDefault();
            index++;
            buttonClick();
        },
        previous: function(e) {
            e.preventDefault();
            index--;
            buttonClick();
        },
        first: function(e) {
            e.preventDefault();
            index=1;
            buttonClick();
        },
        last: function(e) {
            e.preventDefault();
            index=totalPages;
            buttonClick();
        }
    });
    
    var start = new Start();
        
    AccView = Backbone.View.extend({
        tagName: 'tr',
        template: _.template($("#rowacc").html()),
        events: {
            "click #info": "clicked"
        },
        clicked: function(e){
            e.preventDefault();
            myRouter.navigate('/accounts/' + this.model.get("accountId"), {trigger:true});
        },
        initialize: function(){
            _.bind(this.render, this);
        },
        render: function() {
            var element = this.template(this.model.toJSON());
            console.log(this.model.toJSON());
            $(this.el).html(element);
            return this;
        }
    });

        
        /*     DETAILED ACCOUNT INFORMATION     */
	var DetailedInfo = Backbone.View.extend({
		//baseUrl: 'employee/accounts/:id',
		baseUrl: 'employee/accounts/',
    	el: $("#employeeTemplate"),
        template: _.template($("#showinfotemplate").html()),
        events: {
        	"click .btn-success#change_status_btn": "accept",
        	"click .btn-danger#cancel": "cancel"
        },
        cancel: function(e) {
        	e.preventDefault();
        	toastr.warning("Closing with no changes") ;
        	myRouter.navigate("", {trigger: true} );
        },
        accept: function(e) {
        	e.preventDefault();
        	toastr.success("Smth is happenning right now...") ;
        	myRouter.navigate("", {trigger: true} );
        },
        render: function(id) {
        	var detailedAccount = new Account ( {id: id} );
        	var that = this;
        	detailedAccount.fetch({
        		success:function(){
        			var element = that.template(detailedAccount.toJSON());
        			console.log(detailedAccount.toJSON());
        			$(that.el).html(element);
        		}
        	});
        }
	});
    /*     end DETAILED ACCOUNT INFORMATION ends     */


        
    AllAccView = Backbone.View.extend({
        el : $('#accListFrame'),
        initialize : function() {
            _.bindAll(this, 'addOne', 'addAll', 'render');
            accounts.bind('reset', this.addAll);
            accounts.bind('add', this.addOne);
            accounts.fetch();
        },
        addOne : function(account) {
            var view = new AccView({
                model : account
            });
            this.$('#tableAccounts').append(view.render().el);
        },
        addAll : function() {
            accounts.each(this.addOne);
        }
    });        
        
    Views = {
            detailedInfo: new DetailedInfo(),
            allAccView: new AllAccView()
    };
        
    // handlers for elements which are not in .content
    $("#buttonLogout").click(function (e) {
        e.preventDefault();
        showModal(0, 0);
        $('.modal-footer .btn-primary').one('click', function(event) {
            event.preventDefault();
            location.href="j_spring_security_logout";
        });
    });
    $(".modal-footer .btn").click(function (e) {
        e.preventDefault();
        closeModal();
    });

});
/*        THE END OF THE GREAT FUNCTION        */


function buttonClick() {
    accounts = new AccList();
    accView = new AccView();
                    //nameView = new NameView();
    updatePaging();
    Views.allAccView = new AllAccView();
    setTimeout(scrollDown, 100);
}

function updatePaging() {
    $.ajax({
            type: "GET",
            url: "employee/getAccCount",
            async: false,
            success:function(count) {
                totalCount = count;
                console.log(totalCount);
            }
        }
    ).responseText;
    totalPages = Math.ceil(totalCount/paging);
    console.log(totalPages,totalCount, paging);
    $("#previous").attr("disabled", false);
    $("#next").attr("disabled", false);
    $("#first").attr("disabled", false);
    $("#last").attr("disabled", false);
    if(totalPages===0) {
            $("#previous").attr("disabled", true);
            $("#next").attr("disabled", true);
        $("#first").attr("disabled", true);
        $("#last").attr("disabled", true);
        totalPages = "NONE";
    }
    if(totalPages===1) {
        $("#first").attr("disabled", true);
        $("#last").attr("disabled", true);
        $("#previous").attr("disabled", true);
            $("#next").attr("disabled", true);
    }
    if(index===1) {
        $("#previous").attr("disabled", true);
        $("#first").attr("disabled", true);
    }
    if(index===totalPages) {
        $("#next").attr("disabled", true);
        $("#last").attr("disabled", true);
    }
    $("#pageIndex").html(index);
    $("#totalPages").html(totalPages);
    $("#accListFrame #tableAccounts tbody").html("");
}


function scrollDown() {
    $('html, body').animate({scrollTop: $("#foot").offset().top}, 1);
}


//modals, errors, warnings

function closeModal() {
    $("#modal").fadeOut();
}


function showModal(head, message, id) {
    if (head == 0) {
        $(".modal-footer .btn-primary").html("Yes");
        $(".modal-footer .btn#no").removeClass("hide");
    } else {
        $(".modal-footer .btn-primary").html("Ok");
        $(".modal-footer .btn#no").addClass("hide");
    }
    $(".modal-header h4").html(header[head]);
    $(".modal-body p").html(messages[message]);
    $("#modal").fadeIn();
}


function closeDetailedInfo() {
    $(".container#createshowinfo").fadeOut();
}