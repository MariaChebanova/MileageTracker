<?php
	/*
		MileageTracker
	*/

	include("common.php");
	
	// get form info
	$fName = trim($_POST["PassengerFName"]);
	$mName = trim($_POST["PassengerMName"]);
	$lName = trim($_POST["PassengerLName"]);
	$email = trim($_POST["PassengerEmail"]);
	$login = trim($_POST["PassengerUserName"]);
	$password = trim($_POST["PassengerPassword"]);
	
	// check for empty
	if (empty($login)
		|| empty($password)
		|| empty($fName)
		|| empty($lName)
		|| empty($email)) {
		die("Invalid attempt to register in");
	}

	// connect to DB
	$connect = dbConnect();

	// query users
	$passAdd = "INSERT INTO Passenger (PassengerFName, PassengerMName, PassengerLName, PassengerEmail)
			VALUES ('".$fName."', '".$mName."', '".$lName."', '".$email."');";

	$passengerAdd = $connect->query($passAdd);



	$credAdd = "INSERT INTO Credentials (PassengerID, Username, Password)
				VALUES (LAST_INSERT_ID(), '".$login."', '".$password."');";

	$credsAdd = $connect->query($credAdd);

	header("Location: index.php");

?>