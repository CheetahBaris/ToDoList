$(function(){

    const appendStuff = function(data){
        var stuffCode = '<a href="#" class="stuff-link" data-id="' +
            data.id + '">' + data.name+ '</a><br>';
        $('#stuff-list')
            .append('<div>' + stuffCode + '</div>');
    };

    //Loading books on load page
//    $.get('/books/', function(response)
//    {
//        for(i in response) {
//            appendBook(response[i]);
//        }
//    });

    //Show adding book form
    $('#show-add-stuff-form').click(function(){
        $('#stuff-form').css('display', 'flex');
    });

    //Closing adding book form
    $('#stuff-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Getting book
    $(document).on('click', '.stuff-link', function(){
        var link = $(this);
        var stuffId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/stuff/' + stuffId,
            success: function(response)
            {
                 var code = '<span style="float: left;"> type: ' + response.type + '</span><br>';
                link.parent().append(code);
            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Товар не найден!');
                }
            }
        });
        return false;
    });

    //Adding book
    $('#save-stuff').click(function()
    {
        var data = $('#stuff-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/stuff',
            data: data,
            success: function(response)
            {
                $('#stuff-form').css('display', 'none');
                var stuff = {};
                stuff.id = response;
                var dataArray = $('#stuff-form form').serializeArray();
                for(i in dataArray) {
                    stuff[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendStuff(stuff);
            }
        });
        return false;
    });
});