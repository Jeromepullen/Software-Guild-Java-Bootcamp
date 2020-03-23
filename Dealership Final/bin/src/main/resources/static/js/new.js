var newCarContainer = $("#newCarContainer");

function render(vehicles) {
    newCarContainer.empty();
    var html = "";
    vehicles.forEach(function (car) {
        console.log(car);
        html += `<div class="row car">
                    <div class ="col-4">
                        <a href="/inventory/${car.vehicle.vehicleId}">
                            <img src="${car.vehicle.url}" class="card-img-top featuredCars mx-auto img-fluid" alt="${car.vehicle.vin}">
                        </a>
                    </div>
                    <div class="col-4"> 
                        <h5 class="display-5">
                            ${car.vehicle.year} ${car.make.name} ${car.model.name}<br>
                        </h5>
                        <h6>
                            Mileage: ${car.vehicle.mileage} miles <br>
                            Body Style: ${car.body.name} <br>
                            Interior: ${car.interiorColor.name} <br>
                            Exterior: ${car.exteriorColor.name}
                        </h6>
                    </div>
                    <div class ="col-4">
                        <h5 class="display-5">MSRP: $${car.vehicle.msrp}</h5>
                        <h5 class="display-5">Our Price: $${car.vehicle.price}</h5>
                    </div>
                </div>`;
    });
    newCarContainer.append(html);
}

$("#searchForm").submit(function (evt) {
    evt.preventDefault();
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/inventory/new',
        data: JSON.stringify({
            make: $("#searchBar").val(),
            model: $("#searchBar").val(),
            minYear: $("#minYearInput").val(),
            maxYear: $("#maxYearInput").val(),
            minPrice: $("#minPriceInput").val(),
            maxPrice: $("#maxPriceInput").val()
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        //vehicle(modelId, vin, isNew, mileage, year, "
        //bodyStyleId, isAutomatic, msrp, price, exteriorColorId, interiorColorId, description, isFeatured, url, isPurchased)
        success: function (data) {
            $('#errorMessages').empty();
            render(data);
        },
        error: function () {
            $('#errorMessages')
                .append($('<li>')
                    .attr({ class: 'list-group-item list-group-item-danger' })
                    .text('Error searching.  Please try again later.'));
        }
    });


    return false;
});

