<?php
	/*
		MileageTracker
	*/

	include("common.php");
	sessionSet();
	
	// get form info
	$login = trim($_POST["username"]);
	$password = trim($_POST["password"]);
	
	// check for empty
	if (empty($login) || empty($password)) {
		die("Invalid attempt to log in.");
	}

	// connect to DB
	$connect = dbConnect();

	// query users
	$sql = "SELECT Username, Password FROM Credentials WHERE Username = '".$login."' AND Password = '".$password."'";
	$result = $connect->query($sql);

	if ($result->num_rows === 1) {
		makeSession($login);		
	}
	
	# creates a log in session if user enters correct credentials
	function makeSession($login) {
		$_SESSION["name"] = $login;
		
		header("Location: mymiles.php");
		die();
	}
	
	# redirects user back to start page if something went wrong
	function redirectToStart() {
		header("Location: index.php");
		die();
	}
?>