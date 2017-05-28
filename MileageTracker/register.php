<?php
	/* MileageTracker */

	include("common.php");
	sessionSet();
	head();
?>
    
    <div class="headline container-fluid">
    </div>
    <div id="register">
        <div class="form">
          <h2>Register</h2>
          <form id="regform" action="new_user_reg.php" method="post">
            <input type="text" name="PassengerFName" placeholder="first name" />
            <input type="text" name="PassengerLName" placeholder="last name" />
            <input type="email" name="PassengerEmail" placeholder="email" />
            <input type="number" name="PassengerPhoneNumber" placeholder="phone" />
            <input type="text" name="PassengerUserName" placeholder="username" />
            <input type="password" name="PassengerPassword" placeholder="password" />
            <span class="form-checkbox">
                <input type="checkbox" name="AlertEmail" value="Email" /> email alerts&nbsp;
                <input type="checkbox" name="AlertPhone" value="Phone" /> text alerts
            </span><br>
            <input type="submit" value="Register" />
          </form>
        </div>
    </div>

<?= footer() ?>