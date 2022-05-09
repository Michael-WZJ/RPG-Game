package edu.uob;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;

import edu.uob.actions.CustomAct;
import edu.uob.entities.*;
import edu.uob.parser.*;
import java.util.ArrayList;

import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import com.alexmerz.graphviz.objects.Edge;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/** This class implements the STAG server. */
public final class GameServer {

    private static final char END_OF_TRANSMISSION = 4;
    private final GameModel gameModel;

    public static void main(String[] args) throws IOException {
        File entitiesFile = Paths.get("config" + File.separator + "basic-entities.dot").toAbsolutePath().toFile();
        File actionsFile = Paths.get("config" + File.separator + "basic-actions.xml").toAbsolutePath().toFile();
        GameServer server = new GameServer(entitiesFile, actionsFile);
        server.blockingListenOn(8888);
    }

    /**
    * KEEP this signature (i.e. {@code edu.uob.GameServer(File, File)}) otherwise we won't be able to mark
    * your submission correctly.
    *
    * <p>You MUST use the supplied {@code entitiesFile} and {@code actionsFile}
    *
    * @param entitiesFile The game configuration file containing all game entities to use in your game
    * @param actionsFile The game configuration file containing all game actions to use in your game
    *
    */
    public GameServer(File entitiesFile, File actionsFile) {
        // TODO implement your server logic here
        gameModel = new GameModel();
        loadEntities(entitiesFile);
        loadActions(actionsFile);
        gameModel.updateState();
    }

    /**
    * KEEP this signature (i.e. {@code edu.uob.GameServer.handleCommand(String)}) otherwise we won't be
    * able to mark your submission correctly.
    *
    * <p>This method handles all incoming game commands and carries out the corresponding actions.
    */
    public String handleCommand(String command) {
        // TODO implement your server logic here
        ParserStag p = new ParserStag(command, gameModel.getIdentifiers());
        try {
            GameAction action = p.parse();
            String username = action.getUsername();
            if (! gameModel.hasPlayer(username)) {
                Player newPlayer = new Player(username, "A Player", gameModel.getStartLoc());
                gameModel.addPlayer(newPlayer);
            }
            return action.execute(this);

        } catch (STAGException se) {
            return se.getMessage();
        }
        // return "Thanks for your message: " + command; //wzj
    }



    public String lookInventory(String username) {
        String result = "You're carrying these artefacts:\n";
        result = result.concat(gameModel.getPlayer(username).printInventory());
        return result;
    }

    public String lookAround(String username) {
        String location = gameModel.getPlayer(username).getLocation();
        return gameModel.describeLoc(location);
    }


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    /*                      Methods for Reading Files                           */
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    public void loadEntities(File entitiesFile) {
        try {
            Parser parser = new Parser();
            FileReader reader = new FileReader(entitiesFile);
            parser.parse(reader);
            Graph wholeDocument = parser.getGraphs().get(0);
            ArrayList<Graph> sections = wholeDocument.getSubgraphs();

            // The locations will always be in the first subgraph
            ArrayList<Graph> locations = sections.get(0).getSubgraphs();
            Graph firstLoc = locations.get(0);
            String firstLocName = firstLoc.getNodes(false).get(0).getId().getId();
            gameModel.setStartLocation(firstLocName);
            loadLocations(locations);

            // The paths will always be in the second subgraph
            ArrayList<Edge> paths = sections.get(1).getEdges();
            loadPaths(paths);

        } catch (FileNotFoundException fnfe) {

        } catch (ParseException pe) {

        }
    }

    public void loadLocations(ArrayList<Graph> locations) {
        if (locations.isEmpty()) {
            return;
        }
        String locName, locDesc;
        for (Graph g : locations) {
            Node locDetails = g.getNodes(false).get(0);
            locName = locDetails.getId().getId();
            locDesc = locDetails.getAttribute("description");
            gameModel.addLocation(new Location(locName, locDesc));
            loadEntToLoc(g, locName);
        }
    }

    public void loadPaths(ArrayList<Edge> paths) {
        if (paths.isEmpty()) {
            return;
        }
        Node fromLoc, toLoc;
        for (Edge e : paths) {
            fromLoc = e.getSource().getNode();
            toLoc = e.getTarget().getNode();
            gameModel.addPath(fromLoc.getId().getId(), toLoc.getId().getId());
        }
    }

    public void loadEntToLoc(Graph graph, String locName) {
        ArrayList<Graph> entities = graph.getSubgraphs();
        if (entities.isEmpty()) {
            return;
        }
        String entityType;
        for (Graph g : entities) {
            entityType = g.getId().getId();
            switch (entityType) {
                case "characters" -> loadCharacters(g, locName);
                case "artefacts" -> loadArtefacts(g, locName);
                case "furniture" -> loadFurniture(g, locName);
            }
        }
    }

