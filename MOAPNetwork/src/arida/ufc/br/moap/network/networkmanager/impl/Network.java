/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.networkmanager.impl;

import org.gephi.io.importer.api.EdgeDefault;

/**
 *
 * @author igobrilhante
 */
public class Network {
    private String name;
    private String description;
    private EdgeDefault edgeType = EdgeDefault.UNDIRECTED;

    public Network(String name,String description){
        this.name = name;
        this.description = description;
    }
    public Network(String name){
        this.name = name;
        
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EdgeDefault getEdgeType() {
        return edgeType;
    }

    public void setEdgeType(EdgeDefault edgeType) {
        this.edgeType = edgeType;
    }

    
    
    
}
