<?php

	include("common.php");
	sessionNotSet();
	head();

	$airline = trim($_GET["airline"]);
	$user = $_SESSION["name"];
	
	// connect to DB
	$connect = dbConnect();
	
	$sql = "SELECT Passenger_Airline.MileageBalance, Passenger_Airline.MileageBalanceExpirationDate
			FROM Passenger_Airline 
			JOIN Passenger ON Passenger_Airline.PassengerID = Passenger.PassengerID 
			JOIN Airline ON Airline.AirlineID = Passenger_Airline.AirlineID
			JOIN Credentials ON Credentials.PassengerID = Passenger.PassengerID
			WHERE Credentials.Username = '".$user."'
			AND Airline.AirlineCode = '".$airline."'";
	
	$result = $connect->query($sql);
	
	if (!$result->num_rows !== 0) {
		$row = mysqli_fetch_assoc($result);
		$points = $row['Passenger_Airline.MileageBalance'];
	} else {
		$points = 0;
	}
	
	echo "<h2>Please login to airline, and copy points and expiration</h2>";
	
    echo "	<div id=\"update\">
        		<div class=\"form\">
          			<h2>Airline Points</h2>
          			<form id=\"regform\" action=\"airline_complete2.php\" method=\"post\">
          				<input type=\"number\" name=\"Miles\" placeholder=\"$points\" />
          				<input type=\"date\" name=\"Expiration\" placeholder=\"".date('Y-m-d')."\"/>
						<input type=\"hidden\" name=\"Airline\" value=\"$airline\"/>
      					<input type=\"submit\" value=\"Update\" />
					</form>
				</div>
			</div>";
    
    $sql = "SELECT Airline_URL.AirlineURL AS Url, Airline_URL.AirlineURLFormIndex AS Ind FROM Airline_URL 
			JOIN Airline ON Airline_URL.AirlineURLID = Airline.AirlineLoginURLID 
			WHERE Airline.AirlineCode = '$airline'";
    
    $result = $connect->query($sql);
    
    if ($result->num_rows === 1) {
    	
    	// get the url from the result
    	$resarray = $result->fetch_assoc();
    	$url = $resarray['Url'];
    } else {
    	die();
    }
    
    echo "	<div><iframe src=$url ></iframe ></div>";
    
    footer();
?>