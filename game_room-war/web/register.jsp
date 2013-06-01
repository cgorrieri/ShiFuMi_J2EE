<%--
 Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
 Use is subject to license terms.
--%>

<%@page import="enterprise.game_room_ejb.ejb.session.PlayerSessionBeanLocal"%>
<%@page import="enterprise.game_room_ejb.persistence.Player"%>
<%@page import="enterprise.game_room_ejb.ejb.session.PlayerSessionBean"%>
<%@page import="javax.naming.InitialContext"%>
<!--
 Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<!--------------------
LOGIN FORM
by: Amit Jakhu
www.amitjakhu.com
--------------------->

<!--META-->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SHI-FU-MI | Connexion</title>

<!--STYLESHEETS-->
<link href="css/style.css" rel="stylesheet" type="text/css" />

<!--SCRIPTS-->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.2.6/jquery.min.js"></script>
<!--Slider-in icons-->
<script type="text/javascript">
$(document).ready(function() {
	$(".username").focus(function() {
		$(".user-icon").css("left","-48px");
	});
	$(".username").blur(function() {
		$(".user-icon").css("left","0px");
	});
	
	$(".password").focus(function() {
		$(".pass-icon").css("left","-48px");
	});
	$(".password").blur(function() {
		$(".pass-icon").css("left","0px");
	});
});
</script>

</head>
<body>

<!--WRAPPER-->
<div id="wrapper">

	<!--SLIDE-IN ICONS-->
    <div class="user-icon"></div>
    <!--<div class="pass-icon"></div>
    END SLIDE-IN ICONS-->

<!--LOGIN FORM-->
<form name="login-form" class="login-form" action="" method="post">

	<!--HEADER-->
    <div class="header">
    <!--TITLE--><h1>SHI-FU-MI</h1><!--END TITLE-->
    <!--DESCRIPTION--><span>Remplir le formulaire de connexion ou inscrivez-vous !</span><!--END DESCRIPTION-->
    </div>
    <!--END HEADER-->
	
    <%
    String pseudo = request.getParameter("pseudo");
    String email = request.getParameter("mail");
    String mdp = request.getParameter("mdp");

    if (pseudo != null && !"".equals(pseudo)) {
        try {
            
            InitialContext ic = new InitialContext();
            Object o = ic.lookup("java:global/game_room/game_room-ejb/PlayerSessionBean");

            PlayerSessionBeanLocal playerSession = (PlayerSessionBeanLocal) o;
            Player player = new Player(pseudo, email, mdp);
            playerSession.register(player);
            session.setAttribute("PlayerId", playerSession.getPlayer().getId());
            session.setAttribute("PSB", playerSession);
            
    %>
        <div class="message">
            <div class="ok">
                Inscription reussite, Bienvenue sur SHI-FU-MI
                <script type="text/javascript">
                    window.location.href='room.jsp';
                </script>
            </div>
        </div>
<%
        
    } catch(Exception e) {
        e.printStackTrace();
%>
        <div class="message">
            <div class="erreur">
                Inscription échoué, le pseudo <% out.print(pseudo); %> existe déjà
            </div>
        </div>
<%
    }
}
%>

	<!--CONTENT-->
    <div class="content">
	<!--USERNAME--><input name="pseudo" type="text" required="required" pattern=".{3,15}" class="input username" placeholder="Nom" /><!--END USERNAME-->
        <!--MAIL--><input name="mail" type="email" required="required" class="input mail" placeholder="Adresse mail"  /><!--END USERNAME-->
        <!--PASSWORD--><input name="mdp" type="password" required="required" class="input password" placeholder="Mot de passe" /><!--END PASSWORD-->
    </div>
    <!--END CONTENT-->
    
    <!--FOOTER-->
    <div class="footer">
    <!--LOGIN BUTTON--><input type="submit" name="submit" value="Creer" class="button" /><!--END LOGIN BUTTON-->
    </div>
    <!--END FOOTER-->

</form>
<!--END LOGIN FORM-->

</div>
<!--END WRAPPER-->

<!--GRADIENT--><div class="gradient"></div><!--END GRADIENT-->

</body>
</html>	