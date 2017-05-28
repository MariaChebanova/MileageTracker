<?php
	/*
    	MileageTracker
    */
        
	include("common.php");
    // sessionNotSet();
	
	// get the action url, and return the post array
	// to what the airline would expect
	$airline = trim($_POST['MTAirlineCode']);
	unset($_POST['MTAirlineCode']);
	
	
	$connect = dbConnect();
	$sql = "SELECT Airline_URL.AirlineURL AS Url FROM Airline_URL JOIN Airline ON Airline_URL.AirlineURLID = Airline.AirlineLoginURLID WHERE Airline.AirlineCode = '$airline'";
	$result = $connect->query($sql);
	if ($result->num_rows === 1) {
				
		// get the url from the result
		$resarray = $result->fetch_assoc();
		$loginurl = $resarray['Url'];
				
		$doc = new DOMDocument();
		$doc->loadHTMLFile($loginurl);
	}
	
	$sql = "SELECT Airline_URL.AirlineURL AS Url FROM Airline_URL JOIN Airline ON Airline_URL.AirlineURLID = Airline.AirlinePointsURLID WHERE Airline.AirlineCode = '$airline'";
	$result = $connect->query($sql);
	if ($result->num_rows === 1) {
		
		// get the points url
		$resarray = $result->fetch_assoc();
		$pointsurl = $resarray['Url'];
		
		print_r($pointsurl);
	}
	
	$content = request($actionurl, $pointsurl);
	
	
	print_r($content);
	
	
	
function request($actionurl, $pointsurl) {
	$ch = curl_init($actionurl);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_POSTFIELDS, $_POST);
	curl_setopt($ch, CURLOPT_COOKIEJAR, 'cookie.txt');
	$result = curl_exec($ch);

	curl_setopt($ch, CURLOPT_URL, $pointsurl);
	$result = curl_exec($ch);
	
	curl_close($ch);
	
	return $result;
}
	
?>