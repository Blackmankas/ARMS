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

$refer = $_GET["refer"];
$error = isset($_GET["Error"]) ? $_GET["Error"] : '';

if ($refer == 'scan'){
	$upc = $_GET["UPC"];
	$sql = "select * from ScannerLookup WHERE productID = $upc";
}else{
	$search = $_GET["search"];
	$sql = "select * from ScannerLookup WHERE productName = '$search'";
}


$result = mysqli_query($link, $sql);
if (!$result) {
    echo "Error finding UPC: " . mysqli_error($link);
}else{
	foreach($result as $row)
	{
		$upc = $row['productID'];
		$productName = $row['productName'];
		$productPrice = $row['productPrice'];
		$aisleLocation = $row['aisleLocation'];
		$invetoryCount = $row['inventoryCount'];
	}
	header("Location: http://localhost:8000/item.html?upc=".$upc."&productName=".$productName."&productPrice=".$productPrice."&aisleLocation=".$aisleLocation."&inventoryCount=".$invetoryCount."&Error=".$error);
	exit();
}




?>