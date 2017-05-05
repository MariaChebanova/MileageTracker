<?php
	/* MileageTracker */

	include("common.php");
	sessionSet();
	head();
?>

    <div class="headline container-fluid">
        <p>Welcome! Register for MileageTracker.</p>
    </div>
    <div id="register">
        <div class="form">
          <h2>Register</h2>
          <form>
            <input type="text" name="PassengerFName" placeholder="first name" />
            <input type="text" name="PassengerMName" placeholder="middle name (optional)" />
            <input type="text" name="PassengerLName" placeholder="last name" />
            <input type="email" name="PassengerEmail" placeholder="email" />
            <input type="text" name="PassengerUserName" placeholder="username" />
            <input type="password" name="PassengerPassword" placeholder="password" />
            <input type="button" value="Register" />
          </form>
        </div>
    </div>

<?= footer() ?>