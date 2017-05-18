<?php
	/*
		MileageTracker
	*/

	include("common.php");
	
	// get form info
	$fName = trim($_POST["PassengerFName"]);
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
		die("Invalid attempt to register.");
	}

	/*
	# checks to see if new user entered in correct login and password format
	if (preg_match("/^[a-z][a-z0-9]{2,7}$/", $login) && preg_match("/^[0-9].{4,10}[^0-9a-zA-Z]$/", $password)) {
	}
	*/

	// connect to DB
	$connect = dbConnect();

	// query users
	$passAdd = "INSERT INTO Passenger (PassengerFName, PassengerLName, PassengerEmail)
			VALUES ('".$fName."', '".$lName."', '".$email."');";

	$passengerAdd = $connect->query($passAdd);

	$credAdd = "INSERT INTO Credentials (PassengerID, Username, Password)
				VALUES (LAST_INSERT_ID(), '".$login."', '".$password."');";

	$credsAdd = $connect->query($credAdd);

	header("Location: index.php");
?>