<?php
	/*
		MileageTracker
	*/

	# displays the header
	function head() {
?>
		<!DOCTYPE html>
		<html lang="en">
		    <head>
		        <meta charset="UTF-8">
		        <meta name="viewport" content="width=device-width, initial-scale=1">
		        <title>MileageTracker</title>

		        <link rel="stylesheet" href="styles.css"/>
		        <link rel="icon" type="image/ico" href="">
		    </head>
		    <body>
		        <nav>
		            <a href="index.php"><object id="airplane" data="images/airplane.svg" type="image/svg+xml"></object></a>
		            <div id="nav-links">
		                <a href="index.php#login">Login</a>
		                <a href="register.php">Register</a>
		                <a href="about.php">About</a>
		            </div>
		        </nav>
		        <div id="head" class="container-fluid">
		            <h1>MileageTracker</h1>
		            <p id="motto">never let your miles expire</p>
		        </div>

<?php
	}

	# displays the footer
	function footer() {
?>

				<div id="footer" class="container-fluid">
		            <div class="row">
		                <div class="col-sm-3">
		                   &copy; 2017 MileageTracker
		                </div>
		                <div class="col-sm-9">
		                    <p>Imprint</p>
		                </div>
		            </div>
		        </div>
		    </body>
		</html>

<?php
	}

	# checks to see if session is set, redirects if it is
	function sessionSet() {
		session_start();
		
		if (isset($_SESSION["name"])) {
			header("Location: mymiles.php");
			die();
		}
	}
	
	# checks to see if session is not set, redirects if it isn't
	function sessionNotSet() {
		session_start();
		
		if (!isset($_SESSION["name"])) {
			header("Location: index.php");
			die();
		}
	}

	# database connect
	function dbConnect() {
		$servername = "vergil.u.washington.edu";
		$username = "front";
		$password = "MileageTrackerFront";
		$db = "MileageTracker";
		$port = 7713;

		// create connection
		$connect = new MySQLi($servername, $username, $password, $db, $port);

		// check connection
		if ($connect->connect_error) {
			die("Connection failed: " . $connect->connect_error);
		} else {
			return $connect;
		}
	}
?>