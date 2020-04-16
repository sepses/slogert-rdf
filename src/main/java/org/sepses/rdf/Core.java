package org.sepses.rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

/**
 * TODO: this class is not complete yet!
 */
public class Core {

    public static final String ontologyURI = "http://w3id.org/sepses/vocab/log/core#";
    public static final String instanceURI = "http://w3id.org/sepses/id/core/";

    //
    public static final Resource NS;
    public static final Resource NS_INSTANCE;

    // classes
    public static final Resource Ip;


    // root variable
    public static final Property containedVariable;

    // ner variables
    public static final Property containedIp;
    public static final Property containedPort;
    public static final Property containedHost;
    public static final Property containedDomain;
    public static final Property containedUser;

    // non-ner variables
    public static final Property containedFilePath;
    public static final Property containedFile;
    public static final Property containedPath;

    // specific variables
    public static final Property localPid;
    public static final Property localHost;
    public static final Property remoteIp;


    private static final Model m = ModelFactory.createDefaultModel();

    static {

        NS = m.createResource(ontologyURI);
        NS_INSTANCE = m.createResource(instanceURI);

        Ip = m.createProperty(ontologyURI + "Ip");

        containedVariable = m.createProperty(ontologyURI + "containedVariable");

        containedIp = m.createProperty(ontologyURI + "containedIp");
        containedPort = m.createProperty(ontologyURI + "containedPort");
        containedHost = m.createProperty(ontologyURI + "containedHost");
        containedDomain = m.createProperty(ontologyURI + "containedDomain");
        containedUser = m.createProperty(ontologyURI + "containedUser");

        containedFilePath = m.createProperty(ontologyURI + "containedFilePath");
        containedFile = m.createProperty(ontologyURI + "containedFile");
        containedPath = m.createProperty(ontologyURI + "containedPath");

        localPid = m.createProperty(ontologyURI + "localPid");
        localHost = m.createProperty(ontologyURI + "localHost");
        remoteIp = m.createProperty(ontologyURI + "remoteIp");

    }

    public Core() {
    }

    public static String getURI() {
        return "http://w3id.org/sepses/vocab/log/core";
    }
}
