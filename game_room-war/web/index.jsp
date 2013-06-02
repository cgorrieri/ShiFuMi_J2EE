<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.jms.JMSException"%>
<%@page import="javax.annotation.Resource"%>
<%@page import="java.io.Console"%>
<%@page import="java.util.List"%>
<%@page import="enterprise.game_room_ejb.ejb.session.PlayerSessionBeanLocal"%>
<%@page import="enterprise.game_room_ejb.ejb.session.GameSessionBeanLocal"%>
<%@page import="enterprise.game_room_ejb.common.PlayerNotFoundException"%>
<%@page import="enterprise.game_room_ejb.persistence.Player"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>SHI-FU-MI | Connexion</title>

        <link href="css/style.css" rel="stylesheet" type="text/css" />

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
        <div id="wrapper">
            <div class="user-icon"></div>
            <div class="pass-icon"></div>
            
            <form name="login-form" class="login-form" action="index.jsp" method="post">
                <div class="header">
                    <h1>SHI-FU-MI</h1>
                    <span>Remplir le formulaire de connexion ou inscrivez-vous !</span>
                </div>
                
                <div class="message">
                <%
                    String pseudo = request.getParameter("username");
                    String mdp = request.getParameter("password");
                    String deconnexion = request.getParameter("deconnexion");
                    
                    if (pseudo != null && mdp != null) {
                        if (!"".equals(pseudo) && !"".equals(mdp)) {
                            try {
                                InitialContext ic = new InitialContext();
                                Object o = ic.lookup("java:global/game_room/game_room-ejb/PlayerSessionBean");

                                PlayerSessionBeanLocal playerSession = (PlayerSessionBeanLocal) o;

                                playerSession.connexion(pseudo, mdp);
                                session.setAttribute("PlayerId", playerSession.getPlayer().getId());
                                session.setAttribute("PSB", playerSession);
                                
//                                Object o2 = ic.lookup("java:global/game_room/game_room-ejb/GameSessionBean");
//
//                                GameSessionBeanLocal gameSession = (GameSessionBeanLocal) o2;
//                                playerSession.startGame(gameSession);
//                                session.setAttribute("GSB", playerSession.getgSBL());
                                %>
                                <div class="ok">Connexion réussie.<br/>
                                    Redirection vers la salle de jeux...
                                </div>
                                <script type="text/javascript">
                                    window.location.href="room.jsp";
                                </script>
                                <%
                            } catch (PlayerNotFoundException e) {
                                %>
                                 <div class="erreur" id="ima">Identifiant ou mot de passe invalide</div>
                                <% 
                            }
                        } else {
                            %>
                             <div class="erreur" id="ima">Identifiant ou mot de passe vide</div>
                            <%            
                        }
                     } else if (deconnexion != null && "true".equals(deconnexion)) {
                         PlayerSessionBeanLocal psb = (PlayerSessionBeanLocal) session.getAttribute("PSB");
                         psb.deconnexion();
                         session.removeAttribute("PSB");
                         session.removeAttribute("GSB");
                         session.removeAttribute("FTDisplay");
                         
                         %>
                                <div class="ok">Vous avez bien été déconnecté. A bientôt !
                                </div>
                         <%
                     }
                %>
                </div>
    
                <div class="content">
                    <input name="username" type="text" required="required" pattern="[A-Za-z0-9_-]{3,15}" class="input username" placeholder="Pseudo" />
                    <input name="password" type="password" required="required" class="input password" placeholder="Mot de passe" />
                </div>

                <div class="footer">
                    <input type="submit" name="submit" value="Connexion" class="button" /><br/>
                    <a class="register" href="register.jsp">S'enregistrer</a>
                </div>
            </form>
        </div>
        <div class="gradient"></div>
    </body>
</html>