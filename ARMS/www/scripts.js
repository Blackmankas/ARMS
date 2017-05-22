var app = {
	
	Scan: function() {
		cordova.plugins.barcodeScanner.scan(
		
			// This code runs if the scan succeeds
			function (result) {
				window.location.replace("http://ec2-54-215-188-203.us-west-1.compute.amazonaws.com/scanner.php?UPC="+result.text+"&refer=scan");
			},
			
			// This code runs if the scan fails
			function (error) {
				alert("Scanning failed: " + error);
			},
			
			// scanner configuration settings
			{
				showTorchButton : true, // iOS and Android
				torchOn: false, // Android, launch with the torch switched on (if available)
				prompt : "Place a barcode inside the scan area", // Android
				resultDisplayDuration: 0, // Android, display scanned text for X ms. 0 suppresses it entirely, default 1500
				//formats : "QR_CODE,PDF_417", // default: all but PDF_417 and RSS_EXPANDED
				disableAnimations : true, // iOS
				disableSuccessBeep: false // iOS
			}
		);
	}
	
};