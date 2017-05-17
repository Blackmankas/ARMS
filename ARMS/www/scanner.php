<?php
header('Content-Type: application/json');

$link = mysqli_connect("127.0.0.1", "root", "", "DBNAME");  //change this to work with the local database -- IP, username, password, database name

if (!$link) {
    echo "Error: Unable to connect to MySQL." . PHP_EOL;
    echo "Debugging errno: " . mysqli_connect_errno() . PHP_EOL;
    echo "Debugging error: " . mysqli_connect_error() . PHP_EOL;
    exit;
}

//get UPC code from app, insert into sql statement
$sql = "SELECT * from Product WHERE productSKU = $_POST['UPC']";

//encode as .json
$result = mysqli_query($link, $sql);
$rows = array();
	while($r = mysql_fetch_assoc($result)){
		$rows['productSKU'][] = $r;
	}
	
echo json_encode($rows);

?>