<?php
	/* MileageTracker */

	include("common.php");
	sessionSet();
	head();
?>

	<div class="headline container-fluid">
      <div>
          <div class="row">
              <div id="welcome" class="col-sm-6">
                 MileageTracker helps people manage their airline frequent flyer miles. Sign up today to track all of your miles for free. Be alerted when they expire.
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