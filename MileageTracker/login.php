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
		echo "<script type='text/javascript'>alert('invalid attempt to login')</script>";
		echo "<script>setTimeout(\"location.href = 'index.php';\",800);</script>";
		die();
	}

	// connect to DB
	$connect = dbConnect();

	// query users
	$sql = "SELECT Username, Password FROM Credentials WHERE Username = '$login'";
	$result = $connect->query($sql);

	if ($result->num_rows === 1) {
		$row = mysqli_fetch_assoc($result);
		$passwordHash = $row['Password'];
		
		if (password_verify($password, $passwordHash)) {
			makeSession($login);		
		} else {
			echo "<script type='text/javascript'>alert('incorrect password $passwordHash  $password')</script>";
			echo "<script>setTimeout(\"location.href = 'index.php';\",800);</script>";
			die();
		}	
	} else {
		echo "<script type='text/javascript'>alert('incorrect username')</script>";
		echo "<script>setTimeout(\"location.href = 'index.php';\",800);</script>";
		die();
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