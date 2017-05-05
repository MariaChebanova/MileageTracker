<?php
	/*
		MileageTracker
	*/
		
	$login = trim($_POST["PassengerUserName"]);
	$password = trim($_POST["PassengerPassword"]);
	
	if (empty($login) || empty($password)) {
		die("Invalid attempt to log in");
	} 
	
	$users = file("users.txt");
	
	foreach ($users as $user) {
		$credentials = explode(":", $user);
		if ($login == trim($credentials[0])) {
			if ($password == trim($credentials[1])) {
				makeSession($login);
			} else {
				redirectToStart();
			}
		}
		# clears out the credentials before next look
		$credentials = "";
		$user = "";
	}
	
	# checks to see if new user entered in correct login and password format
	if (preg_match("/^[a-z][a-z0-9]{2,7}$/", $login) && preg_match("/^[0-9].{4,10}[^0-9a-zA-Z]$/", $password)) {
		file_put_contents("users.txt", "\n", FILE_APPEND);
		file_put_contents("users.txt", $login, FILE_APPEND);
		file_put_contents("users.txt", ":", FILE_APPEND);
		file_put_contents("users.txt", $password, FILE_APPEND);
		
		makeSession($login);
	} else {
   		redirectToStart();
	} 
	
	# creates a log in session if user enters correct credentials
	# sets the last login cookie to be this user's log on time
	function makeSession($login) {
		$_SESSION["name"] = $login;
		setcookie("lastLogin", date("D y M d, g:i:s a"), time() + 60 * 60 * 24 * 7);

		header("Location: mymiles.php");
		die();
	}
	
	# redirects user back to start page if something went wrong
	function redirectToStart() {
		header("Location: index.html");
		die();
	}
?>