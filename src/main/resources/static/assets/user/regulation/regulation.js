
(function () {
    $.ajax({
        type: 'GET',
        url: URL + `api/regulation`,
        dataType: 'json',
        success: function (result) {
            document.getElementById('regulation').innerHTML = result.content;
        },
        error: function (error) {
            console.log(error)
        }
    })
})()