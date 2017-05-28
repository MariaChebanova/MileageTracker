<?php

	include("common.php");
	sessionNotSet();
	
	$airline = trim($_POST['Airline']);
	$points = trim($_POST['Miles']);
	$expiration = trim($_POST['Expiration']);
	
	$user = $_SESSION["name"];
	
	// update the database
	$connect = dbConnect();
	
	// figure out passengerID
	$sql = "SELECT PassengerID FROM Credentials WHERE Username='$user'";
	$result = $connect->query($sql);
	if ($result->num_rows !== 0) {
		$row = mysqli_fetch_assoc($result);
		$passengerID = $row['PassengerID'];
	} else {
		die();
	}
	
	// figure out airlineID
	$sql = "SELECT AirlineID FROM Airline WHERE AirlineCode='$airline'";
	$result = $connect->query($sql);
	if ($result->num_rows !== 0) {
		$row = mysqli_fetch_assoc($result);
		$airlineID = $row['AirlineID'];
	} else {
		die();
	}
	
	// figure out if points for this airline already exist
	$sql = "SELECT * FROM Passenger_Airline WHERE AirlineID='$airlineID' AND PassengerID='$passengerID'";
	$result = $connect->query($sql);
	if ($result->num_rows !== 0) {
		$sql = "UPDATE Passenger_Airline SET MileageBalance='$points', MileageBalanceExpirationDate='$expiration' WHERE AirlineID='$airlineID' AND PassengerID='$passengerID';";
	} else {
		$sql = "INSERT INTO Passenger_Airline (AirlineID, PassengerID, MileageBalance, MileageBalanceExpirationDate) VALUES ('$airlineID', '$passengerID', '$points', '$expiration');";
	}
	$result = $connect->query($sql);
	
	header("Location: index.php");
	die();
?>