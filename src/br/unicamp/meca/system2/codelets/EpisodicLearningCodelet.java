/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.meca.system2.codelets;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.representation.owrl.AbstractObject;
import br.unicamp.cst.representation.owrl.Property;
import br.unicamp.cst.representation.owrl.QualityDimension;
import br.unicamp.meca.util.AbstractObjectPair;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author wander
 */
public abstract class EpisodicLearningCodelet extends Codelet{
    
    protected String id;
    
    protected String episodicAttentionCodeletId;
    protected String episodicRetrievalCodeletId;
    
    private Memory episodicMemory;
    private Memory episodicBufferMemory;
    
    private List<Memory> episodicMemoryMOList;
    private List<AbstractObjectPair> episodicBufferList;
    //private Memory syncWait;


    
    
    public EpisodicLearningCodelet(String id, String episodicAttentionCodeletId, String episodicRetrievalCodeletId){
        this.id = id;
        this.episodicAttentionCodeletId = episodicAttentionCodeletId;
        this.episodicRetrievalCodeletId = episodicRetrievalCodeletId;
	setName(id);
        
        episodicMemoryMOList = new ArrayList<>();
    }
    
    @Override
    public void accessMemoryObjects() {
        if(episodicBufferMemory==null && episodicAttentionCodeletId!=null)
			this.episodicBufferMemory = this.getInput(episodicAttentionCodeletId);
                
                if(episodicMemory==null && episodicRetrievalCodeletId!=null)
			this.episodicMemory = this.getOutput(episodicRetrievalCodeletId);

    }

    @Override
    public void proc() {
        
        addEncondingEp();
        //set nas memorias

    }
    
    public abstract void addEncondingEp();


    public boolean equals (AbstractObjectPair first, AbstractObjectPair second){
        boolean result = false;
        
        if(first.getAfter().equals(second.getAfter()) || first.getBefore().equals(second.getBefore())){
            result = true;
        }
        
        return result;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public String getId(){
        return this.id;
    }
    
    public boolean containsName(final List<AbstractObject> list, final String name){
        return list.stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
    }
    
    public AbstractObject getAbstractObjectStruct(final AbstractObject list, final String name){
        return list.getCompositeParts().stream().filter(o -> o.getName().equals(name)).findFirst().get();
    }
    
    public boolean containsProperty(final AbstractObject list, final String name){
        return list.getProperties().stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
    }
    
    public Property getPropertyStruct(final AbstractObject list, final String name){
        return list.getProperties().stream().filter(o -> o.getName().equals(name)).findFirst().get();
    }
    
    public QualityDimension getQDStruct(final Property list, final String name){
        return list.getQualityDimensions().stream().filter(o -> o.getName().equals(name)).findFirst().get();
    }
    
}