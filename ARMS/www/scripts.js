var app = {
	
	Scan: function() {
		cordova.plugins.barcodeScanner.scan(
		
			// This code runs if the scan succeeds
			function (result) {
				window.location.replace("home.html?barcode="+result.text);
				//alert("We got a barcode\n" +
						//"Result: " + result.text);
			},
			
			// This code runs if the scan fails
			function (error) {
				alert("Scanning failed: " + error);
			},
			
			// scanner configuration settings
			{
				//preferFrontCamera : true, // iOS and Android
				showFlipCameraButton : true, // iOS and Android
				showTorchButton : true, // iOS and Android
				torchOn: false, // Android, launch with the torch switched on (if available)
				prompt : "Place a barcode inside the scan area", // Android
				resultDisplayDuration: 500, // Android, display scanned text for X ms. 0 suppresses it entirely, default 1500
				//formats : "QR_CODE,PDF_417", // default: all but PDF_417 and RSS_EXPANDED
				//orientation : "landscape", // Android only (portrait|landscape), default unset so it rotates with the device
				disableAnimations : true, // iOS
				disableSuccessBeep: false // iOS
			}
		);
	}
	
};