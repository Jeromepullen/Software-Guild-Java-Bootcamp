var createCarContainer = $("#createCarContainer");

var makeListener = document.getElementById("makeInput");
makeListener.addEventListener("change", function() {
    $.ajax({
        type: 'PUT',
        url: 'http://localhost:8080/admin/addVehicle',
        data: JSON.stringify({
            name: $('#makeInput').val()
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            console.log(data);
            $('#SELECT_LIST').empty();
            for (var i = 0; i <= data.length; i++) {
                $('#SELECT_LIST').append('<option value=' + data[i].modelId + 
                                        '>'+data[i].name+ '</option>');
            }
        },
        error: function () {
            alert("There was an error");
        }
    });
});

