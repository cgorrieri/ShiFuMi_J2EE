<html>
<head>
	<link href="css/styletab.css" rel="stylesheet" type="text/css" />
	<link href="css/jeux.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8"/> 
</head>
<body><h1> Jeux SI-FU-MI</h1>
	<!--
	<div class="header">
		<div class="message">
			<div class="erreur">
				Attention
			</div>
		</div>
	</div>
-->

	<div class="jeux">
		<div class="headNom"> Vous </div>
		<div class="joueurhaut"><center>
			<form action="" method="GET">
				<input class="ima" id="pierre" type="image" name="type" value="pierre" src="images/pierre.png" onclick="javascript:document.getElementById('pierre').style.borderColor='red';">
				<input class="ima" id="papier" type="image" name="type" value="papier" src="images/papier.png" onclick="javascript:document.getElementById('papier').style.borderColor='red';">
				<input class="ima" id="ciseau" type="image" name="type" value="ciseau" src="images/ciseau.png" onclick="javascript:document.getElementById('ciseau').style.borderColor='red';">
			</form>
		</center></div>
		<div class="plateau">
		<center>
			<img class="ima" src="images/pierre.png">
		</center>
		</div>

		<div class="joueurbas"><center>
			<img class="ima" src="images/inconnu.png">
		</center></div>
		<div class="headNom"> Adversaire </div>
	</div>
</body>
</html>

