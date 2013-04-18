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
                var t,arg=activdesactiv.arguments; 
                t=arg[0]; 
                if (document.getElementById(t).checked==false){ 
                    document.getElementById(t).checked=true; 
                } else { 
                    document.getElementById(t).checked=false; 
                } 
            } 
            var requete;
            
            // M�thode qui va appeler en ajax le servlet qui va r�cup�rer les joueurs connect�s.
            function getPlayers() {
                var url = "get_players";
                if (window.XMLHttpRequest) {
                    requete = new XMLHttpRequest();
                    requete.open("GET", url, true);
                    // La m�thodes majPlayers sera appeler � la r�ponse de la requ�te
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
            // Va mettre dans le corps des joueurs, les jours connect�s
            function majPlayers() {
                if (requete.readyState == 4) {
                    if (requete.status == 200) {
                        document.getElementById("players_body").innerHTML = requete.responseText;
                    } else {
                        alert('Une erreur est survenue lors de la mise � jour de la page.'+
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
            <a href="index.jsp?deconnexion=true">D�connexion</a>
        </div>
            <script type="text/javascript">
                // Rafraichi la liste des joueurs
                var x = setInterval(getPlayers, 3000);
            </script>
        <div class="gauche">
            <h2><center>Liste des concurrents</center></h2>
            <form action="" method="GET">
                <table id="rounded-corner" class="tabtous">
                    <thead>
                        <tr>
                            <th scope="col" class="rounded-company"></th>
                            <th scope="col" class="rounded-company">Pseudo</th>
                            <th scope="col" class="rounded-q1">Points</th>
                            <th scope="col" class="rounded-q2">Etat</th>
                        </tr>
                    </thead>
                    <tbody id="players_body">
                        <%
                            // Lister tous les participants
                            List players = psb.getConnectedPlayers();
                            out.print(Helpers.playersListToHTML(players));
                        } else {
                        %>
                        <div class="header">
                            <div class="message">
                                <div class="erreur">
                                    Vous n'�tes pas identifi�...
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
                    </tbody>
                </table>

                <center>
                    <input type="hidden" name="type" value="defier"/>
                    <input class="button" type="submit" value="D�fier !"/>
                    <input class="button" type="button" value="Observer !" />
                </center>
            </form>
        </div>
        <div class="droite">
            <h2><center>Liste des d�fis</center></h2>
            <form action="" method="GET">
                <table id="rounded-corner" class="tabdefie">
                    <thead>
                        <tr >
                            <th scope="col" class="rounded-company"></th>
                            <th scope="col" class="rounded-company">Pseudo</th>
                            <th scope="col" class="rounded-q1">Points</th>
                            <th scope="col" class="rounded-q2">Cadence</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr onclick="activdesactiv('lala');">
                            <td><input id="lala" type="radio" name="pseudo" value="3465" /></td>
                            <td>3465</td>
                            <td>1702</td>
                            <td>Souvent</td>
                        </tr>
                    </tbody>
                </table>
                <input type="hidden" name="type" value="accepter" />
                <center><input class="button" type="submit" value="Accepter !" /></center>
            </form>
        </div>
    </body>
</html>