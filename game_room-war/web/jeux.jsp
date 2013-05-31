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

                String jouer = request.getParameter("jouer");
                if (jouer != null && !"".equals(jouer)) {
                    EnumGame e = EnumGame.UNKNOW;
                    if (jouer.equals("c")) {
                        e = EnumGame.CISEAU;
                    } else if (jouer.equals("f")) {
                        e = EnumGame.FEUILLE;
                    } else if (jouer.equals("p")) {
                        e = EnumGame.PIERRE;
                    }
                    gsb.jouer((Long) session.getAttribute("PlayerId"), e);
                    
                    int res = gsb.getResult((Long)session.getAttribute("PlayerId"));
                    
                    if(res == 0){
                        %>
                        <p>EGALITE</p>
                        <%
                    } else if(res > 0){
                        %>
                        <p>GAGNE</p>
                        <%
                    } else{
                        %>
                        <p>PERDU</p>
                        <%
                    }
                } else {
        %>
        <div class="jeux">
            <div class="headNom"> Vous </div>
            <div class="joueurhaut">
                <center>
                    <form action="" method="GET">
                        <a class="ima" id="pierre" ><img src="images/pierre.png"/></a>
                        <a class="ima" id="papier" ><img src="images/papier.png"/></a>
                        <a class="ima" id="ciseau" ><img src="images/ciseau.png"/></a>
                    </form>
                </center>
            </div>
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
        <%                        }
            }
        %>
    </body>
</html>

