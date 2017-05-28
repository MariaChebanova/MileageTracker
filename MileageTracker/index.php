<?php
	/* MileageTracker */

	include("common.php");
	sessionSet();
	head();
?>

	<div class="headline container-fluid">
      <div class="main-icons">
        <div class="col-sm-4">
          <img class="main-icon" src="images/time.png" /><br>
          Miles Expire
        </div>
         <div class="col-sm-4">
          <img class="main-icon" src="images/travel.png" /><br>
          Rules Differ
        </div>
        <div class="col-sm-4">
          <img class="main-icon" src="images/card.png" /><br>
          Difficult Redemptions
        </div>
      </div>
      <div class="login">
          <div id="welcome" class="col-sm-6">
             Add. Track. Notify. Redeem.<br>
             MileageTracker allows you to track all of your frequent flyer miles in one place. No more complicated charts with balances and hard-to-understand expiration dates. You'll be automatically notified before your miles expire.
          </div>
          <div id="login" class="col-sm-6">
              <div class="form">
                <h2>Login</h2>
                <form id="loginform" action="login.php" method="post">
                  <input type="text" name="username" placeholder="username" />
                  <input type="password" name="password" placeholder="password" />
                  <input type="submit" value="Login" />
                </form>
              </div>
          </div>
      </div>
    </div>
  </div>

<?= footer() ?>