<%@page import="enterprise.game_room_ejb.mdb.UpdateAcceptation"%>
<%@page import="enterprise.game_room_ejb.ejb.session.GameSessionBeanLocal"%>
<%@page import="enterprise.game_room_ejb.mdb.Update"%>
<%@page import="enterprise.game_room_ejb.mdb.TypeUpdate"%>
<%@page import="javax.jms.ObjectMessage"%>
<%@page import="javax.jms.Connection"%>
<%@page import="javax.jms.Session"%>
<%@page import="javax.jms.ConnectionFactory"%>
<%@page import="javax.ejb.Init"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.jms.MessageConsumer"%>
<%@page import="javax.jms.TopicConnection"%>
<%@page import="javax.jms.Topic"%>
<%@page import="javax.jms.JMSException"%>
<%@page import="javax.annotation.Resource"%>
<%@page import="java.io.Console"%>
<%@page import="java.util.List"%>
<%@page import="enterprise.game_room_ejb.ejb.session.PlayerSessionBeanLocal"%>
<%@page import="enterprise.game_room_ejb.persistence.Player"%>

<%! // Fera partie de la classe rendu du jsp
    // Champ privé de la classe
    Boolean firstTime;
    //@Resource(mappedName = "jms/ConnexionTopicCF")
    ConnectionFactory connectionFactory;
    //@Resource(mappedName = "jms/ConnexionTopic")
    Topic topic;
    Connection connection = null;
    Session jmsSession = null;
    MessageConsumer subscriber = null;
    ObjectMessage message = null;

    // methode d'init override
    public void jspInit() {
        System.out.println("jspInit");
        try {
            InitialContext ic = new InitialContext();
            connectionFactory = (ConnectionFactory) ic.lookup("jms/ConnectionFactory");
            topic = (Topic) ic.lookup("jms/ConnexionTopic");

            connection = connectionFactory.createConnection();
            jmsSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            subscriber = jmsSession.createConsumer(topic);

            connection.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // methode de destroy override
    public void jspDestroy() {
        try {
            connection.stop();
            subscriber.close();
            jmsSession.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String playersListToHTML(List players) {
        String resultat = "";
        for (int i = 0; i < players.size(); i++) {
            Player p = (Player) players.get(i);
            resultat += "<tr>"
                    + "<td>" + p.getPseudo() + "</td>"
                    + "<td>" + p.getScore() + "</td>"
                    + "<td>" + p.getEtat() + "</td>"
                    + "<td><a href='?defier=" + p.getId() + "'>défier</a></td>"
                    + "</tr>";
        }
        return resultat;
    }

    public String defiantstToHTML(List<Player> players) {
        String resultat = "";
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            resultat += "<tr>"
                    + "<td>" + p.getPseudo() + "</td>"
                    + "<td>" + p.getScore() + "</td>"
                    + "<td>" + p.getEtat() + "</td>"
                    + "<td><a href='?accepter=" + p.getId() + "'>Accepter</a></td>"
                    + "</tr>";
        }
        return resultat;
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-type" content="text/html; charset=UTF-8"/>
        <link href="css/styletab.css" rel="stylesheet" type="text/css" />
        <link href="css/jeux.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript">
            
        </script>
    </head>
    <body>
        <h1> SI-FU-MI </h1>
        <%
            if (session.getAttribute("PSB") != null) {
                PlayerSessionBeanLocal psb = (PlayerSessionBeanLocal) session.getAttribute("PSB");

                String defier = request.getParameter("defier");
                if (defier != null && !"".equals(defier)) {
                    psb.defier(Long.valueOf(defier));
                }
                String accepter = request.getParameter("accepter");
                if (accepter != null && !"".equals(accepter)) {
                    GameSessionBeanLocal gsb = psb.accepterDefi(Long.valueOf(accepter));
                    session.setAttribute("GSB", gsb); 
                    %>
                        <script type="text/javascript">
                                    window.location.href="jeux.jsp";
                                </script>
                    <%
                }

        %>
        <div class="header">
            <div class="message">
                <%
                    //session.setAttribute("FTDisplay", true);
                    if (session.getAttribute("FTDisplay") == null) {
                        firstTime = true;
                        session.setAttribute("FTDisplay", firstTime);
                    } else {
                        firstTime = (Boolean) session.getAttribute("FTDisplay");
                    }

                    try {
                        if (firstTime) {
                            firstTime = false;
                            session.setAttribute("FTDisplay", firstTime);
                        } else {
                            boolean goOn = false;
                            Update u = null;
                            // Tant que le message ne nous est pas addressé
                            while (!goOn) {
                                message = (ObjectMessage) subscriber.receive();
                                u = (Update) message.getObject();
                                goOn = psb.isMessageForMe(u.dest);
                            }
                            if (u.type == TypeUpdate.CONNEXION) {
                            %>
                            <div class="ok">
                                Le joueur <%= u.pseudo%> s'est connecté
                            </div>
                            <%
                            } else if (u.type == TypeUpdate.DECONNEXION) {
                            %>
                            <div class="erreur">
                                Le joueur <%= u.pseudo%> s'est déconnecté
                            </div>
                            <%
                            } else if (u.type == TypeUpdate.ACCEPTATION) {
                                UpdateAcceptation ua = (UpdateAcceptation) u;
                                ua.gsb.setJ1(psb.getPlayer());
                                session.setAttribute("GSB", ua.gsb);
                            %>
                            <div class="ok">
                                Le joueur <%= u.pseudo%> à accepter le défi
                            </div>
                            <script type="text/javascript">
                                    window.location.href="jeux.jsp";
                                </script>
                            <%
                            } else if (u.type == TypeUpdate.ANNULATION) {
                                // psb.removeDefis               
                            } else if (u.type == TypeUpdate.DEFI) {
                                if (psb.addDefis(u.id)) {
                            %>
                            <div class="ok">
                                Le joueur <%= u.pseudo%> vous a défié
                            </div>
                            <%
                            }
                        }
                    }
                %>
                <!-- <div class="erreur">
                    Vous ne pouvez pas effectuer cette action
                </div> -->
            </div>
        </div>

        <div class="carre_connexion">
            Bonjour <%out.print(psb.getPlayer().getPseudo() + " (" + psb.getPlayer().getId() + ")");%> <br/>
            <% out.print(psb.getPlayer().getScore());%> points <br/>
            <a href="index.jsp?deconnexion=true">Déconnexion</a>
        </div>
        <script type="text/javascript">
            // Rafraichi la liste des joueurs
            //var x = setInterval(getPlayers, 3000);
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
                        out.print(playersListToHTML(players));
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
                        out.print(defiantstToHTML(defiant));
                    %>
                </tbody>
            </table>
        </div>
        <%
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        <script type="text/javascript">
            window.location.reload(true);
        </script>
    </body>
</html>