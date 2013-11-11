var Views = { };
var bankTransactions;
var AllTransView;
var TransView;


var BankTransaction = Backbone.Model.extend({















    url:function() {
        return 'client/create';
    }


});


var creationModel = {
    header: "",
    button: "",
    message: ""
};
var header = [
    "Warning",
    "Error"
];
var transErrors = [
        "Destination account doesn't exist !", //1
        "Destination account is not active !", //2
    "Please enter the sum of money you want to transfer !", //3
    "The sum of this transfer exceeds the sum on your account !" //4
];
var messages = [
    "Are you sure you want to log out from the application?",
];



var totalCount=0;
var totalPages;

var index = 1;
var paging = 10;


var TransList = Backbone.Collection.extend({
    baseUrl: 'client/transactions',
    initialize: function() {
        _.bindAll(this, 'url');
        this.index = index;
    },
    url: function() {
        return this.baseUrl + '?' + $.param({
            index: this.index,
        });
    }
});


/* THE BEGINNING OF GREAT FUNCTION */
$(function () {
    updatePaging();
    Backbone.emulateJSON = false;
    bankTransactions = new TransList();
    updateName();
    updateNumberOfAcc();
    
    var Controller = Backbone.Router.extend({
       routes: {
           "": "start",
           "create": "create"
       },
        start: function() {
          closeModal();
          closeTransEditor();
        },
        create: function() {
            closeTransEditor();
            if(Views.transEditor!=null) {
                creationModel = {
                    header: "Create New",
                    button: "Create",
                    message: "creating new"
                };
            Views.transEditor.render(creationModel);
            }
        }
    });
    
    var controller = new Controller();
    
    Backbone.history.start();
    
    var Start = Backbone.View.extend({
        el: $(".content"),
        events: {
            "click a#create": "create",
            "click #next": "next",
            "click #previous": "previous",
            "click #first": "first",
            "click #last": "last"
        },
        create: function(e) {
            e.preventDefault();
            controller.navigate("create", true);
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
        
    TransView = Backbone.View.extend({
        tagName: 'tr',
        template: _.template($("#rowtrans").html()),
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
    
    
    var TransEditor = Backbone.View.extend({
      el: $("#template"),
        template: _.template($("#createNewTrTemplate").html()),
      events: {
          "click .btn-success#confirmbtn": "confirmNewTr",
          "click .btn-danger#cancel": "cancel"
      },
       cancel: function(e) {
           e.preventDefault();
           toastr.warning("Stopped creating a transaction") ;
           controller.navigate("", true);
       },
        confirmNewTr: function(e) {
            e.preventDefault();
            if (myValidation()) {
                    var myDate = new Date;
                    var bankTransaction = new BankTransaction({
                               destinationAccount:$('#destaccount').val(),
                               amountMoney:$('#amount').val(),
                               transactionDate:myDate.toLocaleString()
               });
               console.log(bankTransaction);
               bankTransaction.save();
               toastr.success("Transaction was successfully created!") ;
               buttonClick();
               controller.navigate("", true);
            }
        },
        render: function(model) {
            $(this.el).html(this.template(model));
        }
    });


        AllTransView = Backbone.View.extend({
        el : $('#transListFrame'),
        initialize : function() {
            _.bindAll(this, 'addOne', 'addAll', 'render');
            bankTransactions.bind('reset', this.addAll);
            bankTransactions.bind('add', this.addOne);
            bankTransactions.fetch();
        },
        addOne : function(bankTransaction) {
            var view = new TransView({
                model : bankTransaction
            });
            this.$('#tableTransactions').append(view.render().el);
        },
        addAll : function() {
            bankTransactions.each(this.addOne);
        }
    });
        
        
    Views = {
        transEditor: new TransEditor(),
        allTransView: new AllTransView()
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
/* THE END OF THE GREAT FUNCTION */


function buttonClick() {
    bankTransactions = new TransList();
    transView = new TransView();
    updatePaging();
    Views.allTransView = new AllTransView();
    setTimeout(scrollDown, 100);
}



function updatePaging() {
    $.ajax({
            type: "GET",
            url: "client/getTrCount",
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
    $("#transListFrame #tableTransactions tbody").html("");
    updateSum();
}

function updateName() {
    $.ajax({
            type: "GET",
            url: "client/getName",
            async: false,
            success:function(string) {
                    userName = string;
                console.log('userName is' + userName);
            }
        }).responseText;
    $("#formSpan").html(userName);
}
function updateSum() {
    $.ajax({
            type: "GET",
            url: "client/getSum",
            async: false,
            success:function(double) {
                    userSum = double;
                console.log('userSum is' + userSum);
            }
        }).responseText;
    $("#formSpan2").html(userSum);
}
function updateNumberOfAcc() {
    $.ajax({
            type: "GET",
            url: "client/getNumberOfAcc",
            async: false,
            success:function(string) {
                    userNumber = string;
                console.log('userNumber is' + userNumber);
            }
        }).responseText;
    $("#formSpan3").html(userNumber);
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


function closeTransEditor() {
    $(".container#createtrans").fadeOut();
}











// VALIDATION

function myValidation()
{
    if ($('#formAmount').validationEngine('validate'))
    {
            return true;
    }
}