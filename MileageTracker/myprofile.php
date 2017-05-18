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
	
	$sql = "SELECT P.PassengerFName, P.PassengerLName, P.PassengerEmail, P.AlertDays, C.Username, C.Password
			FROM Passenger P
			JOIN Credentials C ON P.PassengerID = C.PassengerID
			WHERE C.Username = '".$user."'";
	
	$result = $connect->query($sql);

	if (!$result->num_rows === 1) {
		die();
	}
?>
	<div id="main">
        <h2><?= "$user" ?>'s Profile</h2>

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
          
           <input type=\"password\" name=\"PassengerPassword\" placeholder=\"".$row['Password']."\" />
      		<input type=\"submit\" value=\"Update\" /></form></div></div>";

    echo "</div>";


    echo "<div class=\"col-sm-6\">";
    echo "<div id=\"register\">
        <div class=\"form\">
          <h2>My Preferences</h2>
          <form id=\"regform\" action=\"update_preferences.php\" method=\"post\">
<input type=\"text\" name=\"AlertDays\" placeholder=\"Start Alert This Many Days Before Expiration: ".$row['AlertDays']."\" />

          <input type=\"submit\" value=\"Update\" /></form></div></div>";

    echo "</div></div></div></div>";


    
footer();
//<input type=\"text\" name=\"PassengerUserName\" placeholder=\"".$row['Username']."\" />
?>
            
            
            
            
       
