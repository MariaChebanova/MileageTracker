<?php
	/*
		MileageTracker
	*/

	include ("common.php");
	sessionNotSet();

	$user = $_SESSION["name"];

	// get form info
	$days = trim($_POST["AlertDays"]);
	
	// connect to DB
	$connect = dbConnect();

	if (!empty($days)) {
		$sql = "UPDATE Passenger P JOIN Credentials C on P.PassengerID = C.PassengerID
				SET P.AlertDays = '".$days."'
				WHERE C.Username = '".$user."'";

		$update = $connect->query($sql);
	}

		header("Location: myprofile.php");

?>