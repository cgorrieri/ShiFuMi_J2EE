<%@page import="java.util.List"%>
<%@page import="enterprise.game_room_ejb.ejb.session.PlayerSessionBeanLocal"%>
<%@page import="enterprise.game_room_ejb.persistence.Player"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <link href="styletab.css" rel="stylesheet" type="text/css" />
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
                    <tbody>
                        <%
                            if (session.getAttribute("PSB") != null) {
                                PlayerSessionBeanLocal psb = (PlayerSessionBeanLocal) session.getAttribute("PSB");
                                List players = psb.getConnectedPlayers();
                                for (int i = 0; i < players.size(); i++) {
                                    Player p = (Player) players.get(i);
                        %>
                        <tr onclick="activdesactiv('<% out.print(p.getPseudo());%>');" >
                            <td><input id="<% out.print(p.getPseudo());%>" type="radio" name="id" value="<% out.print(p.getId());%>"/></td>
                            <td><% out.print(p.getPseudo());%></td>
                            <td><% out.print(p.getScore());%></td>
                            <td><% out.print(p.getEtat());%></td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <p>Vous n'etes pas idetifié...</p>
                        <script type="text/javascript">
                            window.location.href="index.jsp";
                        </script>
                        <%                            }
                        %>
                    </tbody>
                </table>

                <center>
                    <input type="hidden" name="type" value="defier"/>
                    <input class="button" type="submit" value="Défier !"/>
                    <input class="button" type="button" value="Observer !"/>
                </center>
            </form>
        </div>
        <div class="droite">
            <h2><center>Liste des défis</center></h2>
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
                            <td><input id="lala" type="radio" name="pseudo" value="3465"></td>
                            <td>3465</td>
                            <td>1702</td>
                            <td>Souvent</td>
                        </tr>
                    </tbody>
                </table>
                <input type="hidden" name="type" value="accepter">
                    <center><input class="button" type="submit" value="Accepter !"></center>
            </form>
        </div>
    </body>
</html>

