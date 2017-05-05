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
		//die("Invalid attempt to log in");
	}

	// connect to DB
	$connect = dbConnect();

	// query users
	$sql = "SELECT Username, Password FROM Credentials WHERE Username = '".$login."' AND Password = '".$password."'";
	$result = $connect->query($sql);

	if ($result->num_rows === 1) {
		makeSession($login);


		/*
		$user = $result->fetch_assoc();
		if ($login === $user["Username"]) {
			
			$get_password = "SELECT Password FROM Credentials WHERE Username = '".$login."'";
			$found_password = $connect->query($get_password);
			
			while ($pass = $found_password->fetch_assoc()) {
				if ($password === $pass["Password"]) {
					makeSession($login);
				}
			}

		} else {
			echo "user not found";
		}
		*/
		
	}


	/*
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

*/
	
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