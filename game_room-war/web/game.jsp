<%@page import="enterprise.game_room_ejb.common.EnumGame"%>
<%@page import="enterprise.game_room_ejb.ejb.session.GameSessionBeanLocal"%>
<%@page import="enterprise.game_room_ejb.ejb.session.GameSessionBean"%>
<html>
    <head>
        <link href="css/styletab.css" rel="stylesheet" type="text/css" />
        <link href="css/jeux.css" rel="stylesheet" type="text/css" />
        <meta http-equiv="Content-type" content="text/html; charset=UTF-8"/> 
    </head>
    <body>
        <h1> Jeux SI-FU-MI</h1>
        <%
            if (session.getAttribute("GSB") != null) {
                GameSessionBeanLocal gsb = (GameSessionBeanLocal) session.getAttribute("GSB");
                
                Long playerId = (Long) session.getAttribute("PlayerId");

                String choice = request.getParameter("choice");
                if (choice != null && !"".equals(choice)) {
                    EnumGame e = EnumGame.UNKNOW;
                    if (choice.equals("s")) {
                        e = EnumGame.SCISSORS;
                    } else if (choice.equals("p")) {
                        e = EnumGame.PAPER;
                    } else if (choice.equals("r")) {
                        e = EnumGame.ROCK;
                    }
                    gsb.sendChoice(playerId, e);
                    
                    int res = gsb.getResult((Long)session.getAttribute("PlayerId"));
                    
                    %>
<div class="header">
    <div class="message">
<%
                    if(res == 0){
                        %>
                        <div class="ok">
                            Match nul
                        </div>
                        <%
                    } else if(res > 0){
                        %>
                       <div class="ok">
                            Vous avez gagné
                        </div>
                        <%
                    } else{
                        %>
                        <div class="erreur">
                            Vous avez perdu
                        </div>
                        <%
                    }
                    %>
    </div>
</div>
                    <%
                }
                if(gsb.getStep() > 0) {
        %>
        <div class="jeux">
            <div class="headNom"> Vous </div>
            <div class="joueurhaut">
                <center>
                    <form action="" method="GET">
                        <a class="ima" id="pierre" href="?choice=r"><img src="images/pierre.png"/></a>
                        <a class="ima" id="papier" href="?choice=p"><img src="images/papier.png"/></a>
                        <a class="ima" id="ciseau" href="?choice=s"><img src="images/ciseau.png"/></a>
                    </form>
                </center>
            </div>
            <div class="joueurbas">
                <center>
                    <%
                    if (choice != null && !"".equals(choice)) {
                        switch(gsb.getOtherVal(playerId)) {
                            case SCISSORS:
                                %><img class="ima" src="images/ciseau.png"><%
                                break;
                            case ROCK:
                                %><img class="ima" src="images/pierre.png"><%
                                break;
                            case PAPER:
                                %><img class="ima" src="images/papier.png"><%
                                break;
                            case UNKNOW:
                                %><img class="ima" src="images/inconnu.png"><%
                                break;
                        }
                    }
                    %>
                </center>
            </div>
            <div class="headNom"> Adversaire </div>
        </div>
        <%
                }
                               else {
                    %>
                    <div>La partie est terminée!<br/>Vous allez être redirigé vers la salle de jeu...</div>
                    <script type="text/javascript">
                        setTimeout(
                            function() {
                            window.location = "room.jsp";
                            },3000);
                    </script>
                    <%
                               }
            }
        %>
    </body>
</html>

