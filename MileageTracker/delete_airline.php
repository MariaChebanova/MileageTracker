<?php
	/*
		MileageTracker
	*/

	include ("common.php");
	sessionNotSet();
	
	$airline = trim($_GET["airline"]);
	$user = $_SESSION["name"];
	
	if (empty($airline)) {
		die("Invalid airline");
	}

	// connect to DB
	$connect = dbConnect();
	
	$sql = "SELECT AirlineID FROM Airline WHERE AirlineCode = '".$airline."'";
	
	$airline = $connect->query($sql);

	$sql = "SELECT P.PassengerID FROM Passenger P JOIN Credentials C ON P.PassengerID = C.PassengerID WHERE C.Username = '".$user."'";
	
	$userID = $connect->query($sql);

	$AI = mysqli_fetch_assoc($airline);

   $UI = mysqli_fetch_assoc($userID);

   $sql = "DELETE FROM Passenger_Airline WHERE AirlineID = '".$AI['AirlineID']."' AND PassengerID = '".$UI['PassengerID']."'";
	
	$result = $connect->query($sql);

	header("Location: mymiles.php");
	//die();
   

?>