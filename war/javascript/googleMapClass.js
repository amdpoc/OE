GoogleMapClass = function()
{
    this.destLatitude = 0;
    this.destLongitude = 0;
    this.initialLocation = null;
    this.initialDefaultLocation = new google.maps.LatLng(35.857534, -78.813111);
    this.address = null;
    this.destination = null;

    this.directionsService = null;
    this.directionsDisplay = null;

    this.map = null;
    this.marker2 = null;
    this.browserSupportFlag = false;
    this.initFlag = false;
    this.dirFlag = false;
    this.addressStatus = true;

    this.init();
};

GoogleMapClass.prototype.init = function()
{
    this.directionsService = new google.maps.DirectionsService();
    this.directionsDisplay = new google.maps.DirectionsRenderer();
    this.oModalDialog = modalDialog;
};

GoogleMapClass.prototype.resetMap = function() {
    this.destLatitude = 0;
    this.destLongitude = 0;
    this.addressStatus = true;
    this.destination = null;
    this.initFlag = false;
    this.dirFlag = false;
};

GoogleMapClass.prototype.setCurrentLocation = function() {
    var that = this;
    if (navigator.geolocation) {
        this.browserSupportFlag = true;
        navigator.geolocation.getCurrentPosition(function(position) {
            console.log(position);
            that.originLatitude = position.coords.latitude;
            that.originLongitude = position.coords.longitude;
            that.initialLocation = new google.maps.LatLng(that.originLatitude, that.originLongitude);
          
            that.buildGoogleDirections();
        }, function(results, status) {
             console.log(results);
             console.log(status);
            that.handleNoGeolocation(this.browserSupportFlag);
            that.buildGoogleDirections();
        }, {enableHighAccuracy:true,maximumAge:300000, timeout:3000}); // Request live position
        //  navigator.geolocation.getCurrentPosition(this.geolocationCallback,  this.handleNoGeolocation(this.browserSupportFlag),
        //{enableHighAccuracy:true,maximumAge:300000, timeout:30000});
        /*   navigator.geolocation.getCurrentPosition(function(position) {
         alert("back");
         that.initialLocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
         console.log("setCurrentLocation :: this.initialLocation = " + that.initialLocation);
         that.buildGoogleDirections();
         // map.setCenter(initialLocation);
         }, function() {
         that.handleNoGeolocation(that.browserSupportFlag);
         }); */
    } else {
        this.browserSupportFlag = false;
        this.handleNoGeolocation(this.browserSupportFlag);
        this.buildGoogleDirections();
    }

};


GoogleMapClass.prototype.getLatLongByAddress = function() {
    var geocoder = new google.maps.Geocoder();
    if (geocoder && this.address != '') {
        var that = this;
        geocoder.geocode({ 'address': this.address}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                that.destination = results[0].geometry.location;
                that.addressStatus = true;
                that.initMap();
            } else {
                that.addressStatus = false;
                that.handleError("A geocoding or directions request could not be successfully processed, yet the exact reason for the failure is not known. Error code: " + status); //google.maps.error4
            }
        });
    }
};
GoogleMapClass.prototype.loadAddress = function(address)
{
      $(".destinationAddress").html(address);
};


GoogleMapClass.prototype.loadGoogleMap = function(address, destLatitude, destLongitude) {
    //get address  + remove white spaces
    console.log("*** loadGoogleMap *** address = ",address,"destLatitude = ",destLatitude,"destLongitude = ",destLongitude);
	this.address = new String(address);
    this.address = this.address.replace(/^\s*/, "").replace(/\s*$/, "");
    this.loadAddress(address);
    this.destLatitude = destLatitude;
    this.destLongitude = destLongitude;
    //alert("this.address ->" + this.address);
    //alert("this.destLatitude ->" + this.destLatitude);
    //alert("this.destLongitude ->" + this.destLongitude);
    //   var that = this;
    /*  if (this.browserSupportFlag) {
     navigator.geolocation.getCurrentPosition(this.geolocationCallback, function(error) {
     that.handleError(error.code + ' ' + error.message);
     }, {enableHighAccuracy:true,maximumAge:600000});
     } else {
     if (!this.browserSupportFlag) {
     alert('Your device or browser software does not appear to support geolocation services');
     }*/
    if (this.destLongitude == 0 && this.destLatitude == 0) {
        this.getLatLongByAddress();
    } else {
        //if (!this.destination) {
            this.destination = new google.maps.LatLng(this.destLatitude, this.destLongitude);
            this.initMap();
        //}
        $("#loader").hide();
    }
};

