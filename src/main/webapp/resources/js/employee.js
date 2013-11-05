var Views = { };
var accounts;
var AllAccView;
var AccView;


var Account = Backbone.Model.extend({















    url:function() {
        return 'employee/info';
    }


});


var creationModel =  {
    header: "",
    button: "",
    message: ""
};
var header = [
    "Warning",
    "Error"
];
var accErrors = [
	"Destination account doesn't exist !",							//1
	"Destination account is not active !",							//2
    "Please enter the sum of money you want to transfer !",			//3
    "The sum of this transfer exceeds the sum on your account !"	//4
];
var messages = [
    "Are you sure you want to log out from the application?",
];

var totalCount=0;
var totalPages;
var index = 1;
var paging = 10;
								var search=false;/////////////////////////////////////////////////////////////

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


/*	THE BEGINNING OF GREAT FUNCTION	*/
$(function () {
    updatePaging();
    Backbone.emulateJSON = false;
    accounts = new AccList();
    var Controller = Backbone.Router.extend({
       routes: {
           "": "start",
           "": "start",
           "info:id": "info"
       },
        start: function() {
          closeModal();
          closeDetailedInfo();
        },
        info: function() {
            closeDetailedInfo();
            if(Views.detailedInfo!=null) {	//????????????????????
                creationModel = {
                    header: "info New",
                    button: "info",
                    message: "creating new"
                };
            Views.detailedInfo.render(creationModel);
            }
        }
    });
    
    var controller = new Controller();
    
    Backbone.history.start();
    
    var Start = Backbone.View.extend({
        el: $(".content"),
        events: {
            "click a#info": "info",
            "click #next": "next",
            "click #previous": "previous",
            "click #first": "first",
            "click #last": "last"
        },
        info: function(e) {
            e.preventDefault();
            controller.navigate("info", true);
        },
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
      el: $("#template"),
        template: _.template($("#showinfotemplate").html()),
      events: {
          "click .btn-success#change_status_btn": "info",
          "click .btn-warning#refresh": "refresh",
          "click .btn-danger#cancel": "cancel"
      },
       cancel: function(e) {
           e.preventDefault();
           controller.navigate("", true);
       },
        refresh: function(e) {
            e.preventDefault();
        },
        info: function(e) {
            e.preventDefault();
            
               /*var bankTransaction = new BankTransaction({
            	   	destinationAccount:$('#destaccount').val(),
            	   	amountMoney:$('#amount').val()
                    });
               console.log(bankTransaction);
               bankTransaction.save();*/
            /*sourceAccount
            destinationAccount
            transactionDate
            amountMoney*/
               //toastr.success("Smth is happenning right now...") ;
               buttonClick();
               controller.navigate("", true);
        },
        render: function(model) {
            $(this.el).html(this.template(model));
        }
    });
    /*     end DETAILED ACCOUNT INFORMATION ends     */
	
	/*
	1-userName
	2-accountNumber
	3-amountMoney
	4-status
	5-list of last 5 transactions	---	Source account
									Destination account
									Date/time of the transaction
									Amount of money transferred
	Promote (new -> active)
		Block (active -> blocked)
			Unblock (blocked -> active
	*/
	
	
	
	/*var AccEditor = Backbone.View.extend({
      el: $("#template"),
        template: _.template($("#showinfotemplate").html()),
      events: {
          "click .btn-danger#cancel": "cancel",
          "click .btn-warning#refresh": "refresh",
          "click .btn-success#change_status_btn": "info"
      },
       cancel: function(e) {
           e.preventDefault();
           controller.navigate("", true);
       },
        refresh: function(e) {
            e.preventDefault();
        },
        info: function(e) {
            e.preventDefault();
            if(validate()) {
               var account = new Account({
            	   	destinationAccount:$('#destaccount').val(),
            	   	amountMoney:$('#amount').val()
                    });
               console.log(account);
               account.save();
               //toastr.success("Transaction was successfully added!") ;
               buttonClick();
               controller.navigate("", true);
            }
        },
        render: function(model) {
            $(this.el).html(this.template(model));
        }
    });*/
	
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
/*	THE END OF THE GREAT FUNCTION	*/


function buttonClick() {
    accounts = new AccList();
    accView = new AccView();
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

    /*var pager = new Imtech.Pager();
    $(document).ready(function() {
        pager.paragraphsPerPage = 5; // set amount elements per page
        pager.pagingContainer = $('#content'); // set of main container
        pager.paragraphs = $('div.z', pager.pagingContainer); // set of required containers
        pager.showPage(1);
    });

    this.paragraphsPerPage = 3;
    this.currentPage = 1;
    this.pagingControlsContainer = '#pagingControls';
    this.pagingContainerPath = '#content';

    this.numPages = function() {
        var numPages = 0;
        if (this.paragraphs != null && this.paragraphsPerPage != null) {
            numPages = Math.ceil(this.paragraphs.length / this.paragraphsPerPage);
        }

        return numPages;
    };

    this.showPage = function(page) {
        this.currentPage = page;
        var html = '';

        this.paragraphs.slice((page-1) * this.paragraphsPerPage,
            ((page-1)*this.paragraphsPerPage) + this.paragraphsPerPage).each(function() {
            html += '<div>' + $(this).html() + '</div>';
        });

        $(this.pagingContainerPath).html(html);

        renderControls(this.pagingControlsContainer, this.currentPage, this.numPages());
    };

    var renderControls = function(container, currentPage, numPages) {
        var pagingControls = 'Page: <ul>';
        for (var i = 1; i <= numPages; i++) {
            if (i != currentPage) {
                pagingControls += '<li><a href="#" onclick="pager.showPage(' + i + '); return false;">' + i + '</a></li>';
            } else {
                pagingControls += '<li>' + i + '</li>';
            }
        }

        pagingControls += '</ul>';

        $(container).html(pagingControls);
    };*/


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

