<?php
	/*
		MileageTracker
	*/

	include ("common.php");
	sessionNotSet();
	head();
	
	$user = $_SESSION["name"];

	// connect to DB
	$connect = dbConnect();
	
	$sql = "SELECT P.PassengerFName, P.PassengerLName, P.PassengerEmail, P.PassengerPhoneNumber, P.AlertDays, P.AlertEmail, P.AlertPhone, C.Username, C.Password
			FROM Passenger P
			JOIN Credentials C ON P.PassengerID = C.PassengerID
			WHERE C.Username = '".$user."'";
	
	$result = $connect->query($sql);

	if (!$result->num_rows === 1) {
		die();
	}
?>
	<div id="main">

<?php

    echo "<div class=\"headline container-fluid\"><div class=\"row\"><div class=\"col-sm-6\">";
     $row = mysqli_fetch_assoc($result);
    echo "<div id=\"register\">
        <div class=\"form\">
          <h2>My Info</h2>
          <form id=\"regform\" action=\"update_profile.php\" method=\"post\">
          <input type=\"text\" name=\"PassengerFName\" placeholder=\"".$row['PassengerFName']."\" />
          <input type=\"text\" name=\"PassengerLName\" placeholder=\"".$row['PassengerLName']."\" />
          <input type=\"email\" name=\"PassengerEmail\" placeholder=\"".$row['PassengerEmail']."\" />
          <input type=\"number\" name=\"PassengerPhoneNumber\" placeholder=\"".$row['PassengerPhoneNumber']."\" />
           <input type=\"password\" name=\"PassengerPassword\" placeholder=\"type a new password here...\" />
      		<input type=\"submit\" value=\"Update\" /></form></div></div>";

    echo "</div>";


    echo "<div class=\"col-sm-6\">";
    echo "<div id=\"register\">
        <div class=\"form\">
          <h2>My Preferences</h2>
          <form id=\"regform\" action=\"update_preferences.php\" method=\"post\">
<input type=\"number\" name=\"AlertDays\" placeholder=\"Alert Before Days: ".$row['AlertDays']."\" />";

	$emailAlert = $row['AlertEmail'];
	$phoneAlert = $row['AlertPhone'];

	echo "<span class=\"form-checkbox\">";
	if ($emailAlert == 1) {
		echo"<input type=\"checkbox\" name=\"AlertEmail\" value=\"Email\" checked=\"checked\"> email alerts";
	} else {
		echo"<input type=\"checkbox\" name=\"AlertEmail\" value=\"Email\"> email alerts";
	}

	if ($phoneAlert == 1) {
		echo"&nbsp;<input type=\"checkbox\" name=\"AlertPhone\" value=\"Phone\" checked=\"checked\"> text alerts<br>";
	} else {
		echo"&nbsp;<input type=\"checkbox\" name=\"AlertPhone\" value=\"Phone\"> text alerts<br>";
	}

    echo "</span><input id=\"update_button\" type=\"submit\" value=\"Update\" /></form></div></div>";

    echo "</div></div></div></div>";


    
footer();
//<input type=\"text\" name=\"PassengerUserName\" placeholder=\"".$row['Username']."\" />
?>
            
            
            
            
       
