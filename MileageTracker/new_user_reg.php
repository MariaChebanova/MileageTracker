<?php
	/*
		MileageTracker
	*/

	include("common.php");
	
	// get form info
	$fName = trim($_POST["PassengerFName"]);
	$lName = trim($_POST["PassengerLName"]);
	$email = trim($_POST["PassengerEmail"]);
	$phone = trim($_POST["PassengerPhoneNumber"]);
	$login = trim($_POST["PassengerUserName"]);
	$password = trim($_POST["PassengerPassword"]);
	$emailAlerts = trim($_POST["AlertEmail"]);
	$phoneAlerts = trim($_POST["AlertPhone"]);
	
	$password = password_hash($password, PASSWORD_DEFAULT);
	
	// check for empty
	if (empty($login)
		|| empty($password)
		|| empty($fName)
		|| empty($lName)
		|| empty($phone)
		|| empty($email)) {
		echo "<script type='text/javascript'>alert('invalid attempt to register')</script>";
		echo "<script>setTimeout(\"location.href = 'register.php';\",800);</script>";
		die();
	}

	/*
	# checks to see if new user entered in correct login and password format
	if (preg_match("/^[a-z][a-z0-9]{2,7}$/", $login) && preg_match("/^[0-9].{4,10}[^0-9a-zA-Z]$/", $password)) {
	}
	*/

	$email_Alerts = 0;
	$phone_Alerts = 0;

	if (!empty($emailAlerts)) {
		$email_Alerts = 1;
	}

	if (!empty($phoneAlerts)) {
		$phone_Alerts = 1;
	}



	// connect to DB
	$connect = dbConnect();

	// query users
	$passAdd = "INSERT INTO Passenger (PassengerFName, PassengerLName, PassengerEmail, PassengerPhoneNumber, AlertEmail, AlertPhone)
			VALUES ('".$fName."', '".$lName."', '".$email."', '".$phone."', ".$email_Alerts.", ".$phone_Alerts.")";

	$passengerAdd = $connect->query($passAdd);

	$credAdd = "INSERT INTO Credentials (PassengerID, Username, Password)
				VALUES (LAST_INSERT_ID(), '".$login."', '".$password."')";

	$credsAdd = $connect->query($credAdd);

	header("Location: index.php");
?>