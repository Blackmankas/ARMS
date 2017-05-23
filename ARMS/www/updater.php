<?php
error_reporting(E_ALL);
ini_set('display_errors', 'on');

$link = mysqli_connect("localhost", "root", "", "A.R.M.S");  //change this to work with the local database -- IP, username, password, database name

if (!$link) {
    echo "Error: Unable to connect to MySQL." . PHP_EOL;
    echo "Debugging errno: " . mysqli_connect_errno() . PHP_EOL;
    echo "Debugging error: " . mysqli_connect_error() . PHP_EOL;
    exit;
}

//get what is to be updated from the app
$productID = $_GET['productID'];
$productName = $_GET['productName'];
$productPrice = $_GET['productPrice'];
$aisleLocation = $_GET['aisleLocation'];
$inventoryCount = $_GET['inventoryCount'];

//send the sql statement
if(!(is_null($productID) and is_null($productName) and is_null($productPrice) and is_null($aisleLocation) and is_null($inventoryCount))){
$sql = "UPDATE ScannerLookup
		SET productName = '$productName', productPrice = '$productPrice',
			aisleLocation = '$aisleLocation', inventoryCount = '$inventoryCount'
		WHERE productID = '$productID'";

if (!mysqli_query($link, $sql)) {
	//Error happened
	header("Location: ../scanner.php?UPC=".$productID."&Error=1");
	exit();
}else{
	//Get the new info and go back to the item page
	header("Location: ../scanner.php?UPC=".$productID."&refer=scan&Error=0");
	exit();
}
}



?>