    public void loadCharacters(Graph graph, String locName) {
        ArrayList<Node> characters = graph.getNodes(false);
        if (characters.isEmpty()) {
            return;
        }
        NPC character;
        for (Node n : characters) {
            character = new NPC(n.getId().getId(), n.getAttribute("description"));
            gameModel.addEntities(locName, character);
        }
    }

    public void loadFurniture(Graph graph, String locName) {
        ArrayList<Node> furnitureList = graph.getNodes(false);
        if (furnitureList.isEmpty()) {
            return;
        }
        Furniture furniture;
        for (Node n : furnitureList) {
            furniture = new Furniture(n.getId().getId(), n.getAttribute("description"));
            gameModel.addEntities(locName, furniture);
        }
    }

    public void loadArtefacts(Graph graph, String locName) {
        ArrayList<Node> artefacts = graph.getNodes(false);
        if (artefacts.isEmpty()) {
            return;
        }
        Artefact artefact;
        for (Node n : artefacts) {
            artefact = new Artefact(n.getId().getId(), n.getAttribute("description"));
            gameModel.addEntities(locName, artefact);
        }
    }



    public void loadActions(File actionsFile) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(actionsFile);
            Element root = document.getDocumentElement();
            NodeList actions = root.getChildNodes();

            int actionsNum = actions.getLength();
            for (int i = 1; i < actionsNum; i += 2) {
                Element action = (Element)actions.item(i);
                loadActionElements(action);
            }

        } catch (ParserConfigurationException pce) {

        } catch (SAXException saxe) {

        } catch (IOException ioe) {

        }
    }

    public void loadActionElements(Element action) {
        Element triggers = (Element)action.getElementsByTagName("triggers").item(0);
        NodeList triggerList = triggers.getElementsByTagName("keyword");
        int triggerNum = triggerList.getLength();
        for (int i = 0; i < triggerNum; i++) {
            String trigger = triggerList.item(i).getTextContent();
            CustomAct cusAction = buildCustomAct(trigger, action);
            //System.out.println(cusAction); // wzj
            gameModel.addAction(cusAction);
        }


    }

    public CustomAct buildCustomAct(String trigger, Element action) {
        CustomAct cusAction = new CustomAct(trigger);

        Element subjects = (Element)action.getElementsByTagName("subjects").item(0);
        NodeList subjectList = subjects.getElementsByTagName("entity");
        int subjectNum = subjectList.getLength();
        for (int i = 0; i < subjectNum; i++) {
            String subject = subjectList.item(i).getTextContent();
            cusAction.addSubject(subject);
        }

        Element consumed = (Element)action.getElementsByTagName("consumed").item(0);
        NodeList consumedList = consumed.getElementsByTagName("entity");
        int consumedNum = consumedList.getLength();
        for (int i = 0; i < consumedNum; i++) {
            String consume = consumedList.item(i).getTextContent();
            cusAction.addConsumes(consume);
        }

        Element produced = (Element)action.getElementsByTagName("produced").item(0);
        NodeList producedList = produced.getElementsByTagName("entity");
        int producedNum = producedList.getLength();
        for (int i = 0; i < producedNum; i++) {
            String produce = producedList.item(i).getTextContent();
            cusAction.addProduces(produce);
        }

        Element narration = (Element)action.getElementsByTagName("narration").item(0);
        String narrationText = narration.getTextContent();
        cusAction.setNarration(narrationText);

        return cusAction;
    }












    public GameModel getGameModel() {
        return gameModel;
    }


    // wzj 不要这么多空行
    //  === Methods below are there to facilitate server related operations. ===

    /**
    * Starts a *blocking* socket server listening for new connections. This method blocks until the
    * current thread is interrupted.
    *
    * <p>This method isn't used for marking. You shouldn't have to modify this method, but you can if
    * you want to.
    *
    * @param portNumber The port to listen on.
    * @throws IOException If any IO related operation fails.
    */
    public void blockingListenOn(int portNumber) throws IOException {
        try (ServerSocket s = new ServerSocket(portNumber)) {
            System.out.println("Server listening on port " + portNumber);
            while (!Thread.interrupted()) {
                try {
                    blockingHandleConnection(s);
                } catch (IOException e) {
                    System.out.println("Connection closed");
                }
            }
        }
    }

    /**
    * Handles an incoming connection from the socket server.
    *
    * <p>This method isn't used for marking. You shouldn't have to modify this method, but you can if
    * * you want to.
    *
    * @param serverSocket The client socket to read/write from.
    * @throws IOException If any IO related operation fails.
    */
    private void blockingHandleConnection(ServerSocket serverSocket) throws IOException {
        try (Socket s = serverSocket.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))) {
            System.out.println("Connection established");
            String incomingCommand = reader.readLine();
            if(incomingCommand != null) {
                System.out.println("Received message from " + incomingCommand);
                String result = handleCommand(incomingCommand);
                writer.write(result);
                writer.write("\n" + END_OF_TRANSMISSION + "\n");
                writer.flush();

            }
        }
    }
}
