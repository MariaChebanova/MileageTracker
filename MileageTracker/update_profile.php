<?php
	/*
		MileageTracker
	*/

	include ("common.php");
	sessionNotSet();

	$user = $_SESSION["name"];

	// get form info
	$fName = trim($_POST["PassengerFName"]);
	$lName = trim($_POST["PassengerLName"]);
	$email = trim($_POST["PassengerEmail"]);
	//$login = trim($_POST["PassengerUserName"]);
	$password = trim($_POST["PassengerPassword"]);
	
	// connect to DB
	$connect = dbConnect();

	if (!empty($fName)) {
		$sql = "UPDATE Passenger P JOIN Credentials C on P.PassengerID = C.PassengerID
				SET P.PassengerFName = '".$fName."'
				WHERE C.Username = '".$user."'";

		$update = $connect->query($sql);
	}

	if (!empty($lName)) {
		$sql = "UPDATE Passenger P JOIN Credentials C on P.PassengerID = C.PassengerID
				SET P.PassengerLName = '".$lName."'
				WHERE C.Username = '".$user."'";

		$update = $connect->query($sql);
	}

	if (!empty($email)) {
		$sql = "UPDATE Passenger P JOIN Credentials C on P.PassengerID = C.PassengerID
				SET P.PassengerEmail = '".$email."'
				WHERE C.Username = '".$user."'";

		$update = $connect->query($sql);
	}

	if (!empty($password)) {
		$sql = "UPDATE Credentials
				SET Password = '".$password."'
				WHERE Username = '".$user."'";

		$update = $connect->query($sql);
	}

	/*if (!empty($login)) {
		$sql = "UPDATE Credentials
				SET Username = '".$login."'
				WHERE Username = '".$user."'";

		$update = $connect->query($sql);

		header("Location: logout.php");
		die();
	}*/
	
	
	header("Location: myprofile.php");

?>