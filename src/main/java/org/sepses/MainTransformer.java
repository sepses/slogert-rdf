package org.sepses;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.sepses.helper.Utility;
import org.sepses.rdf.Core;
import org.sepses.rdf.Slogert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainTransformer {

    public static void main(String[] args) throws FileNotFoundException {

        transform(args[0], args[1], args[2]);
        //        transform("slogert-owl.ttl", "auth.log_structured.ttl", "output-model.ttl");

    }

    public static void transform(String slogertFileString, String inputFileString, String outputFileString)
            throws FileNotFoundException {

        File slogertFile = new File(slogertFileString);
        File inputFile = new File(inputFileString);
        File outputFile = new File(outputFileString);

        Model slogModel = ModelFactory.createDefaultModel();
        RDFDataMgr.read(slogModel, new FileInputStream(slogertFile), Lang.TURTLE);

        Model model = ModelFactory.createDefaultModel();
        RDFDataMgr.read(model, new FileInputStream(inputFile), Lang.TURTLE);

        Model outputModel = transformModel(slogModel, model);
        RDFDataMgr.write(new FileOutputStream(outputFile), outputModel, Lang.TURTLE);

        outputModel.close();
        model.close();
        slogModel.close();
    }

    public static Model transformModel(Model slogModel, Model model) {

        Model oModel = ModelFactory.createDefaultModel();

        model.listStatements().forEachRemaining(statement -> {
            Resource subject = statement.getSubject();
            Property predicate = statement.getPredicate();
            RDFNode objectNode = statement.getObject();

            if (slogModel.contains(predicate, RDFS.range)) {
                String object = objectNode.asLiteral().getString();
                String cleanString = Utility.cleanUriContent(object);
                Property prop = slogModel.getProperty(predicate.getURI());

                Resource range = prop.getPropertyResourceValue(RDFS.range);
                Resource resource =
                        oModel.createResource(Core.NS_INSTANCE + range.getLocalName() + "_" + cleanString);

                oModel.add(subject, predicate, resource);
                oModel.add(resource, RDF.type, range);
                oModel.add(resource, RDFS.label, object);

            } else {
                oModel.add(statement);
            }
        });

        return oModel;
    }

    public static Model testWrite(Model model) {

        Model outputModel = ModelFactory.createDefaultModel();

        StmtIterator cip = model.listStatements(null, Slogert.containedIp, (RDFNode) null);
        cip.forEachRemaining(statement -> {
            String ipString = statement.getObject().asLiteral().getString();
            String cleanString = Utility.cleanUriContent(ipString);
            Resource resource = outputModel.createResource(Core.NS_INSTANCE + cleanString);

            outputModel.add(statement.getSubject(), Core.containedIp, resource);
            outputModel.add(resource, RDF.type, Core.Ip);
        });

        return outputModel;
    }

}
