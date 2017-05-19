<?php
	/*
		MileageTracker
	*/

	include ("common.php");
	sessionNotSet();

	$user = $_SESSION["name"];

	// get form info
	$days = trim($_POST["AlertDays"]);
	$email = trim($_POST["AlertEmail"]);
	$phone = trim($_POST["AlertPhone"]);
	
	// connect to DB
	$connect = dbConnect();

	if (!empty($days)) {
		$sql = "UPDATE Passenger P JOIN Credentials C on P.PassengerID = C.PassengerID
				SET P.AlertDays = '".$days."'
				WHERE C.Username = '".$user."'";

		$update = $connect->query($sql);
	}

	if (!empty($email)) {
		$sql = "UPDATE Passenger P JOIN Credentials C on P.PassengerID = C.PassengerID
				SET P.AlertEmail = 1
				WHERE C.Username = '".$user."'";

		$update = $connect->query($sql);
	} else {
		$sql = "UPDATE Passenger P JOIN Credentials C on P.PassengerID = C.PassengerID
				SET P.AlertEmail = 0
				WHERE C.Username = '".$user."'";

		$update = $connect->query($sql);
	}

	if (!empty($phone)) {
		$sql = "UPDATE Passenger P JOIN Credentials C on P.PassengerID = C.PassengerID
				SET P.AlertPhone = 1
				WHERE C.Username = '".$user."'";

		$update = $connect->query($sql);
	} else {
		$sql = "UPDATE Passenger P JOIN Credentials C on P.PassengerID = C.PassengerID
				SET P.AlertPhone = 0
				WHERE C.Username = '".$user."'";

		$update = $connect->query($sql);
	}

		header("Location: myprofile.php");

?>