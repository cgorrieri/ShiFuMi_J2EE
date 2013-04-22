<%@page import="java.sql.Connection"%>
<%@page import="javax.jms.JMSException"%>
<%@page import="javax.annotation.Resource"%>
<%@page import="java.io.Console"%>
<%@page import="java.util.List"%>
<%@page import="enterprise.game_room_ejb.ejb.session.PlayerSessionBeanLocal"%>
<%@page import="enterprise.game_room_ejb.persistence.Player"%>
<%@page import="helpers.Helpers"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <link href="css/styletab.css" rel="stylesheet" type="text/css" />
        <link href="css/jeux.css" rel="stylesheet" type="text/css" />
        <meta http-equiv="Content-type" content="text/html; charset=UTF-8"/>
        <script type="text/javascript">
            function activdesactiv(){ 
                var arg=activdesactiv.arguments; 
                var t=arg[0]; 
                if (document.getElementById(t).checked==false){ 
                    document.getElementById(t).checked=true; 
                } else { 
                    document.getElementById(t).checked=false; 
                } 
            } 
            var requete;
            
            // Méthode qui va appeler en ajax le servlet qui va récupérer les joueurs connectés.
            function defier(id) {
                var url = "defier?id="+id;
                if (window.XMLHttpRequest) {
                    requete = new XMLHttpRequest();
                    requete.open("GET", url, true);
                    // La méthodes majPlayers sera appeler à la réponse de la requète
                    requete.onreadystatechange = function() {alert("OK")};
                    requete.send(null);
                } else if (window.ActiveXObject) {
                    requete = new ActiveXObject("Microsoft.XMLHTTP");
                    if (requete) {
                        requete.open("GET", url, true);
                        requete.onreadystatechange = function() {alert("OK")};
                        requete.send();  
                    }
                } else {
                    alert("Le navigateur ne supporte pas la technologie Ajax");
                }
            }
            
            // Méthode qui va appeler en ajax le servlet qui va récupérer les joueurs connectés.
            function getPlayers() {
                var url = "get_players";
                if (window.XMLHttpRequest) {
                    requete = new XMLHttpRequest();
                    requete.open("GET", url, true);
                    // La méthodes majPlayers sera appeler à la réponse de la requète
                    requete.onreadystatechange = majPlayers;
                    requete.send(null);
                } else if (window.ActiveXObject) {
                    requete = new ActiveXObject("Microsoft.XMLHTTP");
                    if (requete) {
                        requete.open("GET", url, true);
                        requete.onreadystatechange = majPlayers;
                        requete.send();  
                    }
                } else {
                    alert("Le navigateur ne supporte pas la technologie Ajax");
                }
            }
            // Va mettre dans le corps des joueurs, les jours connectés
            function majPlayers() {
                if (requete.readyState == 4) {
                    if (requete.status == 200) {
                        document.getElementById("players_body").innerHTML = requete.responseText;
                    } else {
                        alert('Une erreur est survenue lors de la mise à jour de la page.'+
                            '\n\nCode retour = '+requete.statusText);    
                    }
                }
            }
        </script>
    </head>
    <body><h1> SI-FU-MI </h1>
        <div class="header">
            <div class="message">
                <!-- <div class="erreur">
                    Vous ne pouvez pas effectuer cette action
                </div> -->
            </div>
        </div>
        <%
            if (session.getAttribute("PSB") != null) {
                PlayerSessionBeanLocal psb = (PlayerSessionBeanLocal) session.getAttribute("PSB");
        %>
        <div class="carre_connexion">
            Bonjour <%out.print(psb.getPlayer().getPseudo() + " (" + psb.getPlayer().getId() + ")");%> <br/>
            <% out.print(psb.getPlayer().getScore());%> points <br/>
            <a href="index.jsp?deconnexion=true">Déconnexion</a>
        </div>
        <script type="text/javascript">
            // Rafraichi la liste des joueurs
            var x = setInterval(getPlayers, 3000);
        </script>
        <div class="gauche">
            <h2><center>Liste des concurrents</center></h2>

            <table id="rounded-corner" class="tabtous">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company">Pseudo</th>
                        <th scope="col" class="rounded-q1">Points</th>
                        <th scope="col" class="rounded-q2">Etat</th>
                        <th scope="col" class="rounded-q2">Actions</th>
                    </tr>
                </thead>
                <tbody id="players_body">
                    <%
                        // Lister tous les participants
                        List players = psb.getConnectedPlayers();
                        out.print(Helpers.playersListToHTML(players));
                    %>
                </tbody>
            </table>
        </div>
        <div class="droite">
            <h2><center>Liste des défis</center></h2>
            <table id="rounded-corner" class="tabdefie">
                <thead>
                    <tr >
                        <th scope="col" class="rounded-company">Pseudo</th>
                        <th scope="col" class="rounded-q1">Points</th>
                        <th scope="col" class="rounded-q2">Etat</th>
                        <th scope="col" class="rounded-q2">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Lister tous les participants
                        List defiant = psb.getDefies();
                        out.print(Helpers.playersListToHTML(defiant));
                    %>
                </tbody>
            </table>
        </div>
        <%
        } else {
        %>
        <div class="header">
            <div class="message">
                <div class="erreur">
                    Vous n'étes pas identifié...
                    Redirection vers la connexion...
                    <a href="index.jsp"> RETOUR </a>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            window.location.href="index.jsp";
        </script>
        <%                            }
        %>
    </body>
</html>