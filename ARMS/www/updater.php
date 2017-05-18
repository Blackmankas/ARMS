<?php
header('Content-Type: application/json');

$link = mysqli_connect("127.0.0.1", "root", "", "A.R.M.S");  //change this to work with the local database -- IP, username, password, database name

if (!$link) {
    echo "Error: Unable to connect to MySQL." . PHP_EOL;
    echo "Debugging errno: " . mysqli_connect_errno() . PHP_EOL;
    echo "Debugging error: " . mysqli_connect_error() . PHP_EOL;
    exit;
}

//get what is to be updated from the app
$column = $_POST['column'];
$newValue = $_POST['value'];
$UPC = $_POST['UPC'];

//send the sql statement
$sql = "UPDATE Product SET $column = $value WHERE productSKU = $UPC";

if (!mysqli_query($link, $sql)) {
	echo "Error changing values: " . mysqli_error($link);
}

//return true or false? //Dont know how to actually do this for an html file

?>