package org.sepses;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.sepses.helper.Utility;
import org.sepses.rdf.Core;
import org.sepses.rdf.Slogert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainTransformer {

    public static void main(String[] args) throws FileNotFoundException {
        Model slogModel = ModelFactory.createDefaultModel();
        RDFDataMgr.read(slogModel, new FileInputStream("slogert-owl.ttl"), Lang.TURTLE);

        Model model = ModelFactory.createDefaultModel();
        RDFDataMgr.read(model, new FileInputStream("auth.log_structured.ttl"), Lang.TURTLE);

        Model outputModel = ModelFactory.createDefaultModel();
        outputModel.setNsPrefixes(slogModel.getNsPrefixMap());

        write(slogModel, model, outputModel);

        RDFDataMgr.write(new FileOutputStream("output-model.ttl"), outputModel, Lang.TURTLE);

        outputModel.close();
        model.close();
        slogModel.close();

    }

    public static void write(Model slogModel, Model model, Model outputModel) {

        model.listStatements().forEachRemaining(statement -> {
            Resource subject = statement.getSubject();
            Property predicate = statement.getPredicate();
            RDFNode objectNode = statement.getObject();

            if (slogModel.contains(predicate, RDFS.range)) {
                String object = objectNode.asLiteral().getString();
                String cleanString = Utility.cleanUriContent(object);
                Property prop = slogModel.getProperty(predicate.getURI());

                Resource range = prop.getPropertyResourceValue(RDFS.range);
                Resource resource = outputModel.createResource(Core.NS_INSTANCE + cleanString);

                outputModel.add(subject, predicate, resource);
                outputModel.add(resource, RDF.type, range);

            } else {
                outputModel.add(statement);
            }
        });
    }

    public static void testWrite(Model model, Model outputModel) {

        StmtIterator cip = model.listStatements(null, Slogert.containedIp, (RDFNode) null);
        cip.forEachRemaining(statement -> {
            String ipString = statement.getObject().asLiteral().getString();
            String cleanString = Utility.cleanUriContent(ipString);
            Resource resource = outputModel.createResource(Core.NS_INSTANCE + cleanString);

            outputModel.add(statement.getSubject(), Core.containedIp, resource);
            outputModel.add(resource, RDF.type, Core.Ip);
        });
    }

}