GoogleMapClass.prototype.initMap = function() {
    console.log("initMap :::::::: this.addressStatus = ",this.addressStatus,"this.initFlag = ",this.initFlag);
	//if (this.addressStatus && !this.initFlag) {
	if (this.addressStatus) {

        if (this.map == null) {
            console.log("MAP IS NULL !!!!!!!!!!!!!!!!!!!!!!!!");
        	//var myOptions = { zoom: 12, mapTypeId: google.maps.MapTypeId.ROADMAP, center: this.destination };
            this.mapBounds = new google.maps.LatLngBounds();
            //this.directions = new google.maps.DirectionsRendererOptions({preserveViewport:true}); 
            var myOptions = {zoom: 12, mapTypeId: google.maps.MapTypeId.ROADMAP, center: this.destination };
            this.map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
            this.marker = new google.maps.Marker({ position: this.destination, map: this.map, title:"Destination" });
        } else {
            console.log("MAP IS NOT ***************** NULL");
            this.map.panTo(this.destination);
            this.map.setZoom(12);
            this.marker.setMap(this.map);
            this.marker.setPosition(this.destination);
            if (this.directionsDisplay != null) {
                this.directionsDisplay.setMap(null);
                if (this.marker2 != null) this.marker2.setVisible(false);
                //document.getElementById("directions").style.display = 'none';
            }
        }
        this.initFlag = true;
    } else {
        if (!this.addressStatus) {
            this.initFlag = false;
            this.loadedHandler(false, "Invalid address - can't display map");//google.maps.error2
        }
    }
    this.loadedHandler(true, "");
};
GoogleMapClass.prototype.getGoogleDirections = function() {
    //if (!this.dirFlag) {
        this.directionsDisplay.setMap(this.map);
        this.directionsDisplay.setPanel(document.getElementById("directions"));

        //    this.origin = new google.maps.LatLng(this.originLatitude, this.originLongitude);
        this.setCurrentLocation();
        // map.setCenter(initialLocation);
        /*    var request = { origin: this.initialLocation, destination:this.destination, travelMode: google.maps.DirectionsTravelMode.DRIVING };
         var that = this;
         this.directionsService.route(request, function(response, status) {
         if (status == google.maps.DirectionsStatus.OK) {
         that.directionsDisplay.setDirections(response);
         if (that.marker2 == null) {
         that.marker2 = new google.maps.Marker({ position: that.initialLocation, map: that.map, title:"Origin" });
         } else that.marker2.setVisible(true);
         document.getElementById("directions").style.display = 'block';
         that.dirFlag = true;
         that.loadedHandler(true, "");
         } else that.handleGoggleErrors(status);
         that.dirScroller.setupScroller(true);
         that.dirScroller.scrollTo(0, 0);
         });
         */
    //} else 
    //{
    //    this.directionsDisplay.setPanel(document.getElementById("directions"));
    //    this.dirScroller = new TouchScroll(document.querySelector("#directionsFrame", {elastic: true}));
    //    $("#loader").hide();
    //}
};
GoogleMapClass.prototype.buildGoogleDirections = function() {
    // map.setCenter(initialLocation);
    //alert(this.initialLocation);
    var request = { origin: this.initialLocation, destination:this.destination, travelMode: google.maps.DirectionsTravelMode.DRIVING };
    var that = this;
    this.directionsService.route(request, function(response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            that.directionsDisplay.setDirections(response);
            if (that.marker2 == null) {
                that.marker2 = new google.maps.Marker({ position: that.initialLocation, map: that.map, title:"Origin" });
            } else that.marker2.setVisible(true);
            document.getElementById("directions").style.display = 'block';
            that.dirFlag = true;
            that.loadedHandler(true, "");
        } else that.handleGoggleErrors(status);
       //that.dirScroller.setupScroller(true);
       //that.dirScroller.scrollTo(0, 0);
       modalDialog.hideLoader();

    });
    //$("#map_canvas").animate({height:"400px"},"slow");

};

GoogleMapClass.prototype.handleNoGeolocation = function(errorFlag) {
    if (errorFlag) {
        alert("Your device or browser software does not appear to support geolocation services."); //google.maps.error1
        this.initialLocation = this.initialDefaultLocation;
    } else {
        alert("Your device or browser software does not appear to support geolocation services."); //google.maps.error1
        this.initialLocation = this.initialDefaultLocation;
    }
};

GoogleMapClass.prototype.handleError = function(error) {
    this.loadedHandler(false, error);
};
GoogleMapClass.prototype.loadedHandler = function(status, errorMsg) {
    $("#loader").hide();
    if (!status) {
        alert(errorMsg);
    }
};

GoogleMapClass.prototype.handleGoggleErrors = function(status) {
    var error = "";
    if (status == google.maps.DirectionsStatus.NOT_FOUND)
        error = 'No corresponding geographic location could be found for one of the specified addresses. This may be due to the fact that the address is relatively new, or it may be incorrect. Error code:' + status; //google.maps.error3
    else if (status == google.maps.DirectionsStatus.REQUEST_DENIED) error = 'A geocoding or directions request could not be successfully processed, yet the exact reason for the failure is not known. Error code:' + status;    //google.maps.error4
    else if (status == google.maps.DirectionsStatus.INVALID_REQUEST) error = 'A directions request could not be successfully parsed. Error code:' + status;  //google.maps.error7
        else error = 'An unknown error occurred. Error code:' + status;  //google.maps.error8
    this.loadedHandler(false, error);
};

// *** DIRECTION DIALOG
GoogleMapClass.prototype.showDirections = function()
{
	var oTitleStyle = {"margin-top":"4px"};
	this.oModalDialog.renderTitle("Directions",oTitleStyle);
	this.oModalDialog.showExpandButton();

	var aBodyHTML = [];
	aBodyHTML.push(	'<div id="modal_body">');
	aBodyHTML.push(		'<div id="directionsFrame" class="directionsFrame scroller_native"><div id="directions" class="directionsFrameContent"></div></div>');
	aBodyHTML.push(	'</div>');
	this.oModalDialog.renderBody(aBodyHTML);

	//var oDialogStyle = {top:"90px",right:"15px",left:"auto",width:"250px",height:"640px",zIndex:5};
	var oDialogStyle = {top:"100px",right:"15px",left:"auto",width:"300px",height:"560px",zIndex:5};
	this.oModalDialog.showDialog(oDialogStyle);
	this.oModalDialog.showLoader(oDialogStyle);
 
    this.getGoogleDirections();
};
